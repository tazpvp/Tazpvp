package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import lombok.RequiredArgsConstructor;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.GuildMemberEntity;
import net.tazpvp.tazpvp.data.services.GuildMemberService;
import net.tazpvp.tazpvp.data.services.GuildService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GuildServiceImpl implements GuildService {
    private final GuildMemberService guildMemberService;
    private Dao<GuildEntity, Integer> userDao;
    @Override
    public Dao<GuildEntity, Integer> getUserDao() {
        if (userDao == null) {
            try {
                userDao = DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), GuildEntity.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return userDao;
    }

    @Override
    public GuildEntity createGuild(String name, UUID owner) {
        final GuildEntity guildEntity = new GuildEntity();

        guildEntity.setName(name);
        guildEntity.setOwner(owner);

        saveGuild(guildEntity);
        addMember(guildEntity, owner, true);
        return guildEntity;
    }

    @Override
    public void saveGuild(GuildEntity guild) {
        try {
            getUserDao().createOrUpdate(guild);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addMember(GuildEntity guild, UUID player, boolean officer) {
        GuildMemberEntity guildMemberEntity = guildMemberService.getGuildMemberByUUID(player);

        if (guildMemberEntity != null) {
            guildMemberEntity.setGuildEntity(guild);
        } else {
            guildMemberEntity = new GuildMemberEntity();
            guildMemberEntity.setGuildEntity(guild);
            guildMemberEntity.setPUUID(player);
            guildMemberEntity.setOfficer(officer);
        }

        guildMemberService.saveGuildMemberEntity(guildMemberEntity);
        guild.getMembers().add(guildMemberEntity);
        saveGuild(guild);
    }

    @Override
    public void removeMember(GuildEntity guild, UUID player) {
        GuildMemberEntity guildMemberEntity = guildMemberService.getGuildMemberByUUID(player);

        if (guildMemberEntity != null) {
            guild.getMembers().remove(guildMemberEntity);
            guildMemberService.deleteMember(guildMemberEntity);
            saveGuild(guild);
        }
    }

    @Override
    public GuildEntity getGuildByPlayer(UUID player) {
        GuildMemberEntity member = guildMemberService.getGuildMemberByUUID(player);

        if (member != null) {
            return member.getGuildEntity();
        }
        return null;
    }

    @Override
    public GuildEntity getGuild(int guildID) {
        try {
            return getUserDao().queryForId(guildID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGuild(GuildEntity guild) {
        try {
            getUserDao().delete(guild);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void messageAll(GuildEntity guild, String msg) {
        for (UUID uuid : getAllOnlineMembers(guild)) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(msg);
        }
    }

    @Override
    public List<UUID> getAllOnlineMembers(GuildEntity guild) {
        List<UUID> onlinePlayers = new ArrayList<>();

        for (UUID offlineMember : getAllMembers(guild)) {
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(offlineMember))) {
                onlinePlayers.add(offlineMember);
            }
        }
        return onlinePlayers;
    }

    @Override
    public List<UUID> getAllMembers(GuildEntity guild) {
        List<UUID> offlinePlayers = new ArrayList<>();
        ForeignCollection<GuildMemberEntity> guildMembers = guild.getMembers();

        for (GuildMemberEntity guildMember : guildMembers) {
            offlinePlayers.add(guildMember.getPUUID());
        }

        return offlinePlayers;
    }

    @Override
    public List<UUID> getOnlineOfficers(GuildEntity guild) {
        List<UUID> onlinePlayers = new ArrayList<>();

        for (UUID offlineMember : getOfficers(guild)) {
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(offlineMember))) {
                onlinePlayers.add(offlineMember);
            }
        }
        return onlinePlayers;
    }

    @Override
    public List<UUID> getOfficers(GuildEntity guild) {
        List<UUID> offlinePlayers = new ArrayList<>();

        ForeignCollection<GuildMemberEntity> guildMembers = guild.getMembers();
        for (GuildMemberEntity guildMember : guildMembers) {
            if (guildMember.isOfficer()) {
                offlinePlayers.add(guildMember.getPUUID());
            }
        }

        return offlinePlayers;
    }

    @Override
    public GuildMemberEntity getMemberEntity(GuildEntity guild, UUID uuid) {
        ForeignCollection<GuildMemberEntity> guildMembers = guild.getMembers();
        for (GuildMemberEntity guildMember : guildMembers) {
            if (uuid.equals(guildMember.getPUUID())) {
                return guildMember;
            }
        }
        return null;
    }

    @Override
    public List<GuildEntity> getAllGuilds() {
        try {
            return getUserDao().queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GuildEntity> getAllGuildsSorted() {
        try {
            return getUserDao().queryBuilder().orderBy("kills", true).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isInGuild(UUID player, GuildEntity guildEntity) {
        return getMemberEntity(guildEntity, player) != null;
    }

    @Override
    public boolean inSameGuild(UUID player, UUID player2) {
        final GuildEntity guildEntity = getGuildByPlayer(player);
        if (guildEntity == null) return false;
        return getMemberEntity(guildEntity, player2) != null;
    }

    @Override
    public boolean isInAGuild(UUID player) {
        return getGuildByPlayer(player) != null;
    }

    @Override
    public boolean isOfficer(UUID player, GuildEntity guildEntity) {
        GuildMemberEntity guildMemberEntity = getMemberEntity(guildEntity, player);
        if (guildMemberEntity != null) {
            return guildMemberEntity.isOfficer();
        }
        return false;
    }
}

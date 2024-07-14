package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.GuildMemberEntity;
import net.tazpvp.tazpvp.data.services.GuildMemberService;
import net.tazpvp.tazpvp.data.services.GuildService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class GuildServiceImpl implements GuildService {
    private final GuildMemberService guildMemberService;

    public GuildServiceImpl(GuildMemberService guildMemberService) {
        this.guildMemberService = guildMemberService;
    }

    @Override
    public Dao<GuildEntity, Integer> getUserDao() {
        return null;
    }

    @Override
    public GuildEntity createGuild(String name, UUID owner) {
        final GuildEntity guildEntity = new GuildEntity();

        guildEntity.setName(name);
        guildEntity.setOwner(owner);

        saveGuild(guildEntity);

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
    public void addMember(GuildEntity guild, GuildMemberEntity member) {
        guild.getMembers().add(member);
    }

    @Override
    public GuildEntity getGuildByPlayer(UUID player) throws SQLException {
        GuildMemberEntity member = guildMemberService.getGuildMemberByUUID(player);

        if (member != null) {
            return member.getGuildEntity();
        }

        return null;
    }

    @Override
    public GuildEntity getGuild(int id) {
        try {
            return getUserDao().queryForId(id);
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
    public List<GuildEntity> getAllGuilds() {
        try {
            return getUserDao().queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

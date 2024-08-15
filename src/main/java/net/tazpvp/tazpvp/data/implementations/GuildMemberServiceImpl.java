package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.SneakyThrows;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.GuildMemberEntity;
import net.tazpvp.tazpvp.data.services.GuildMemberService;

import java.sql.SQLException;
import java.util.UUID;

public class GuildMemberServiceImpl implements GuildMemberService {
    private Dao<GuildMemberEntity, Integer> userDao;
    @Override
    public Dao<GuildMemberEntity, Integer> getUserDao() {
        if (userDao == null) {
            try {
                userDao = DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), GuildMemberEntity.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return userDao;
    }

    @Override
    public GuildMemberEntity createGuildMemberEntity(UUID pUUID, GuildEntity guild) {
        final GuildMemberEntity guildMemberEntity = new GuildMemberEntity();

        guildMemberEntity.setPUUID(pUUID);
        guildMemberEntity.setGuildEntity(guild);

        saveGuildMemberEntity(guildMemberEntity);

        return guildMemberEntity;
    }

    @Override
    public void saveGuildMemberEntity(GuildMemberEntity guildMember) {
        try {
            getUserDao().createOrUpdate(guildMember);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public GuildMemberEntity getGuildMemberByUUID(UUID id) {
        return getUserDao().queryBuilder()
                .where().eq("pUUID", id)
                .queryForFirst();
    }

    @Override
    public GuildMemberEntity getGuildMemberById(int id) {
        try {
            return getUserDao().queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void promoteMember(UUID id) {
        try {
            GuildMemberEntity member = getUserDao().queryBuilder()
                    .where().eq("pUUID", id).queryForFirst();
            if (member != null) {
                member.setOfficer(true);
                saveGuildMemberEntity(member);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void demoteMember(UUID id) {
        try {
            GuildMemberEntity member = getUserDao().queryBuilder()
                    .where().eq("pUUID", id).queryForFirst();
            if (member != null) {
                member.setOfficer(false);
                saveGuildMemberEntity(member);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMember(GuildMemberEntity guildMemberEntity) {
        try {
            getUserDao().delete(guildMemberEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

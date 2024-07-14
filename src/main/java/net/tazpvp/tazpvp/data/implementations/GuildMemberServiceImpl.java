package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.GuildMemberEntity;
import net.tazpvp.tazpvp.data.services.GuildMemberService;

import java.sql.SQLException;
import java.util.UUID;

public class GuildMemberServiceImpl implements GuildMemberService {
    @Override
    public Dao<GuildMemberEntity, Integer> getUserDao() {
        return null;
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

    @Override
    public GuildMemberEntity getGuildMemberByUUID(UUID id) throws SQLException {
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
}

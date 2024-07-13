package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.services.GuildService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class GuildServiceImpl implements GuildService {
    @Override
    public Dao<GuildEntity, UUID> getUserDao() {
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
    public void addMember(int guild, UUID member) {
        GuildEntity guildEntity = getGuild(guild);

        //guildEntity.getMembers().add(member);
    }

    @Override
    public GuildEntity getGuildByPlayer(UUID player) {
        return null;
    }

    @Override
    public GuildEntity getGuild(int id) {
        return null;
    }

    @Override
    public void deleteGuild(UUID player) {

    }

    @Override
    public void deleteGuild(int guild) {

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

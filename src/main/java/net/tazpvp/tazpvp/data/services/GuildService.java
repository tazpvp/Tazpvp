package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.GuildEntity;

import java.util.List;
import java.util.UUID;

public interface GuildService extends DataService {
    Dao<GuildEntity, UUID> getUserDao();

    GuildEntity createGuild(String name, UUID owner);
    void saveGuild(GuildEntity guild);
    void addMember(int guild, UUID member);
    GuildEntity getGuildByPlayer(UUID player);
    GuildEntity getGuild(int id);
    void deleteGuild(UUID player);
    void deleteGuild(int guild);
    List<GuildEntity> getAllGuilds();
}

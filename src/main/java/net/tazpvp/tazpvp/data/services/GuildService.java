package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.GuildMemberEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface GuildService extends DataService {
    Dao<GuildEntity, Integer> getUserDao();

    GuildEntity createGuild(String name, UUID owner);
    void saveGuild(GuildEntity guild);
    void addMember(GuildEntity guild, GuildMemberEntity member);
    GuildEntity getGuildByPlayer(UUID player) throws SQLException;
    GuildEntity getGuild(int id);
    void deleteGuild(GuildEntity guild);
    List<GuildEntity> getAllGuilds();
}

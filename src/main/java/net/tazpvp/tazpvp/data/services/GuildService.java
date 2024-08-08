package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.GuildMemberEntity;

import java.util.List;
import java.util.UUID;

public interface GuildService extends DataService {
    Dao<GuildEntity, Integer> getUserDao();

    void createGuild(String name, UUID owner);
    void saveGuild(GuildEntity guild);
    void addMember(GuildEntity guild, GuildMemberEntity member);
    GuildEntity getGuildByPlayer(UUID player);
    GuildEntity getGuild(int id);
    void deleteGuild(GuildEntity guild);
    void messageAll(GuildEntity guild, String msg);
    List<UUID> getAllOnlineMembers(GuildEntity guild);
    List<UUID> getAllMembers(GuildEntity guild);
    List<UUID> getOnlineOfficers(GuildEntity guild);
    List<UUID> getOfficers(GuildEntity guild);
    GuildMemberEntity getMemberEntity(GuildEntity guild, UUID uuid);
    List<GuildEntity> getAllGuilds();
    List<GuildEntity> getAllGuildsSorted();
}

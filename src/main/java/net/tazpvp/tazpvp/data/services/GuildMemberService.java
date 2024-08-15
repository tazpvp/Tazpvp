package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.GuildMemberEntity;

import java.util.UUID;

public interface GuildMemberService extends DataService {
    Dao<GuildMemberEntity, Integer> getUserDao();

    GuildMemberEntity createGuildMemberEntity(UUID pUUID, GuildEntity guild);

    void saveGuildMemberEntity(GuildMemberEntity guildMember);

    GuildMemberEntity getGuildMemberByUUID(UUID id);

    GuildMemberEntity getGuildMemberById(int id);

    void promoteMember(UUID id);

    void demoteMember(UUID id);

    void deleteMember(GuildMemberEntity guildMemberEntity);

}

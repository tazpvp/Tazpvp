package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.TalentEntity;

import java.util.UUID;

public interface TalentService extends DataService {
    Dao<TalentEntity, UUID> getUserDao();
    void saveTalentEntity(TalentEntity talentEntity);
    TalentEntity getTalentEntity(UUID uuid);
    boolean talentEntityExists(UUID uuid);
    TalentEntity getOrDefault(UUID uuid);
    void removeTalentEntity(TalentEntity talentEntity);
}

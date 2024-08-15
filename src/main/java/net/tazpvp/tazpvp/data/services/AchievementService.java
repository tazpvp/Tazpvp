package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;

import java.util.UUID;

public interface AchievementService extends DataService {
    Dao<AchievementEntity, UUID> getUserDao();
    void saveAchievementEntity(AchievementEntity AchievementEntity);
    AchievementEntity createDefault(UserAchievementEntity userAchievementEntity);

}

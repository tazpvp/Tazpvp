package net.tazpvp.tazpvp.utils.data;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.utils.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.utils.data.entity.AchievementEntity;

import java.util.UUID;

public interface AchievementService extends DataService {

    Dao<AchievementEntity, UUID> getUserDao();
    void saveAchievementEntity(AchievementEntity AchievementEntity);
    AchievementEntity getAchievementEntity(UUID uuid);
    boolean achievementEntityExists(UUID uuid);
    AchievementEntity getOrDefault(UUID uuid);
}

package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;

import java.util.UUID;

public interface UserAchievementService extends DataService {

    Dao<UserAchievementEntity, UUID> getUserDao();
    void saveUserAchievementEntity(UserAchievementEntity UserAchievementEntity);
    UserAchievementEntity getUserAchievementEntity(UUID uuid);
    boolean userAchievementEntityExists(UUID uuid);
    UserAchievementEntity getOrDefault(UUID uuid);
}

package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.services.AchievementService;

import java.sql.SQLException;
import java.util.UUID;

public class AchievementServiceImpl implements AchievementService {
    @Override
    public Dao<AchievementEntity, Integer> getUserDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), AchievementEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAchievementEntity(AchievementEntity achievementEntity) {
        try {
            getUserDao().createOrUpdate(achievementEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AchievementEntity getAchievementEntity(int id) {
        try {
            AchievementEntity achievementEntity = getUserDao().queryForId(id);
            if (achievementEntity == null) {
                achievementEntity = new AchievementEntity();
                achievementEntity.setId(id);
                achievementEntity.setCompleted(false);
                achievementEntity.setCollected(false);
            }

            return achievementEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AchievementEntity createBasic() {
        AchievementEntity achievementEntity = new AchievementEntity();

        saveAchievementEntity(achievementEntity);

        return achievementEntity;
    }
}

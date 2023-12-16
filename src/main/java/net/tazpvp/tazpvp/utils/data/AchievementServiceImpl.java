package net.tazpvp.tazpvp.utils.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.data.entity.AchievementEntity;

import java.sql.SQLException;
import java.util.UUID;

public class AchievementServiceImpl implements AchievementService{
    @Override
    public Dao<AchievementEntity, UUID> getUserDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), AchievementEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAchievementEntity(final AchievementEntity achievementEntity) {
        try {
            getUserDao().createOrUpdate(achievementEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AchievementEntity getAchievementEntity(final UUID uuid) {
        try {
            getUserDao().queryForId(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean achievementEntityExists(final UUID uuid) {
        try {
            getUserDao().idExists(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public AchievementEntity getOrDefault(final UUID uuid) {
        final AchievementEntity achievementEntity = getAchievementEntity(uuid);

        if (achievementEntity == null) {
            final AchievementEntity blankAchievementEntity = new AchievementEntity();
            blankAchievementEntity.setUuid(uuid);

            return blankAchievementEntity;
        }

        return achievementEntity;
    }
}

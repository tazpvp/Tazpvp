package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.services.AchievementService;
import net.tazpvp.tazpvp.data.services.UserAchievementService;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.UUID;

public class UserAchievementServiceImpl implements UserAchievementService {
    private Dao<UserAchievementEntity, UUID> userDao;
    @Override
    public Dao<UserAchievementEntity, UUID> getUserDao() {
        if (userDao != null) {
            return userDao;
        }
        try {
            userDao = DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), UserAchievementEntity.class);
            return userDao;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUserAchievementEntity(final UserAchievementEntity userAchievementEntity) {
        try {
            getUserDao().createOrUpdate(userAchievementEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserAchievementEntity getUserAchievementEntity(final UUID uuid) {
        try {
            return getUserDao().queryForId(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean userAchievementEntityExists(final UUID uuid) {
        try {
            return getUserDao().idExists(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserAchievementEntity getOrDefault(final UUID uuid) {
        final UserAchievementEntity userAchievementEntity = getUserAchievementEntity(uuid);

        if (userAchievementEntity == null) {
            final UserAchievementEntity blankUserAchievementEntity = new UserAchievementEntity();
            blankUserAchievementEntity.setUuid(uuid);

            try {
                getUserDao().createOrUpdate(blankUserAchievementEntity);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            AchievementService achievementService = new AchievementServiceImpl();
            blankUserAchievementEntity.setAdept(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setAgile(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setBowling(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setArtisan(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setCharm(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setCraftsman(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setError(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setSuperior(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setGamble(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setGrinder(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setGladiator(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setHarvester(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setLegend(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setMerchant(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setRehab(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setSkilled(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setZorgin(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setSpeedrunner(achievementService.createDefault(blankUserAchievementEntity));

            saveUserAchievementEntity(blankUserAchievementEntity);

            return blankUserAchievementEntity;
        }

        return userAchievementEntity;
    }
}

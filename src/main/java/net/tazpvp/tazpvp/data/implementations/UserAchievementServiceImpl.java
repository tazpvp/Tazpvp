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

            AchievementService achievementService = new AchievementServiceImpl();
            blankUserAchievementEntity.setAdept(achievementService.createBasic());
            blankUserAchievementEntity.setAgile(achievementService.createBasic());
            blankUserAchievementEntity.setBowling(achievementService.createBasic());
            blankUserAchievementEntity.setArtisan(achievementService.createBasic());
            blankUserAchievementEntity.setCharm(achievementService.createBasic());
            blankUserAchievementEntity.setCraftsman(achievementService.createBasic());
            blankUserAchievementEntity.setError(achievementService.createBasic());
            blankUserAchievementEntity.setSuperior(achievementService.createBasic());
            blankUserAchievementEntity.setGamble(achievementService.createBasic());
            blankUserAchievementEntity.setGrinder(achievementService.createBasic());
            blankUserAchievementEntity.setGladiator(achievementService.createBasic());
            blankUserAchievementEntity.setHarvester(achievementService.createBasic());
            blankUserAchievementEntity.setLegend(achievementService.createBasic());
            blankUserAchievementEntity.setMerchant(achievementService.createBasic());
            blankUserAchievementEntity.setRehab(achievementService.createBasic());
            blankUserAchievementEntity.setSkilled(achievementService.createBasic());
            blankUserAchievementEntity.setZorgin(achievementService.createBasic());
            blankUserAchievementEntity.setSpeedrunner(achievementService.createBasic());

            saveUserAchievementEntity(blankUserAchievementEntity);

            return blankUserAchievementEntity;
        }

        return userAchievementEntity;
    }
}

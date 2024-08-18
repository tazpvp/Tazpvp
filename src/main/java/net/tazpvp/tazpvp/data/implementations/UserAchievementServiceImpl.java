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
    public void saveUserAchievementEntity(final UserAchievementEntity achievementEntity) {
        AchievementService achievementService = new AchievementServiceImpl();
        try {
            achievementService.saveAchievementEntity(achievementEntity.getAdept());
            achievementService.saveAchievementEntity(achievementEntity.getAgile());
            achievementService.saveAchievementEntity(achievementEntity.getBowling());
            achievementService.saveAchievementEntity(achievementEntity.getCraftsman());
            achievementService.saveAchievementEntity(achievementEntity.getArtisan());
            achievementService.saveAchievementEntity(achievementEntity.getError());
            achievementService.saveAchievementEntity(achievementEntity.getGladiator());
            achievementService.saveAchievementEntity(achievementEntity.getCharm());
            achievementService.saveAchievementEntity(achievementEntity.getGamble());
            achievementService.saveAchievementEntity(achievementEntity.getHarvester());
            achievementService.saveAchievementEntity(achievementEntity.getGrinder());
            achievementService.saveAchievementEntity(achievementEntity.getMerchant());
            achievementService.saveAchievementEntity(achievementEntity.getLegend());
            achievementService.saveAchievementEntity(achievementEntity.getRehab());
            achievementService.saveAchievementEntity(achievementEntity.getSpeedrunner());
            achievementService.saveAchievementEntity(achievementEntity.getSuperior());
            achievementService.saveAchievementEntity(achievementEntity.getSkilled());
            achievementService.saveAchievementEntity(achievementEntity.getZorgin());
            getUserDao().createOrUpdate(achievementEntity);
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

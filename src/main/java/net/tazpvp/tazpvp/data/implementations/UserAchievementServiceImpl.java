package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.services.AchievementService;
import net.tazpvp.tazpvp.data.services.UserAchievementService;

import java.sql.SQLException;
import java.util.UUID;

public class UserAchievementServiceImpl implements UserAchievementService {
    @Override
    public Dao<UserAchievementEntity, UUID> getUserDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), UserAchievementEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUserAchievementEntity(final UserAchievementEntity achievementEntity) {
        AchievementService achievementService = new AchievementServiceImpl();
        try {
            getUserDao().createOrUpdate(achievementEntity);
            achievementService.saveAchievementEntity(achievementEntity.getAgileAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getBowlingAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getCraftsmanAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getArtisanAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getErrorAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getGladiatorAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getCharmAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getGambleAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getHarvesterAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getGrinderAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getMerchantAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getLegendAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getRehabAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getSpeedrunnerAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getSuperiorAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getSkilledAchievementEntity());
            achievementService.saveAchievementEntity(achievementEntity.getZorginAchievementEntity());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserAchievementEntity getUserAchievementEntity(final UUID uuid) {
        try {
            getUserDao().queryForId(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean userAchievementEntityExists(final UUID uuid) {
        try {
            getUserDao().idExists(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public UserAchievementEntity getOrDefault(final UUID uuid) {
        final UserAchievementEntity userAchievementEntity = getUserAchievementEntity(uuid);

        if (userAchievementEntity == null) {
            final UserAchievementEntity blankUserAchievementEntity = new UserAchievementEntity();
            blankUserAchievementEntity.setUuid(uuid);
            AchievementService achievementService = new AchievementServiceImpl();
            blankUserAchievementEntity.setAdeptAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setAgileAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setBowlingAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setArtisanAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setCharmAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setCraftsmanAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setErrorAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setSuperiorAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setGambleAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setGrinderAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setGladiatorAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setHarvesterAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setLegendAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setMerchantAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setRehabAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setSkilledAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setZorginAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));
            blankUserAchievementEntity.setSpeedrunnerAchievementEntity(achievementService.createDefault(blankUserAchievementEntity));

            return blankUserAchievementEntity;
        }

        return userAchievementEntity;
    }
}

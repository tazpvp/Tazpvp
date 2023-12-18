package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.ExpirationRankEntity;
import net.tazpvp.tazpvp.data.entity.UserRankEntity;
import net.tazpvp.tazpvp.data.services.ExpirationRankService;
import net.tazpvp.tazpvp.data.services.GameRankService;
import net.tazpvp.tazpvp.data.services.UserRankService;

import java.sql.SQLException;
import java.util.UUID;

public class UserRankServiceImpl implements UserRankService {
    @Override
    public Dao<UserRankEntity, UUID> getUserDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), UserRankEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUserRankEntity(UserRankEntity userRankEntity) {
        try {
            getUserDao().createOrUpdate(userRankEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRankEntity getUserRankEntity(UUID uuid) {
        try {
            return getUserDao().queryForId(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean userRankEntityExists(UUID uuid) {
        try {
            return getUserDao().idExists(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRankEntity getOrDefault(UUID uuid) {
        final UserRankEntity userRankEntity = getUserRankEntity(uuid);

        if (userRankEntity == null) {
            final UserRankEntity userRankEntityTemp = new UserRankEntity();
            userRankEntityTemp.setUuid(uuid);
            userRankEntityTemp.setDeathParticle(null);
            userRankEntityTemp.setArrowParticle(null);
            ForeignCollection<ExpirationRankEntity> expirationRankEntities = userRankEntityTemp.getRanks();

            final ExpirationRankService expirationRankService = new ExpirationRankServiceImpl();
            final GameRankService gameRankService = new GameRankServiceImpl();

            final ExpirationRankEntity expirationRankEntity = expirationRankService
                    .createExpirationRank(userRankEntityTemp,
                            gameRankService.getOrCreateIfNotExists("default", "", 0),
                            0L);

            expirationRankEntities.add(expirationRankEntity);
            userRankEntityTemp.setRanks(expirationRankEntities);

            return userRankEntityTemp;
        }

        return userRankEntity;
    }
}

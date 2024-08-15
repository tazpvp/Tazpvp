package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.ExpirationRankEntity;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.entity.UserRankEntity;
import net.tazpvp.tazpvp.data.services.ExpirationRankService;

import java.sql.SQLException;

public class ExpirationRankServiceImpl implements ExpirationRankService {
    @Override
    public Dao<ExpirationRankEntity, Integer> getUserDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), ExpirationRankEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveExpirationRank(ExpirationRankEntity expirationRankEntity) {
        try {
            getUserDao().createOrUpdate(expirationRankEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExpirationRankEntity createExpirationRank(UserRankEntity userRankEntity, GameRankEntity gameRankEntity, long expiresTimestamp) {
        final ExpirationRankEntity expirationRankEntity = new ExpirationRankEntity();
        expirationRankEntity.setUserRankEntity(userRankEntity);
        expirationRankEntity.setGameRankEntity(gameRankEntity);
        expirationRankEntity.setExpirationTimestamp(expiresTimestamp);

        return expirationRankEntity;
    }

    @Override
    public boolean hasRankExpired(ExpirationRankEntity expirationRankEntity) {
        if (expirationRankEntity.getExpirationTimestamp() == 0L) {
            return false;
        }
        return expirationRankEntity.getExpirationTimestamp() < System.currentTimeMillis();
    }
}

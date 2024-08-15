package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.ExpirationRankEntity;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.entity.UserRankEntity;

public interface ExpirationRankService extends DataService {
    Dao<ExpirationRankEntity, Integer> getUserDao();
    void saveExpirationRank(ExpirationRankEntity expirationRankEntity);
    ExpirationRankEntity createExpirationRank(UserRankEntity userRankEntity, GameRankEntity gameRankEntity, long expiresTimestamp);
    boolean hasRankExpired(ExpirationRankEntity expirationRankEntity);
}

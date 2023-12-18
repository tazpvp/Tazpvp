package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.RankEntity;
import net.tazpvp.tazpvp.data.entity.UserRankEntity;

import java.util.UUID;

public interface UserRankService extends DataService {
    Dao<UserRankEntity, UUID> getUserDao();
    void saveUserRankEntity(UserRankEntity userRankEntity);
    UserRankEntity getUserRankEntity(UUID uuid);
    boolean userRankEntityExists(UUID uuid);
    UserRankEntity getOrDefault(UUID uuid);
}

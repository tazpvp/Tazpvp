package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.entity.RankEntity;
import net.tazpvp.tazpvp.data.DataService;

import java.util.UUID;

public interface RankService extends DataService {

    Dao<RankEntity, UUID> getUserDao();
    void saveRankEntity(RankEntity rankEntity);
    RankEntity getRankEntity(UUID uuid);
    boolean rankEntityExists(UUID uuid);
    RankEntity getOrDefault(UUID uuid);
}

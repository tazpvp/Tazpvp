package net.tazpvp.tazpvp.utils.data;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.utils.data.entity.RankEntity;

import java.util.UUID;

public interface RankService extends DataService {

    Dao<RankEntity, UUID> getUserDao();
    void saveRankEntity(RankEntity rankEntity);
    RankEntity getRankEntity(UUID uuid);
    boolean rankEntityExists(UUID uuid);
    RankEntity getOrDefault(UUID uuid);
}

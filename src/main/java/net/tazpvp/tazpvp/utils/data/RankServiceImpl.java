package net.tazpvp.tazpvp.utils.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.data.entity.RankEntity;

import java.sql.SQLException;
import java.util.UUID;

public class RankServiceImpl implements RankService{
    @Override
    public Dao<RankEntity, UUID> getUserDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), RankEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveRankEntity(final RankEntity rankEntity) {
        try {
            getUserDao().createOrUpdate(rankEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RankEntity getRankEntity(final UUID uuid) {
        try {
            return getUserDao().queryForId(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean rankEntityExists(final UUID uuid) {
        try {
            return getUserDao().idExists(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RankEntity getOrDefault(final UUID uuid) {
        if (rankEntityExists(uuid)) {
            return getRankEntity(uuid);
        } else {
            return new RankEntity(uuid, false, Rank.DEFAULT.toString(), null, null, null, 0L);
        }
    }
}

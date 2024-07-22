package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.AllArgsConstructor;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.database.PostgresqlDatabase;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.services.PlayerStatService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class PlayerStatServiceImpl implements PlayerStatService {
    @Override
    public Dao<PlayerStatEntity, UUID> getDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), PlayerStatEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerStatEntity getOrDefault(UUID uuid) {
        PlayerStatEntity playerStatEntity = null;
        try {
            playerStatEntity = getDao().queryForId(uuid);
            if (playerStatEntity == null) {
                playerStatEntity = new PlayerStatEntity();
                playerStatEntity.setUuid(uuid);
                playerStatEntity.setXp(0);
                playerStatEntity.setLevel(1);
                playerStatEntity.setKills(0);
                playerStatEntity.setDeaths(0);
                playerStatEntity.setRebirths(0);
                playerStatEntity.setPlaytime(0);
            }
            return playerStatEntity;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(PlayerStatEntity playerStatEntity) {
        try {
            getDao().createOrUpdate(playerStatEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(PlayerStatEntity playerStatEntity) {
        try {
            getDao().delete(playerStatEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlayerStatEntity> getTop10Most(String columnName) {
        try {
            return getDao().queryBuilder().orderBy(columnName, true).limit(10L).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

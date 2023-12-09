package net.tazpvp.tazpvp.utils.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.tazpvp.tazpvp.utils.data.database.PostgresqlDatabase;
import net.tazpvp.tazpvp.utils.data.entity.RankEntity;

import java.sql.SQLException;
import java.util.UUID;

public interface RankService {
    default void createTableIfNotExists(final PostgresqlDatabase postgresqlDatabase) {
        final ConnectionSource connectionSource = postgresqlDatabase.getConnectionSource();

        try {
            TableUtils.createTableIfNotExists(connectionSource, RankEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Dao<RankEntity, UUID> getUserDao();
    void saveRankEntity(RankEntity rankEntity);
    RankEntity getRankEntity(UUID uuid);
    boolean rankEntityExists(UUID uuid);
    RankEntity getOrDefault(UUID uuid);
}

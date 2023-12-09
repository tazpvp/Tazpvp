package net.tazpvp.tazpvp.utils.data;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import net.tazpvp.tazpvp.utils.data.database.PostgresqlDatabase;
import net.tazpvp.tazpvp.utils.data.entity.RankEntity;

import java.sql.SQLException;

public interface DataService {
    default void createTableIfNotExists(final PostgresqlDatabase postgresqlDatabase, final Class<?> clazz) {
        final ConnectionSource connectionSource = postgresqlDatabase.getConnectionSource();

        try {
            TableUtils.createTableIfNotExists(connectionSource, clazz);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

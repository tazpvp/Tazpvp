package net.tazpvp.tazpvp.utils.data.database;

import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class PostgresqlDatabase {
    @Getter
    private final HikariConfig hikariConfig;
    private final HikariDataSource hikariDataSource;
    @Getter
    private final ConnectionSource connectionSource;

    public PostgresqlDatabase(final String connectionUrl, final String username, final String password) throws SQLException {
        this.hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(connectionUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        this.hikariDataSource = new HikariDataSource(hikariConfig);
        this.connectionSource = new DataSourceConnectionSource(hikariDataSource, hikariConfig.getJdbcUrl());
    }

    public void close() {
        try {
            hikariDataSource.close();
            connectionSource.close();
        } catch (Exception p_e) {
            throw new RuntimeException(p_e);
        }
    }
}

package net.tazpvp.tazpvp.utils.data;

import net.tazpvp.tazpvp.Tazpvp;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.module.Column;
import world.ntdi.postglam.sql.module.Row;
import world.ntdi.postglam.sql.module.Table;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public final class RankData {
    private static final Table table;

    static {
        try {
            table = new Table(Tazpvp.getDatabase(), "ranks", Map.entry("id", DataTypes.UUID), new LinkedHashMap<>(Map.of("rank", DataTypes.TEXT, "prefix", DataTypes.TEXT)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Set rank of UUID, doesn't have to already be in table.
     * @param uuid UUID target
     * @param rank Vault Rank
     */
    public static void setRank(@Nonnull final UUID uuid, @Nonnull final String rank) {
        if (!hasRank(uuid)) {
            initRank(uuid, rank);
            return;
        }

        try {
            new Row(table, uuid.toString()).update(new Column(table, "rank"), rank);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        final String rankPrefix = "prefix"; //TODO: Hook into vault
        setPrefix(uuid, rankPrefix);
    }

    /**
     * Initialize uuid into table
     * @param uuid UUID Target
     * @param rank Vault Rank
     */
    private static void initRank(@Nonnull final UUID uuid, @Nonnull final String rank) {
        final String rankPrefix = "prefix"; //TODO: Hook into vault
        try {
            new Row(table, uuid.toString(), rank, rankPrefix);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set prefix of row, will do nothing if not already inside of table
     * @param uuid UUID target
     * @param prefix New Prefix
     */
    public static void setPrefix(@Nonnull final UUID uuid, @Nonnull final String prefix) {
        if (hasRank(uuid)) {
            try {
                new Row(table, uuid.toString()).update(new Column(table, "prefix"), prefix);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Check if they are inside rank table
     * @param uuid UUID target
     * @return if player is inside rank table
     */
    public static boolean hasRank(@Nonnull final UUID uuid) {
        try {
            return table.doesRowExist(uuid.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the rank of UUID - Will throw runtime exception if not in table
     * @param uuid UUID target
     * @return String of UUID's rank
     */
    public static String getRank(@Nonnull final UUID uuid) {
        try {
            return (String) new Row(table, uuid.toString()).fetch(new Column(table, "rank"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get prefix of UUID - Will throw runtime exception if not in table
     * @param uuid UUID target
     * @return String of UUID's prefix
     */
    public static String getPrefix(@Nonnull final UUID uuid) {
        try {
            return (String) new Row(table, uuid.toString()).fetch(new Column(table, "prefix"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

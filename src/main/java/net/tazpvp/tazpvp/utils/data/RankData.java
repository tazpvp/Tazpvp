package net.tazpvp.tazpvp.utils.data;

import world.ntdi.nrcore.utils.sql.SQLHelper;

import javax.annotation.Nonnull;
import java.util.UUID;

public final class RankData {

    private static final String NAME = "ranks";
    private static final String ID_COLUMN = "id";

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

        SQLHelper.updateValue(NAME, ID_COLUMN, uuid.toString(), "rank", rank);
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
        SQLHelper.initializeValues(NAME, "ID, RANK, PREFIX", "'" + uuid.toString() + "', '" + rank + "', '" + rankPrefix + "'");
    }

    /**
     * Set prefix of row, will do nothing if not already inside of table
     * @param uuid UUID target
     * @param prefix New Prefix
     */
    public static void setPrefix(@Nonnull final UUID uuid, @Nonnull final String prefix) {
        SQLHelper.updateValue(NAME, ID_COLUMN, uuid.toString(), "prefix", prefix);
    }

    /**
     * Check if they are inside rank table
     * @param uuid UUID target
     * @return if player is inside rank table
     */
    public static boolean hasRank(@Nonnull final UUID uuid) {
        return SQLHelper.ifRowExists(NAME, ID_COLUMN, uuid.toString());
    }

    /**
     * Get the rank of UUID - Will throw runtime exception if not in table
     * @param uuid UUID target
     * @return String of UUID's rank
     */
    public static String getRank(@Nonnull final UUID uuid) {
        return SQLHelper.getString(NAME, ID_COLUMN, uuid.toString(), 2); // INDEX = 2 bc that is index of rank column
    }

    /**
     * Get prefix of UUID - Will throw runtime exception if not in table
     * @param uuid UUID target
     * @return String of UUID's prefix
     */
    public static String getPrefix(@Nonnull final UUID uuid) {
        return SQLHelper.getString(NAME, ID_COLUMN, uuid.toString(), 3); // INDEX = 3 bc that is index of prefix column
    }
}

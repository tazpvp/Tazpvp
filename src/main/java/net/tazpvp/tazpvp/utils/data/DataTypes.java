package net.tazpvp.tazpvp.utils.data;

import javax.annotation.Nonnull;

public enum DataTypes {
    COINS("coins", 2, true),
    XP("xp", 3, true),
    LEVEL("level", 4, true),
    KILLS("kills", 5, true),
    DEATHS("deaths", 6, true),
    TOPKILLSTREAK("top_ks", 7, true),
    PRESTIGE("prestige", 8, true),
    REBIRTH("rebirth", 9, true),
    PREMIUM("premium", 10, false),
    PREFIX("prefix", 11, true),
    DUELWINS("duel_wins", 12, true),
    DIVISION("division", 13, true),
    PLAYTIMEUNIX("playtime", 14, true),
    GUILD_ID("guild_id", 15, false),
    TALENTS("talents", 16, false),
    ACHIEVEMENTS("achievements", 17, false);

    private final String columnName;
    private final int columnIndex;
    private final boolean quantitative;

    DataTypes(@Nonnull final String columnName, final int columnIndex, final boolean quantitative) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.quantitative = quantitative;
    }

    /**
     * Get the column name
     * @return the column's name
     */
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * Get the index of a column
     * @return The column's index
     */
    public int getColumnIndex() {
        return this.columnIndex;
    }

    /**
     * Check if the value is numerical or not
     * @return True if quantitative, else false
     */
    public boolean isQuantitative() {
        return this.quantitative;
    }
}

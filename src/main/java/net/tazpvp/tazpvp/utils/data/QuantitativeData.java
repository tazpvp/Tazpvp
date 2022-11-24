package net.tazpvp.tazpvp.utils.data;

import javax.annotation.Nonnull;

public enum QuantitativeData {
    COINS("coins", 1),
    XP("xp", 2),
    LEVEL("level", 3),
    KILLS("kills", 4),
    DEATHS("deaths", 5),
    TOPKILLSTREAK("top_ks", 6),
    PRESTIGE("prestige", 7),
    REBIRTH("rebirth", 8),
    PLAYTIMEUNIX("playtime", 9),
    TALENTS("talents", 10),
    ACHIEVEMENTS("achievements", 11);

    private final String columnName;
    private final int columnIndex;

    QuantitativeData(@Nonnull final String columnName, final int columnIndex) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
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
}

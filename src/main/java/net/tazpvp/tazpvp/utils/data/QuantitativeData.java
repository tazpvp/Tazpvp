package net.tazpvp.tazpvp.utils.data;

import javax.annotation.Nonnull;

public enum QuantitativeData {
    COINS("coins", 2),
    XP("xp", 3),
    LEVEL("level", 4),
    KILLS("kills", 5),
    DEATHS("deaths", 6),
    TOPKILLSTREAK("top_ks", 7),
    PRESTIGE("prestige", 8),
    REBIRTH("rebirth", 9),
    PLAYTIMEUNIX("playtime", 10),
    TALENTS("talents", 11),
    ACHIEVEMENTS("achievements", 12);

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

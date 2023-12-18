package net.tazpvp.tazpvp.game.duels.divisions;

import net.tazpvp.tazpvp.utils.enums.CC;

public enum Divisions {
    BRONZE_I(CC.DARK_RED + "Bronze I", 1),
    BRONZE_II(CC.DARK_RED + "Bronze II", 2),
    BRONZE_III(CC.DARK_RED + "Bronze III", 3),
    BRONZE_IV(CC.DARK_RED + "Bronze IV", 4),
    BRONZE_V(CC.DARK_RED + "Bronze V", 5),
    SILVER_I(CC.GRAY + "Silver I", 6),
    SILVER_II(CC.GRAY + "Silver II", 7),
    SILVER_III(CC.GRAY + "Silver III", 8),
    SILVER_IV(CC.GRAY + "Silver IV", 9),
    SILVER_V(CC.GRAY + "Silver V", 10),
    GOLD_I(CC.YELLOW + "Gold I", 11),
    GOLD_II(CC.YELLOW + "Gold II", 12),
    GOLD_III(CC.YELLOW + "Gold III", 13),
    GOLD_IV(CC.YELLOW + "Gold IV", 14),
    GOLD_V(CC.YELLOW + "Gold V", 15),
    PLATINUM_I(CC.DARK_BLUE + "Platinum I", 16),
    PLATINUM_II(CC.DARK_BLUE + "Platinum II", 17),
    PLATINUM_III(CC.DARK_BLUE + "Platinum III", 18),
    PLATINUM_IV(CC.DARK_BLUE + "Platinum IV", 19),
    PLATINUM_V(CC.DARK_BLUE + "Platinum V", 20),
    DIAMOND_I(CC.BLUE + "Diamond I", 21),
    DIAMOND_II(CC.BLUE + "Diamond II", 22),
    DIAMOND_III(CC.BLUE + "Diamond III" ,23),
    DIAMOND_IV(CC.BLUE + "Diamond IV", 24),
    DIAMOND_V(CC.BLUE + "Diamond V", 25),
    CHAMPION_I(CC.LIGHT_PURPLE + "Champion I", 26),
    CHAMPION_II(CC.LIGHT_PURPLE + "Champion II", 27),
    CHAMPION_III(CC.LIGHT_PURPLE + "Champion III", 28),
    CHAMPION_IV(CC.LIGHT_PURPLE + "Champion IV", 29),
    CHAMPION_V(CC.LIGHT_PURPLE + "Champion V", 30),
    GRAND_CHAMPION_I(CC.RED + "Grand Champion I", 31),
    GRAND_CHAMPION_II(CC.RED + "Grand Champion II", 32),
    GRAND_CHAMPION_III(CC.RED + "Grand Champion III", 33),
    GRAND_CHAMPION_IV(CC.RED + "Grand Champion IV", 34),
    GRAND_CHAMPION_V(CC.RED + "Grand Champion V", 35),
    SUPERSONIC_LEGEND(CC.DARK_PURPLE + "" + CC.BOLD + "Supersonic Legend", 36);

    private final String title;
    private final int height;

    private static final Divisions[] vals = values();

    Divisions(String title, int height) {
        this.title = title;
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public int getHeight() {
        return height;
    }

    public Divisions rankUp() {
        Divisions current = this;
        Divisions above = getNext();
        if (above != null) {
            if (above.getHeight() % 5 == 1) {
                return getNext(2);
            }
        }
        return null;
    }

    public Divisions getNext() {
        return (this.equals(SUPERSONIC_LEGEND) ? null : vals[(this.ordinal() + 1) % vals.length]) ;
    }

    public Divisions getNext(int displacement) {
        return vals[(this.ordinal() + displacement) % vals.length];
    }

    public Divisions getPrevious() {
        return vals[(this.ordinal() - 1) % vals.length];
    }

    public static Divisions getDivisionFromHeight(int height) {
        for (Divisions divisions : vals) {
            if (divisions.getHeight() == height) {
                return divisions;
            }
        }
        return null;
    }
}

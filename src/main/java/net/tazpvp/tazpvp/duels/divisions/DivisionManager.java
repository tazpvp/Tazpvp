package net.tazpvp.tazpvp.duels.divisions;

import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.OfflinePlayer;

public final class DivisionManager {
    public static void PlayerWin(OfflinePlayer p) {
        Divisions divisions = Divisions.getDivisionFromHeight(PersistentData.getInt(p, DataTypes.DIVISION));

        Divisions next = divisions.rankUp();
        if (next != null) {
            PersistentData.set(p, DataTypes.DIVISION, next.getHeight());
        }
    }

    public static void PlayerLoose(OfflinePlayer p) {
        Divisions divisions = Divisions.getDivisionFromHeight(PersistentData.getInt(p, DataTypes.DIVISION));

        Divisions previous = divisions.getPrevious();
        if (previous != null) {
            PersistentData.set(p, DataTypes.DIVISION, previous.getHeight());
        }
    }
}

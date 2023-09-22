package net.tazpvp.tazpvp.utils;

import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.WeakHashMap;

public class PlaytimeUtil {
    private final static WeakHashMap<UUID, Long> loginTime = new WeakHashMap<>();

    public static void playerJoined(@Nonnull final Player p) {
        loginTime.put(p.getUniqueId(), System.currentTimeMillis());
    }

    public static void playerLeft(@Nonnull final Player p) {
        long currentTime = System.currentTimeMillis();
        long timePlayed = currentTime - loginTime.get(p.getUniqueId());
        PersistentData.set(p, DataTypes.PLAYTIMEUNIX, PersistentData.getInt(p, DataTypes.PLAYTIMEUNIX) + timePlayed);
    }

    public static long getPlayTime(final OfflinePlayer p) {
        long currTime = System.currentTimeMillis();
        long timePlayed = currTime - loginTime.get(p.getUniqueId());

        return PersistentData.getInt(p.getUniqueId(), DataTypes.PLAYTIMEUNIX) + timePlayed;
    }

    public static String secondsToDDHHMMSS(long seconds) {
        return String.format("%02dh %02dm %02ds", (seconds / 3600 % 24), (seconds / 60) % 60, seconds % 60);
    }
}

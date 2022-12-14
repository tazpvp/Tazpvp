package net.tazpvp.tazpvp.utils;

import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.WeakHashMap;

public class PlaytimeUtil {
    private static WeakHashMap<UUID, Long> playtime = new WeakHashMap<>();

    /**
     * Fired when a player joins
     * @param p The targeted player
     */
    public static void playerJoined(@Nonnull final Player p) {
        playtime.put(p.getUniqueId(), System.currentTimeMillis());
    }

    /**
     * Fired when a player leaves
     * @param p The targeted player
     */
    public static void playerLeft(@Nonnull final Player p) {
        long currentTime = System.currentTimeMillis();
        long timePlayed = currentTime - playtime.get(p.getUniqueId());
        PersistentData.set(p, DataTypes.PLAYTIMEUNIX, PersistentData.getInt(p, DataTypes.PLAYTIMEUNIX) + timePlayed);
    }

    /**
     * Convert unix time stamp to readable string
     * @param seconds the Unix seconds
     * @return new and improved readable string
     */
    public static String secondsToDDHHMMSS(long seconds) {
        return String.format("%02dh %02dm %02ds", (seconds / 3600 % 24), (seconds / 60) % 60, seconds % 60);
    }
}

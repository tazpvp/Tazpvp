package net.tazpvp.tazpvp.utils;

import net.tazpvp.tazpvp.utils.data.PlayerData;
import net.tazpvp.tazpvp.utils.data.QuantitativeData;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.WeakHashMap;

public class PlaytimeUtil {
    private static WeakHashMap<UUID, Long> playtime = new WeakHashMap<>();

    public static void playerJoined(@Nonnull final Player p) {
        playtime.put(p.getUniqueId(), System.currentTimeMillis());
    }

    public static void playerLeft(@Nonnull final Player p) {
        long currentTime = System.currentTimeMillis();
        long timePlayed = currentTime - playtime.get(p.getUniqueId());
        PlayerData.set(p, QuantitativeData.PLAYTIMEUNIX, PlayerData.get(p, QuantitativeData.PLAYTIMEUNIX) + timePlayed);
    }

    public static String secondsToDDHHMMSS(long seconds) {
        return String.format("%02dd %02dh %02dm %02ds", seconds / 86400, (seconds / 3600 % 24), (seconds / 60) % 60, seconds % 60);
    }
}

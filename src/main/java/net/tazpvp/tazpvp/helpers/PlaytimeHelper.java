package net.tazpvp.tazpvp.helpers;

import net.tazpvp.tazpvp.enums.StatEnum;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.WeakHashMap;

public class PlaytimeHelper {
    private final static WeakHashMap<UUID, Long> loginTime = new WeakHashMap<>();

    public static void playerJoined(@Nonnull final Player p) {
        loginTime.put(p.getUniqueId(), System.currentTimeMillis());
    }

    public static void playerLeft(@Nonnull final Player p) {
        if (!loginTime.containsKey(p.getUniqueId())) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        long timePlayed = currentTime - loginTime.get(p.getUniqueId());
        StatEnum.PLAYTIME.add(p.getUniqueId(), timePlayed);
    }

    public static long getPlayTime(final OfflinePlayer p) {
        long currTime = System.currentTimeMillis();
        long timePlayed = currTime - loginTime.get(p.getUniqueId());

        return StatEnum.PLAYTIME.getLong(p.getUniqueId()) + timePlayed;
    }

    public static String secondsToDDHHMMSS(long seconds) {
        return String.format("%02dh %02dm %02ds", (seconds / 3600 % 24), (seconds / 60) % 60, seconds % 60);
    }
}

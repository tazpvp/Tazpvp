package net.tazpvp.tazpvp.utils.objects.bosses.zorg;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ZorgManager {

    @Getter
    private static final List<Zorg> bosses = new ArrayList<>();
    private static final Location spawnLoc = new Location(Bukkit.getWorld("arena"), 0, 100, 0);

    public static void respawnZorg() {
        if (bosses.size() < 1) {
            bosses.add(new Zorg(spawnLoc));
        }
    }

    public static void despawnZorg() {
        for (Zorg boss : bosses) {
            boss.despawn();
            bosses.remove(boss);
        }
    }
}

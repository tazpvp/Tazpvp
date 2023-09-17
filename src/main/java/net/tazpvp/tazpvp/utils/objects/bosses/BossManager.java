package net.tazpvp.tazpvp.utils.objects.bosses;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.objects.bosses.zorg.Zorg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BossManager {

    @Getter
    private final static List<CustomBoss> bosses = new ArrayList<>();
    @Getter
    private static CustomBoss spawnedBoss;
    @Getter
    private final static Random random = new SecureRandom();

    public static void spawnBoss() {
        if (spawnedBoss == null) {
            spawnedBoss = bosses.get(random.nextInt(bosses.size()));
            spawnedBoss.spawn();
        }
    }

    public static void setupRunnable(final JavaPlugin p_javaPlugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (spawnedBoss != null) {
                    if (spawnedBoss.getBoss().getNearbyEntities(10, 10, 10).size() > 0) {
                        spawnedBoss.getRandomAttack().attack(spawnedBoss);
                    }
                }
            }
        }.runTaskTimer(p_javaPlugin, 3 * 20, 3 * 20);
    }

    public static void despawnBoss() {
        if (spawnedBoss != null) {
            spawnedBoss.despawn();
            spawnedBoss = null;
        }
    }

    public static void bossDied() {
        spawnedBoss = null;
        new BukkitRunnable() {
            @Override
            public void run() {
                spawnBoss();
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*20);
    }

    public static void addBoss(final CustomBoss p_customBoss) {
        bosses.add(p_customBoss);
    }
}

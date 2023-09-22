package net.tazpvp.tazpvp.bosses;

import lombok.Getter;
import lombok.Setter;
import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BossManager {

    @Getter
    private final static List<CustomBoss> bosses = new ArrayList<>();
    @Getter @Setter
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
                    if (!spawnedBoss.getBoss().getNearbyEntities(10, 10, 10).isEmpty()) {
                        spawnedBoss.getRandomAttack().attack(spawnedBoss);
                    }
                }
            }
        }.runTaskTimer(p_javaPlugin, 5 * 20, 5 * 20);
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

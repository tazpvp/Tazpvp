package net.tazpvp.tazpvp.utils.passive;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Afk {

    public final static HashMap<Player, Location> playerLocations = new HashMap<>();
    public final static List<Player> afkPlayers = new ArrayList<>();

    public static void initialize() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!playerLocations.containsKey(p)) {
                        playerLocations.put(p, p.getLocation());
                    } else {
                        if (p.getLocation().equals(playerLocations.get(p))) {
                            afkPlayers.add(p);
                        }
                    }
                }
            }
        }.runTaskTimer(Tazpvp.getInstance(),  20 * 60,20 * 60);
    }
}
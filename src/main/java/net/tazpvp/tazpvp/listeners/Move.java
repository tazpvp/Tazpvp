package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.events.Event;
import net.tazpvp.tazpvp.events.EventUtils;
import net.tazpvp.tazpvp.utils.ParkourUtil;
import net.tazpvp.tazpvp.utils.functions.DeathFunctions;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Move implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        Location raidus = new Location(Bukkit.getWorld("arena"), 0, 100, 24);
        Block b = new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY() - 1, e.getPlayer().getLocation().getZ()).getBlock();

        if (p.getWorld().equals(Bukkit.getWorld("parkour"))) {
            if (b.getType() == Material.WATER && p.getGameMode() != GameMode.SURVIVAL) {
                ParkourUtil.getCheckpoint(p);
            }
        }

        if (p.getWorld().getName().equals("arena")) {
            if (p.getLocation().distance(raidus) < 5) {
                Launchpad(p);
                return;
            }
            if (p.getLocation().getY() < 80) {
                if (p.getGameMode() == GameMode.SURVIVAL) {
                    DeathFunctions.death(p, p);
                }
            }
        }
    }

    private void Launchpad(Player p) {
        if (p.getGameMode().equals(GameMode.SURVIVAL)){
            p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
            p.setVelocity(new Vector(0, 1, 1.5));
            Tazpvp.getObservers().forEach(o -> o.launch(p));
        }
    }
}

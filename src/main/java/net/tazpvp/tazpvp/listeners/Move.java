package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.ParkourUtil;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.DeathFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import world.ntdi.nrcore.NRCore;

public class Move implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerWrapper pw = PlayerWrapper.getPlayer(p);

        Location raidus = new Location(Bukkit.getWorld("arena"), 0, 100, NRCore.config.spawn.getZ() + 29);
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
            if (pw.isRespawning()) {
                e.setCancelled(true);
                return;
            }
            if (p.getLocation().getY() < NRCore.config.spawn.getY() - 22) {
                if (p.getGameMode() == GameMode.SURVIVAL) {
                    DeathFunctions.death(p.getUniqueId());
                    return;
                }
            }
            if (p.hasMetadata("spawnTeleport")) {
                p.sendMessage(CC.RED + "Teleportation cancelled. You moved.");
                p.removeMetadata("spawnTeleport", Tazpvp.getInstance());
            }
        }
    }

    private void Launchpad(Player p) {
        PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        if (!pw.isLaunching()) {
            pw.setLaunching(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    pw.setLaunching(false);
                }
            }.runTaskLater(Tazpvp.getInstance(), 20);
            if (p.getGameMode().equals(GameMode.SURVIVAL)){
                p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
                if (PersistentData.getTalents(p.getUniqueId()).is("Glide")) {
                    p.setVelocity(new Vector(0, 1.7, 8));
                } else {
                    p.setVelocity(new Vector(0, 1.2, 3));
                }
                Tazpvp.getObservers().forEach(o -> o.launch(p));
            }
        }
    }
}

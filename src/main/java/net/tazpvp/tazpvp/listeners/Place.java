package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.config.ConfigUtils;

public class Place implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE) {

            Block b = e.getBlockPlaced();
            BlockState previousBlock = e.getBlockReplacedState();
            b.setMetadata("PlayerPlaced", new FixedMetadataValue(Tazpvp.getInstance(), true));

            if (Tazpvp.spawnRegion.contains(p.getLocation())) {
                e.setCancelled(true);
                return;
            }

            if (b.getLocation().distance(new Location(Bukkit.getWorld("arena"), 0, ConfigUtils.spawn.getY() - 10, ConfigUtils.spawn.getZ() + 76)) > 35 || b.getLocation().getY() < ConfigUtils.spawn.getY() - 10) {
                e.setCancelled(true);
                return;
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getBlock().setType(previousBlock.getType());
                }
            }.runTaskLater(Tazpvp.getInstance(), 20 * 8);

            Tazpvp.getObservers().forEach(observer -> observer.place(p, b));
        }
    }
}

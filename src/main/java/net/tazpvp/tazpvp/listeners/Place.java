package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;

public class Place implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        int delay;

        if (p.getGameMode() != GameMode.CREATIVE) {

            Block b = e.getBlockPlaced();
            Material mat = b.getType();
            BlockState previousBlock = e.getBlockReplacedState();
            BlockData previousBlockBlockData = previousBlock.getBlockData();
            b.setMetadata("PlayerPlaced", new FixedMetadataValue(Tazpvp.getInstance(), true));

            if (Tazpvp.spawnRegion.contains(p.getLocation())) {
                e.setCancelled(true);
                return;
            }

            if (b.getLocation().distance(new Location(Bukkit.getWorld("arena"), 0, NRCore.config.spawn.getY() - 10, NRCore.config.spawn.getZ() + 76)) > 35 || b.getLocation().getY() < NRCore.config.spawn.getY() - 10) {
                e.setCancelled(true);
                return;
            }

            if (mat == Material.PLAYER_HEAD) {
                e.setCancelled(true);
                p.sendMessage("Trade player heads with bub at the tree.");
            }

            // Delay in seconds.
            if (mat == Material.COBWEB) {
                delay = 2;
            } else if (PersistentData.isPremium(p.getUniqueId())) {
                delay = 20;
            } else {
                delay = 8;
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getBlock().setType(previousBlock.getType());
                    e.getBlock().setBlockData(previousBlockBlockData);
                }
            }.runTaskLater(Tazpvp.getInstance(), 20 * delay);

            Tazpvp.getObservers().forEach(observer -> observer.place(p, b));
        }
    }
}

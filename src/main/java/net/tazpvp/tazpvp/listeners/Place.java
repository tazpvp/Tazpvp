package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class Place implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE) {

            Block b = e.getBlockPlaced();
            b.setMetadata("PlayerPlaced", new FixedMetadataValue(Tazpvp.getInstance(), true));

            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getBlock().setType(Material.AIR);
                }
            }.runTaskLater(Tazpvp.getInstance(), 20 * 8);

            Tazpvp.getObservers().forEach(observer -> observer.place(p, b));
        }
    }
}

package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.implementations.UserRankServiceImpl;
import net.tazpvp.tazpvp.data.services.UserRankService;
import net.tazpvp.tazpvp.enums.RegionEnum;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;

public class PlaceListener implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        int delay;

        if (!p.getWorld().equals(Bukkit.getWorld("arena"))) {
            return;
        }
        if (p.getGameMode() != GameMode.CREATIVE) {

            Block b = e.getBlockPlaced();
            Material mat = b.getType();
            BlockState previousBlock = e.getBlockReplacedState();
            BlockData previousBlockBlockData = previousBlock.getBlockData();
            b.setMetadata("PlayerPlaced", new FixedMetadataValue(Tazpvp.getInstance(), true));

            if (RegionEnum.spawnRegion.contains(p.getLocation())) {
                e.setCancelled(true);
                return;
            }

            if (b.getLocation().distance(new Location(Bukkit.getWorld("arena"), 0, NRCore.config.spawn.getY() - 10, NRCore.config.spawn.getZ() + 78)) > 36 || b.getLocation().getY() < NRCore.config.spawn.getY() - 10) {
                e.setCancelled(true);
                return;
            }

            if (mat == Material.PLAYER_HEAD) {
                e.setCancelled(true);
                p.sendMessage("Trade player heads with bub at the tree.");
                return;
            }

            if (mat.name().endsWith("ORE")) {
                e.setCancelled(true);
                return;
            }

            if (mat == Material.TNT) {
                b.setType(Material.AIR);
                b.getWorld().spawn(b.getLocation().add(0.5, 0, 0.5), TNTPrimed.class);
                return;
            }

            final UserRankService userRankService = new UserRankServiceImpl();
            boolean hasPremium = userRankService.hasRank(pw.getUserRankEntity(), "premium");

            // Delay in seconds.
            if (mat == Material.COBWEB) {
                delay = 2;
            } else if (hasPremium) {
                delay = 20;
            } else {
                delay = 8;
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getBlock().removeMetadata("PlayerPlaced", Tazpvp.getInstance());
                    e.getBlock().setType(previousBlock.getType());
                    e.getBlock().setBlockData(previousBlockBlockData);
                }
            }.runTaskLater(Tazpvp.getInstance(), 20 * delay);

            pw.getBlocksPlaced().add(b.getType());
            Tazpvp.getObservers().forEach(observer -> observer.place(p, b));
        }
    }
}

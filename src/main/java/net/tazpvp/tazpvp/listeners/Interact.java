package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.game.crates.Crate;
import net.tazpvp.tazpvp.game.items.UsableItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Interact implements Listener {

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.BEACON) {
                e.setCancelled(true);
                for (Crate crate : Tazpvp.getCrateManager().getCrates()) {
                    crate.acceptClick(e);
                }
                return;
            } else if (e.getClickedBlock().getType() == Material.CHEST) {
                e.setCancelled(true);
            } else if (e.getClickedBlock().getType() == Material.ANVIL) {
                e.setCancelled(true);
            }
        }
        if (item == null) return;
        if (UsableItem.getCustomItem(e.getItem()) != null) {
            UsableItem usableItem = UsableItem.getCustomItem(e.getItem());
            if (usableItem == null) return;
            if (e.getAction() == Action.LEFT_CLICK_AIR) {
                usableItem.onLeftClick(p, e.getItem());
            } else if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                usableItem.onRightClick(p, e.getItem());
            }
        }

        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.BEACON) {
                e.setCancelled(true);
                for (Crate crate : Tazpvp.getCrateManager().getCrates()) {
                    crate.openPreview(e);
                }
            }
        }
    }
}

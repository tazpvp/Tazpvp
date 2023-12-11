package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.items.UsableItem;
import net.tazpvp.tazpvp.utils.crate.Crate;
import net.tazpvp.tazpvp.utils.functions.DeathFunctions;
import org.bukkit.Bukkit;
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

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.BEACON) {
                e.setCancelled(true);
                for (Crate crate : Tazpvp.getCrateManager().getCrates()) {
                    crate.acceptClick(e);
                }
                return;
            }
            if (e.getClickedBlock().getType() == Material.CHEST) {
                e.setCancelled(true);
            }
        }
        if (e.getItem() == null) return;
        if (getCustomItem(e.getItem()) == null) return;
        UsableItem item = getCustomItem(e.getItem());
        if (item == null) return;
        if (e.getAction() == Action.LEFT_CLICK_AIR) {
            item.onLeftClick(p, e.getItem());
        } else if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            item.onRightClick(p, e.getItem());
        }
    }

    private UsableItem getCustomItem(ItemStack item) {
        for (UsableItem customItem : UsableItem.customItemList) {
            if (item.getItemMeta() == null) return null;
            if (customItem.getMaterial() == item.getType() && customItem.getName().equalsIgnoreCase(item.getItemMeta().getDisplayName())) {
                return customItem;
            }
        }
        return null;
    }
}

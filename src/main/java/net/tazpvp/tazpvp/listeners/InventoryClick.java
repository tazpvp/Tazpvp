package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.functions.ItemFunctions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        ItemStack equipment = e.getCurrentItem();
        ItemStack enchantBook = e.getCursor();
        Inventory inv = e.getInventory();
        Player p = (Player) e.getWhoClicked();

        if (equipment != null && enchantBook != null) {
            if (inv.getType() == InventoryType.CRAFTING) {
                if (equipment.getType() != Material.AIR && enchantBook.getType() == Material.ENCHANTED_BOOK) {
                    e.setCancelled(true);

                    ItemFunctions.customEnchant(equipment, enchantBook, p);

                    Tazpvp.getObservers().forEach(observer -> observer.enchant(p));
                }
            }
        }
    }
}

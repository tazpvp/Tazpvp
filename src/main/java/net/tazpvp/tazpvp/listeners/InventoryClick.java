package net.tazpvp.tazpvp.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class InventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack applyTo = e.getCurrentItem();
        ItemStack enchant = e.getCursor();

        if (e.getInventory().getType() == InventoryType.CRAFTING) {
            if (!applyTo.getType().equals(Material.AIR) && !enchant.getType().equals(Material.AIR)) {

                if (enchant.getType().equals(Material.ENCHANTED_BOOK)) {

                    if (ableToApplyEnchantTo(applyTo)) {
                        e.setCancelled(true);
                        for (Enchantment enchantment : applyTo.getEnchantments().keySet()) {
                            applyTo.removeEnchantment(enchantment);
                        }

                        if(enchant.getItemMeta() instanceof EnchantmentStorageMeta) {
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchant.getItemMeta();
                            meta.getStoredEnchants().forEach(applyTo::addUnsafeEnchantment);
                        }
                        enchant.setType(Material.AIR);

                    }
                }
            }
        }

    }

    private boolean ableToApplyEnchantTo(ItemStack i) {
        Material mat = i.getType();
        String name = mat.name();

        if (name.endsWith("BOW")) return true;

        if (name.endsWith("SWORD")) return true;

        if (name.endsWith("HELMET") || name.endsWith("CHESTPLATE") || name.endsWith("LEGGINGS") || name.endsWith("BOOTS"))  return true;

        if (name.endsWith("PICKAXE")) return true;

         return false;
    }
}

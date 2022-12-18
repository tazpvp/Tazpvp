package net.tazpvp.tazpvp.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class InventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack applyTo = e.getCurrentItem();
        ItemStack enchant = e.getCursor();
        if (applyTo == null || enchant == null) return;

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
                        } else {
                            enchant.getEnchantments().forEach(applyTo::addUnsafeEnchantment);
                        }


                        HumanEntity p = e.getWhoClicked();
                        p.setItemOnCursor(new ItemStack(Material.AIR));

                    } else if (applyTo.getType() == Material.ENCHANTED_BOOK) {
                        if (enchant.getEnchantments().equals(applyTo.getEnchantments())) {
                            enchant.getEnchantments().values().forEach(val -> {
                                if (val > 3) return;
                            });

                            Map<Enchantment, Integer> enchantMap = applyTo.getEnchantments();

                            enchantMap.forEach((ech, i) -> {
                                enchantMap.put(ech, i++);
                            });

                            for (Enchantment enchantment : applyTo.getEnchantments().keySet()) {
                                applyTo.removeEnchantment(enchantment);
                            }

                            applyTo.addUnsafeEnchantments(enchantMap);

                            HumanEntity p = e.getWhoClicked();
                            p.setItemOnCursor(new ItemStack(Material.AIR));
                        }
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

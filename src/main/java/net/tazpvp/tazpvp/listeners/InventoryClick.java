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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryClick implements Listener {
    private final Map<String, List<String>> acceptable = new HashMap<>(){{
        put("BOW", List.of("punch", "knockback", "flame", "unbreaking"));
        put("SWORD", List.of("sharpness", "fire_aspect", "sweeping", "unbreaking"));
    }};

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack applyTo = e.getCurrentItem();
        ItemStack enchant = e.getCursor();
        if (applyTo == null || enchant == null) return;

        if (e.getInventory().getType() == InventoryType.CRAFTING) {
            if (!applyTo.getType().equals(Material.AIR) && !enchant.getType().equals(Material.AIR)) {

                if (enchant.getType().equals(Material.ENCHANTED_BOOK)) {

                    if (ableToApplyEnchantTo(applyTo)) {
                        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchant.getItemMeta();
                        Enchantment enchantment = (Enchantment) meta.getStoredEnchants().keySet().toArray()[0];

                        if (acceptableEnchant(applyTo, enchantment)) {

                            e.setCancelled(true);
                            for (Enchantment enchantRemoved : applyTo.getEnchantments().keySet()) {
                                applyTo.removeEnchantment(enchantRemoved);
                            }

                            if (enchant.getItemMeta() instanceof EnchantmentStorageMeta) {
                                meta.getStoredEnchants().forEach(applyTo::addUnsafeEnchantment);
                            } else {
                                enchant.getEnchantments().forEach(applyTo::addUnsafeEnchantment);
                            }


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

    private boolean acceptableEnchant(ItemStack i, Enchantment e) {
        String cutName = i.getType().name().split("_")[1];

        acceptable.get(cutName).forEach(name -> {
            System.out.println(e.getKey().toString().equals("minecraft:" + name));
        });
        return true;
    }
}

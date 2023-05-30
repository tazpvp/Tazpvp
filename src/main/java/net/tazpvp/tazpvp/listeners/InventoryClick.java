package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

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
        ItemStack equipment = e.getCurrentItem();
        ItemStack enchant = e.getCursor();
        Player p = (Player) e.getWhoClicked();

        if (equipment == null || enchant == null) return;

        if (e.getInventory().getType() == InventoryType.CRAFTING) {
            if (!equipment.getType().equals(Material.AIR) && !enchant.getType().equals(Material.AIR)) {

                if (enchant.getType().equals(Material.ENCHANTED_BOOK)) {

                    if (ableToApplyEnchantTo(equipment)) {
                        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchant.getItemMeta();
                        Enchantment enchantment = (Enchantment) meta.getStoredEnchants().keySet().toArray()[0];

                        if (acceptableEnchant(equipment, enchantment)) {

                            e.setCancelled(true);

                            if (equipment.getEnchantments().containsKey(enchantment)) {
                                if (equipment.hasItemMeta() && equipment.getItemMeta().hasEnchants()) {

                                    Map<Enchantment, Integer> enchantments = equipment.getEnchantments();
                                    Enchantment firstEquipmentEnchant = enchantments.keySet().iterator().next();
                                    final int equipmentEnchantLevel = enchantments.get(firstEquipmentEnchant);
                                    ItemMeta equipmentMeta = equipment.getItemMeta();

                                    if (!(equipmentMeta instanceof EnchantmentStorageMeta equipmentEnchantMeta)) {
                                        return;
                                    }

                                    if (equipmentEnchantLevel >= 3) {
                                        p.sendMessage("This enchantment is already at it's maximum level.");
                                        equipmentEnchantMeta.addEnchant(enchantment, equipmentEnchantLevel, true);
                                        return;
                                    }
                                    equipmentEnchantMeta.addEnchant(enchantment, (equipmentEnchantLevel + 1), true);
                                }
                                return;
                            }

                            for (Enchantment enchantRemoved : equipment.getEnchantments().keySet()) {
                                equipment.removeEnchantment(enchantRemoved);
                            }

                            meta.getStoredEnchants().forEach(equipment::addUnsafeEnchantment);

                            p.setItemOnCursor(new ItemStack(Material.AIR));
                            Tazpvp.getObservers().forEach(observer -> observer.enchant(p));
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

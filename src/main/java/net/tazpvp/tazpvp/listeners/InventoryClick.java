package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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
        ItemStack enchantBook = e.getCursor();
        Player player = (Player) e.getWhoClicked();

        if (equipment == null || enchantBook == null) return;
        if (e.getInventory().getType() != InventoryType.CRAFTING) return;
        if (equipment.getType() == Material.AIR || enchantBook.getType() != Material.ENCHANTED_BOOK) return;
        if (!ableToApplyEnchantTo(equipment)) return;

        EnchantmentStorageMeta enchantMeta = (EnchantmentStorageMeta) enchantBook.getItemMeta();
        if (enchantMeta == null || enchantMeta.getStoredEnchants().isEmpty()) return;

        Enchantment enchantment = enchantMeta.getStoredEnchants().keySet().iterator().next();
        if (!acceptableEnchant(equipment, enchantment)) return;

        e.setCancelled(true);

        ItemMeta equipmentMeta = equipment.getItemMeta();
        if (equipmentMeta == null) return;

        int newLevel;
        if (equipmentMeta.hasEnchant(enchantment)) {
            int currentLevel = equipmentMeta.getEnchantLevel(enchantment);
            if (currentLevel >= 3) {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                player.sendMessage(CC.RED + "This enchantment is already at its maximum level.");
                return;
            }
            newLevel = currentLevel + 1;
        } else {
            newLevel = 1;
        }

        equipmentMeta.addEnchant(enchantment, newLevel, true);
        equipment.setItemMeta(equipmentMeta);

        enchantBook.setAmount(enchantBook.getAmount() - 1);
        if (enchantBook.getAmount() <= 0) {
            e.setCursor(new ItemStack(Material.AIR));
        } else {
            e.setCursor(enchantBook);
        }

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);

        player.sendMessage(CC.RED + "Enchantment " + enchantment.getKey().getKey() + " applied to the equipment (Level " + newLevel + ").");

        Tazpvp.getObservers().forEach(observer -> observer.enchant(player));
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
        if (acceptable.containsKey(cutName)) {
            for (String name : acceptable.get(cutName)) {
                if (e.getKey().toString().equals("minecraft:" + name)) {
                    return true;
                }
            }
        }
        return false;
    }
}

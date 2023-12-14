package net.tazpvp.tazpvp.utils.functions;

import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemFunctions {

    private static final Map<String, List<String>> acceptable = new HashMap<>(){{
        put("BOW", List.of("power", "punch", "flame", "infinity", "unbreaking", "mending"));
        put("CROSSBOW", List.of("piercing", "quick_charge", "multishot", "unbreaking", "mending"));
        put("SWORD", List.of("sharpness", "smite", "bane_of_arthropods", "knockback", "fire_aspect", "looting", "sweeping", "unbreaking", "mending"));
        put("HELMET", List.of("protection", "fire_protection", "feather_falling", "blast_protection", "projectile_protection", "respiration", "aqua_affinity", "thorns", "unbreaking", "mending", "vanishing_curse"));
        put("CHESTPLATE", List.of("protection", "fire_protection", "blast_protection", "projectile_protection", "thorns", "unbreaking", "mending", "vanishing_curse"));
        put("LEGGINGS", List.of("protection", "fire_protection", "blast_protection", "projectile_protection", "thorns", "unbreaking", "mending", "vanishing_curse"));
        put("BOOTS", List.of("protection", "fire_protection", "feather_falling", "blast_protection", "projectile_protection", "frost_walker", "depth_strider", "soul_speed", "thorns", "unbreaking", "mending", "vanishing_curse"));
        put("SHIELD", List.of("unbreaking", "mending"));
    }};
    public static void customEnchant(ItemStack equipment, ItemStack enchantBook, Player p) {

        if (!ableToApplyEnchantTo(equipment)) return;

        EnchantmentStorageMeta enchantMeta = (EnchantmentStorageMeta) enchantBook.getItemMeta();
        if (enchantMeta == null || enchantMeta.getStoredEnchants().isEmpty()) return;

        Enchantment enchantment = enchantMeta.getStoredEnchants().keySet().iterator().next();
        if (!acceptableEnchant(equipment, enchantment)) return;

        ItemMeta equipmentMeta = equipment.getItemMeta();
        if (equipmentMeta == null) return;

        int newLevel;
        if (equipmentMeta.hasEnchant(enchantment)) {
            int currentLevel = equipmentMeta.getEnchantLevel(enchantment);
            if (currentLevel >= 2) {
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                p.sendMessage(CC.RED + "This enchantment is already at its maximum level.");
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
            enchantBook.setType(Material.AIR);
        } else {
            enchantBook.setType(enchantBook.getType());
        }

        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);

        p.sendMessage(CC.GREEN + "You applied the " + CC.WHITE + enchantment.getKey().getKey() + CC.GREEN + " enchantment. [" + newLevel + "]");
    }

    private static boolean ableToApplyEnchantTo(ItemStack i) {
        Material mat = i.getType();
        String name = mat.name();

        if (name.endsWith("BOW")) return true;
        if (name.endsWith("SWORD")) return true;
        if (name.endsWith("HELMET") || name.endsWith("CHESTPLATE") || name.endsWith("LEGGINGS") || name.endsWith("BOOTS"))  return true;
        if (name.endsWith("PICKAXE")) return true;

        return false;
    }

    private static boolean acceptableEnchant(ItemStack i, Enchantment e) {
        String[] splitName = i.getType().name().split("_");
        if (splitName.length > 1) {
            String cutName = splitName[1];
            if (acceptable.containsKey(cutName)) {
                for (String name : acceptable.get(cutName)) {
                    if (e.getKey().toString().equals("minecraft:" + name)) {
                        return true;
                    }
                }
            }
        } else {
            String itemName = i.getType().name();
            if (acceptable.containsKey(itemName)) {
                for (String name : acceptable.get(itemName)) {
                    if (e.getKey().toString().equals("minecraft:" + name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

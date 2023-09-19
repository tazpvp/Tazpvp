package net.tazpvp.tazpvp.guis.Mine;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;

public class CustomEnchantments {
    public static Enchantment DOUBLE_ORES;
    public static Enchantment AUTO_SMELT;

    public static void registerCustomEnchantments() {
        DOUBLE_ORES = new EnchantmentWrapper("double_ores");
        Enchantment.registerEnchantment(DOUBLE_ORES);

        AUTO_SMELT = new EnchantmentWrapper("auto_smelt");
        Enchantment.registerEnchantment(AUTO_SMELT);
    }
}

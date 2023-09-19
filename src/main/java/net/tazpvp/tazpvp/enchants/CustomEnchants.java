package net.tazpvp.tazpvp.enchants;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomEnchants {

    public static Enchantment DOUBLE_ORES = new EnchantWrapper(new NamespacedKey(Tazpvp.getInstance(), "double_ores"), "Double Ores", 1);
    public static Enchantment AUTO_SMELT = new EnchantWrapper(new NamespacedKey(Tazpvp.getInstance(), "auto_smelt"), "Auto Smelt", 1);

    public static void register() {
        boolean registeredDoubleOres = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(DOUBLE_ORES);
        boolean registeredAutoSmelt = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(AUTO_SMELT);

        if (!registeredDoubleOres) registerEnchant(DOUBLE_ORES);
        if (!registeredAutoSmelt) registerEnchant(AUTO_SMELT);
    }

    public static void registerEnchant(Enchantment enchant) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchant);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }
        if (registered) {

        }
    }
}

package net.tazpvp.tazpvp.enchants;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomEnchants {

    public static List<Enchantment> enchantList = List.of(
            new EnchantWrapper("double_ores", "Double Ores", 1),
            new EnchantWrapper("auto_smelt", "Auto Smelt", 1)
    );

    public static void register() {
        for (Enchantment ench : enchantList) {
            boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(ench);

            if (!registered) registerEnchant(ench);
        }
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

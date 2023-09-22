package net.tazpvp.tazpvp.enchants;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class EnchantUtil {

    private static final List<Enchantment> customEnchantments = new ArrayList<>();

    static {
        customEnchantments.add(new EnchantWrapper(new NamespacedKey(Tazpvp.getInstance(), "double_ores"), "Double Ores", 1));
        customEnchantments.add(new EnchantWrapper(new NamespacedKey(Tazpvp.getInstance(), "auto_smelt"), "Auto Smelt", 1));
        customEnchantments.add(new EnchantWrapper(new NamespacedKey(Tazpvp.getInstance(), "custom_efficiency"), "Efficiency", 5));
    }

    public static void register() {
        for (Enchantment enchantment : customEnchantments) {
            if (!isEnchantmentRegistered(enchantment)) {
                registerEnchant(enchantment);
            }
        }
    }

    public static void registerEnchant(Enchantment enchant) {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchant);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isEnchantmentRegistered(Enchantment enchant) {
        return Arrays.stream(Enchantment.values())
                .collect(toList())
                .contains(enchant);
    }

    public static Enchantment getEnchant(String type) {
        return customEnchantments.stream()
                .filter(enchantment -> enchantment.getKey().getKey().equals(type))
                .findFirst()
                .orElse(null);
    }
}

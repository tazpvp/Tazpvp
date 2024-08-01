package net.tazpvp.tazpvp.helpers;

import net.tazpvp.tazpvp.wrappers.EnchantWrapper;
import net.tazpvp.tazpvp.enums.EnchantEnum;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class EnchantHelper {

    private static final List<Enchantment> customEnchantments = new ArrayList<>();

    static {
        for (EnchantEnum enchant : EnchantEnum.values()) {
            customEnchantments.add(new EnchantWrapper(enchant.getName(), enchant.getMaxLevel()));
        }
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
//            Enchantment.registerEnchantment(enchant);
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

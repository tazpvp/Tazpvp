package net.tazpvp.tazpvp.utils.functions;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.EnchantmentBookBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DeathFunctions {
    public static ItemStack deathItem() {
        Random r = new Random();

        List<Enchantment> enchants = List.of(
                Enchantment.SHARPNESS,
                Enchantment.POWER,
                Enchantment.PROTECTION,
                Enchantment.MENDING,
                Enchantment.FLAME,
                Enchantment.FIRE_ASPECT,
                Enchantment.UNBREAKING
        );

        List<ItemStack> items = Arrays.asList(
                new ItemStack(Material.GOLDEN_APPLE, 2),
                new ItemStack(Material.COOKED_BEEF, 10)
        );

        int randomItemChance = r.nextInt(10);

        if (randomItemChance < 7) {
            Enchantment enchant = enchants.get(r.nextInt(enchants.size()));
            return new EnchantmentBookBuilder().enchantment(enchant, 1).build();
        } else {
            return items.get(r.nextInt(items.size()));
        }
    }
}

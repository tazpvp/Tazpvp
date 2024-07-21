package net.tazpvp.tazpvp.game.items.enchants;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnchantWrapper extends Enchantment {

    private final String name;
    private final int maxLevel;
    public EnchantWrapper(String name, int maxLevel) {
        super();
        this.name = name;
        this.maxLevel = maxLevel;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @NotNull
    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return item.getType() == Material.DIAMOND_PICKAXE || item.getType() == Material.IRON_PICKAXE || item.getType() == Material.STONE_PICKAXE || item.getType() == Material.WOODEN_PICKAXE;
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return null;
    }

    @NotNull
    @Override
    public String getTranslationKey() {
        return null;
    }
}

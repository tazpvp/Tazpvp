package net.tazpvp.tazpvp.enchants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;

@AllArgsConstructor
@Getter
public enum Enchants {
    CUSTOM_EFFICIENCY(5,15,"Efficiency", EnchantUtil.getEnchant("custom_efficiency")),
    DOUBLE_ORES(1,56,"Double Ores", EnchantUtil.getEnchant("double_ores")),
    AUTO_SMELT(1,35,"Auto Smelt", EnchantUtil.getEnchant("auto_smelt"));

    private final int maxLevel;
    private final int cost;
    private final String name;
    private final Enchantment enchant;
}

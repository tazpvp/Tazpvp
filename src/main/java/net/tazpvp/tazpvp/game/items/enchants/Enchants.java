package net.tazpvp.tazpvp.game.items.enchants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;

@AllArgsConstructor
@Getter
public enum Enchants {
    DOUBLE_ORES(1,300,"Double Ores", "double_ores"),
    AUTO_SMELT(1,200,"Auto Smelt", "auto_smelt");

    private final int maxLevel;
    private final int cost;
    private final String name;
    private final String key;

    public Enchantment getEnchant() {
        return EnchantUtil.getEnchant(this.key);
    }
}

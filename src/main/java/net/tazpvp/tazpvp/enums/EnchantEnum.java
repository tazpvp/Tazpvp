package net.tazpvp.tazpvp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.tazpvp.tazpvp.helpers.EnchantHelper;
import org.bukkit.enchantments.Enchantment;

@AllArgsConstructor
@Getter
public enum EnchantEnum {
    DOUBLE_ORES(1,300,"Double Ores", "double_ores"),
    AUTO_SMELT(1,200,"Auto Smelt", "auto_smelt");

    private final int maxLevel;
    private final int cost;
    private final String name;
    private final String key;

    public Enchantment getEnchant() {
        return EnchantHelper.getEnchant(this.key);
    }
}

package net.tazpvp.tazpvp.enums;

import lombok.Getter;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

@Getter
public enum StaticItems {

    MYTHIC_KEY(ChatHelper.gradient("#db3bff", "Mythic Key", true), Material.TRIPWIRE_HOOK),
    RARE_KEY(ChatHelper.gradient("#039dfc", "Rare Key", true), Material.TRIPWIRE_HOOK),
    COMMON_KEY(ChatHelper.gradient("#03fc39", "Common Key", true), Material.TRIPWIRE_HOOK);

    private final String name;
    private final Material type;

    StaticItems(String name, Material type) {
        this.name = name;
        this.type = type;
    }

    public ItemStack item(int num) {
        return ItemBuilder.of(type, num, name).build();
    }
}

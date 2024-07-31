package net.tazpvp.tazpvp.game.items;

import lombok.Getter;
import net.tazpvp.tazpvp.helpers.ChatFunctions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

@Getter
public enum StaticItems {

    SHARD(ChatFunctions.gradient("#db3bff", "Shard", true), Material.AMETHYST_SHARD),
    MYTHIC_KEY(ChatFunctions.gradient("#db3bff", "Mythic Key", true), Material.TRIPWIRE_HOOK),
    RARE_KEY(ChatFunctions.gradient("#039dfc", "Rare Key", true), Material.TRIPWIRE_HOOK),
    COMMON_KEY(ChatFunctions.gradient("#03fc39", "Common Key", true), Material.TRIPWIRE_HOOK);

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

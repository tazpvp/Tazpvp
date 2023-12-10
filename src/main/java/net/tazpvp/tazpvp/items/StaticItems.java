package net.tazpvp.tazpvp.items;

import lombok.Getter;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

@Getter
public enum StaticItems {

    SHARD(ChatFunctions.gradient("#db3bff", "Shard", true), Material.AMETHYST_SHARD);

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

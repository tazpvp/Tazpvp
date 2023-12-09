package net.tazpvp.tazpvp.utils.enums;

import lombok.Getter;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

@Getter
public enum GameItems {

    SHARD(ChatFunctions.gradient("#db3bff", "Shard", true), Material.AMETHYST_SHARD);

    private final String name;
    private final Material type;
    private final ItemStack item;

    GameItems(String name, Material type) {
        this.name = name;
        this.type = type;
        this.item = ItemBuilder.of(type, 1, name).build();
    }
}

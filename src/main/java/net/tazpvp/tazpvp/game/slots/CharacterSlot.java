package net.tazpvp.tazpvp.game.slots;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class CharacterSlot {
    private final int id;
    private final WeaponType type;
    @Getter
    private ItemStack[] items;
    @Getter
    private ItemStack[] armors;

    public CharacterSlot(int id, WeaponType type) {
        this.id = id;
        this.type = type;
    }
}

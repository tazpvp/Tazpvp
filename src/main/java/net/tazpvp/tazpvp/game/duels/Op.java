package net.tazpvp.tazpvp.game.duels;

import net.tazpvp.tazpvp.objects.DuelObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class Op extends DuelObject {
    public Op(final UUID P1, final UUID P2) {
        super(P1, P2, "op");
    }

    @Override
    public void addItems(PlayerInventory inv) {
        inv.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        inv.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        inv.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        inv.setBoots(new ItemStack(Material.DIAMOND_BOOTS));

        inv.addItem(new ItemStack(Material.DIAMOND_SWORD));
        inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 6));
    }
}

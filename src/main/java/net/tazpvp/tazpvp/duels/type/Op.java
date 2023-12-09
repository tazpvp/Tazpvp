package net.tazpvp.tazpvp.duels.type;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Op extends Classic {
    public Op(final UUID P1, final UUID P2) {
        super(P1, P2);
    }

    @Override
    public void addItems(final UUID duelerUUID) {
        Player p = Bukkit.getPlayer(duelerUUID);
        Inventory inv = p.getInventory();

        p.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        p.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        p.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        p.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

        inv.addItem(new ItemStack(Material.DIAMOND_SWORD));
        inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 6));
    }
}

package net.tazpvp.tazpvp.services;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface KitService {
    String serializeInventory(ItemStack[] inventory);
    ItemStack[] deserializeInventory(String data);
    List<ItemStack> getValidItems();
}

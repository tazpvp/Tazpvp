package net.tazpvp.tazpvp.items.usables;

import net.tazpvp.tazpvp.items.UsableItem;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class azureVapor extends UsableItem {
    public azureVapor() {
        super("Azure Vapor", null, Material.BLUE_ORCHID);
    }

    @Override
    public void onRightClick(Player p, ItemStack item) {
        if (p.getFireTicks() > 0) {
            p.setFireTicks(0);
            item.setAmount(item.getAmount() - 1);
        }
    }

    @Override
    public void onLeftClick(Player p, ItemStack item) {
        if (p.getFireTicks() > 0) {
            p.setFireTicks(0);
            item.setAmount(item.getAmount() - 1);
        }
    }

    @Override
    public void onLeftClick(Player p, ItemStack item, Player target) {
    }

    @Override
    public void onLeftClick(Player p, ItemStack item, Block b) {
    }
}

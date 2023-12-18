package net.tazpvp.tazpvp.game.items.usables;

import net.tazpvp.tazpvp.game.items.UsableItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AzureVapor extends UsableItem {
    public AzureVapor() {
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

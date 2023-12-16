package net.tazpvp.tazpvp.items.usables;

import net.tazpvp.tazpvp.items.UsableItem;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PremiumPass extends UsableItem {
    public PremiumPass() {
        super("Premium Token", new String[]{CC.RED + "Right click to activate", CC.RED + "1 month of premium"}, Material.NETHER_STAR);
    }

    @Override
    public void onRightClick(Player p, ItemStack item) {

    }

    @Override
    public void onLeftClick(Player p, ItemStack item) {

    }

    @Override
    public void onLeftClick(Player p, ItemStack item, Player target) {

    }

    @Override
    public void onLeftClick(Player p, ItemStack item, Block b) {

    }
}

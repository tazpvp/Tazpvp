package net.tazpvp.tazpvp.game.items.usables;

import net.tazpvp.tazpvp.game.items.UsableItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Inker extends UsableItem {
    public Inker() {
        super("Inker", null, Material.INK_SAC);
    }

    @Override
    public void onRightClick(Player p, ItemStack item) {

    }

    @Override
    public void onLeftClick(Player p, ItemStack item) {

    }

    @Override
    public void onLeftClick(Player p, ItemStack item, Player target) {
        if (target == null) return;
        for (PotionEffect effect : target.getActivePotionEffects()) {
            if (effect.getType().equals(PotionEffectType.BLINDNESS)) {
                return;
            }
        }
        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*2,0, false, false));
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount()- 1);
        } else {
            p.getInventory().remove(item);
        }
    }

    @Override
    public void onLeftClick(Player p, ItemStack item, Block b) {

    }
}

package net.tazpvp.tazpvp.guis.Mine;

import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import net.tazpvp.tazpvp.utils.objects.Pickaxe;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class Enchantments extends GUI {

    private final Pickaxe pickaxe;

    public Enchantments(Player p, Pickaxe pickaxe) {
        super("Pickaxe Enchantments", 3);
        this.pickaxe = pickaxe;
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        fill(0, 27, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        Button autoSmelt = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Auto Smelt").lore(CC.GRAY + "Automatically refine ores.").build(), (e) -> {
            p.closeInventory();
            applyEnchant(p, pickaxe, "Efficiency", 1, CustomEnchantments.AUTO_SMELT);
        });

        Button efficiency = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Efficiency").lore(CC.GRAY + "Increase the speed of mining.").build(), (e) -> {
            p.closeInventory();
            applyEnchant(p, pickaxe, "Efficiency", 1, Enchantment.DIG_SPEED);
        });

        Button doubleOres = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Double Ores").lore(CC.GRAY + "Duplicate the ores you mine.").build(), (e) -> {
            p.closeInventory();
            applyEnchant(p, pickaxe, "Efficiency", 1, CustomEnchantments.DOUBLE_ORES);
        });

        addButton(autoSmelt, 11);
        addButton(efficiency, 13);
        addButton(doubleOres, 15);

        update();
    }

    private void applyEnchant(Player p, Pickaxe pickaxe, String type, int cost, Enchantment enchant) {
        int shardCount = PlayerFunctions.countShards(p);
        if (shardCount >= cost) {
            ItemStack pickaxeItem = pickaxe.getItem();
            p.sendMessage("You enchanted your pickaxe with " + type);
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
            int enchantLevel = pickaxeItem.getEnchantmentLevel(enchant);
            if (enchantLevel < enchant.getMaxLevel()) {
                pickaxeItem.addEnchantment(enchant, enchantLevel + 1);
            } else {
                p.sendMessage("You've reached the maximum level for this enchantment");
            }
        } else {
            p.sendMessage("You do not have enough shards for this upgrade.");
        }
    }
}

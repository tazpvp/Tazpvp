package net.tazpvp.tazpvp.guis.Mine;

import net.tazpvp.tazpvp.enchants.CustomEnchants;
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

    private final ItemStack pickaxe;
    private final Player p;

    public Enchantments(Player p, ItemStack pickaxe) {
        super("Pickaxe Enchantments", 3);
        this.pickaxe = pickaxe;
        this.p = p;
        addItems();
        open(p);
    }

    private void addItems() {
        fill(0, 27, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        Button autoSmelt = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Auto Smelt").lore(CC.GRAY + "Automatically refine ores.").build(), (e) -> {
            p.closeInventory();
            applyEnchant(CustomEnchants.AUTO_SMELT, 1);
        });

        Button efficiency = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Efficiency").lore(CC.GRAY + "Increase the speed of mining.").build(), (e) -> {
            p.closeInventory();
            applyEnchant(Enchantment.DIG_SPEED, 1);
        });

        Button doubleOres = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Double Ores").lore(CC.GRAY + "Duplicate the ores you mine.").build(), (e) -> {
            p.closeInventory();
            applyEnchant(CustomEnchants.DOUBLE_ORES, 1);
        });

        addButton(autoSmelt, 11);
        addButton(efficiency, 13);
        addButton(doubleOres, 15);

        update();
    }

    private void applyEnchant(Enchantment enchant, int cost) {
        int shardCount = PlayerFunctions.countShards(p);
        if (shardCount >= cost) {
            if (pickaxe.getItemMeta().hasEnchant(enchant)) {
                int enchantLevel = pickaxe.getEnchantmentLevel(enchant);
                if (enchantLevel < enchant.getMaxLevel()) {
                    pickaxe.addEnchantment(enchant, enchantLevel + 1);
                } else {
                    p.sendMessage("You've reached the maximum level for this enchantment");
                    return;
                }
            } else {
                pickaxe.addEnchantment(enchant, 1);
            }
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
            p.sendMessage("You enchanted your pickaxe with " + enchant.getName());
        } else {
            p.sendMessage("You do not have enough shards for this upgrade.");
        }
    }
}

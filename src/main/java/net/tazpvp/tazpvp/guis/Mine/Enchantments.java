package net.tazpvp.tazpvp.guis.Mine;

import net.tazpvp.tazpvp.enchants.Enchants;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

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

        Button autoSmelt = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Auto Smelt").lore(CC.GRAY + "Automatically refine ores.").build(), (e) ->
            applyEnchant(Enchants.AUTO_SMELT));
        Button efficiency = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Efficiency").lore(CC.GRAY + "Increase the speed of mining.").build(), (e) ->
            applyEnchant(Enchants.CUSTOM_EFFICIENCY));
        Button doubleOres = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Double Ores").lore(CC.GRAY + "Duplicate the ores you mine.").build(), (e) ->
            applyEnchant(Enchants.DOUBLE_ORES));

        addButton(autoSmelt, 11);
        addButton(efficiency, 13);
        addButton(doubleOres, 15);

        update();
    }

    private void applyEnchant(Enchants enchantEnum) {
        int shardCount = PlayerFunctions.countShards(p);

        ItemMeta meta = pickaxe.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (meta.getLore() != null)
             lore = meta.getLore();

        int cost = enchantEnum.getCost();
        Enchantment enchant = enchantEnum.getEnchant();
        if (shardCount < cost) {
            p.sendMessage(CC.RED + "You do not have enough shards for this upgrade.");
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            return;
        }

        if (!pickaxe.getItemMeta().hasEnchant(enchant)) {
            pickaxe.addUnsafeEnchantment(enchant, 1);
            lore.add(ChatColor.GOLD + enchantEnum.getName() + " I");
        } else {
            int enchantLevel = pickaxe.getEnchantmentLevel(enchant);
            if (enchantLevel >= enchant.getMaxLevel()) {
                p.sendMessage(CC.RED + "You've reached the maximum level for this enchantment");
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                return;
            }

            pickaxe.addUnsafeEnchantment(enchant, enchantLevel + 1);
            lore.add(ChatColor.GOLD + enchantEnum.getName() + " " + ChatFunctions.intToRoman(pickaxe.getEnchantmentLevel(enchant)));

        }
        meta.setLore(lore);
        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
        p.sendMessage("You enchanted your pickaxe with " + enchantEnum.getName());
    }
}

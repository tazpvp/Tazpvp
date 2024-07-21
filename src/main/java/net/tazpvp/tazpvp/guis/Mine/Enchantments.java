package net.tazpvp.tazpvp.guis.Mine;

import net.tazpvp.tazpvp.data.DataTypes;
import net.tazpvp.tazpvp.data.PersistentData;
import net.tazpvp.tazpvp.game.items.enchants.Enchants;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
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
import java.util.Objects;

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

        Button autoSmelt = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Auto Smelt").lore(CC.GRAY + "Automatically refine ores.", CC.GRAY + "Cost: " + CC.GOLD + "$" + Enchants.AUTO_SMELT.getCost()).build(), (e) ->
            applyEnchant(Enchants.AUTO_SMELT));
        Button efficiency = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Efficiency").lore(CC.GRAY + "Increase the speed of mining.", CC.GRAY + "Cost: " + CC.GOLD + "$200").build(), (e) ->
            applyEnchant(Enchantment.EFFICIENCY, 200));
        Button doubleOres = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Double Ores").lore(CC.GRAY + "Duplicate the ores you mine.", CC.GRAY + "Cost: " + CC.GOLD + "$" + Enchants.DOUBLE_ORES.getCost()).build(), (e) ->
            applyEnchant(Enchants.DOUBLE_ORES));

        addButton(autoSmelt, 11);
        addButton(efficiency, 13);
        addButton(doubleOres, 15);

        update();
    }

    private void applyEnchant(Enchants enchantEnum) {
        int cost = enchantEnum.getCost();
        ItemMeta itemMeta = pickaxe.getItemMeta();

        if (PersistentData.getInt(p.getUniqueId(), DataTypes.COINS) < cost) {
            p.sendMessage(CC.RED + "You do not have enough money for this upgrade.");
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            return;
        }

        Enchantment enchant = enchantEnum.getEnchant();
        int levelToAdd = 1;

        if (itemMeta.hasEnchant(enchant)) {
            int currentEnchantLevel = pickaxe.getEnchantmentLevel(enchant);

            if (currentEnchantLevel >= enchant.getMaxLevel()) {
                p.sendMessage(CC.RED + "You've reached the maximum level for this enchantment");
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                return;
            }

            levelToAdd = currentEnchantLevel + 1;
        }

        pickaxe.addUnsafeEnchantment(enchant, levelToAdd);
        PersistentData.remove(p.getUniqueId(), DataTypes.COINS, cost);
        updateLore(pickaxe, enchantEnum.getName(), levelToAdd);
        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
        p.sendMessage("You enchanted your pickaxe with " + enchantEnum.getName());
    }

    private void applyEnchant(Enchantment enchant, int cost) {

        if (PersistentData.getInt(p.getUniqueId(), DataTypes.COINS) < cost) {
            p.sendMessage(CC.RED + "You do not have enough money for this upgrade.");
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            return;
        }

        int levelToAdd = 1;
        if (pickaxe.getItemMeta().hasEnchant(enchant)) {
            int currentEnchantLevel = pickaxe.getEnchantmentLevel(enchant);
            if (currentEnchantLevel >= enchant.getMaxLevel()) {
                p.sendMessage(CC.RED + "You've reached the maximum level for this enchantment");
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                return;
            }
            levelToAdd = currentEnchantLevel + 1;
        }
        pickaxe.addEnchantment(enchant, levelToAdd);
        PersistentData.remove(p.getUniqueId(), DataTypes.COINS, cost);
        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
        String keyName = enchant.getKey().getKey();
        p.sendMessage("You enchanted your pickaxe with " + keyName.substring(0, 1).toUpperCase() + keyName.substring(1));
    }

    private void updateLore(ItemStack itemStack, String enchantPrefix, int level) {
        final ItemMeta meta = itemStack.getItemMeta();

        final List<String> lore = Objects.requireNonNullElse(meta.getLore(), new ArrayList<>());

        boolean loreExists = false;
        final String enchantLine = CC.GRAY + enchantPrefix + " " + ChatFunctions.intToRoman(level);

        if (!lore.isEmpty()) {
            for (int i = 0; i < lore.size(); i++) {
                final String loreLine = lore.get(i);

                if (loreLine.toLowerCase().startsWith(enchantPrefix.toLowerCase())) {
                    loreExists = true;
                    lore.set(i, enchantLine);
                }
            }
        }

        if (!loreExists) {
            lore.add(enchantLine);
        }

        meta.setLore(lore);
        pickaxe.setItemMeta(meta);
    }
}

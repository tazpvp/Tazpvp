package net.tazpvp.tazpvp.game.npcs.enchant.gui;

import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.EnchantEnum;
import net.tazpvp.tazpvp.enums.StatEnum;
import net.tazpvp.tazpvp.helpers.ChatHelper;
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
import java.util.UUID;

public class Enchantments extends GUI {

    private final ItemStack pickaxe;
    private final Player p;

    private final UUID id;

    public Enchantments(Player p, ItemStack pickaxe) {
        super("Pickaxe Enchantments", 3);
        this.pickaxe = pickaxe;
        this.p = p;
        this.id = p.getUniqueId();
        addItems();
        open(p);
    }

    private void addItems() {
        fill(0, 27, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        Button autoSmelt = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1)
                .name(CC.GREEN + "" + CC.BOLD + "Auto Smelt")
                .lore(CC.GRAY + "Automatically refine ores.", CC.GRAY + "Cost: " + CC.GOLD + "$" + EnchantEnum.AUTO_SMELT.getCost())
                .build(), (e) ->
            applyEfficiency(EnchantEnum.AUTO_SMELT));
        Button efficiency = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1)
                        .name(CC.GREEN + "" + CC.BOLD + "Efficiency")
                        .lore(CC.GRAY + "Increase the speed of mining.", CC.GRAY + "Cost: " + CC.GOLD + "$200")
                        .build(), (e) ->
            applyEfficiency());
        Button doubleOres = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1)
                .name(CC.GREEN + "" + CC.BOLD + "Double Ores")
                .lore(CC.GRAY + "Duplicate the ores you mine.", CC.GRAY + "Cost: " + CC.GOLD + "$" + EnchantEnum.DOUBLE_ORES.getCost())
                .build(), (e) ->
            applyEfficiency(EnchantEnum.DOUBLE_ORES));

        addButton(autoSmelt, 11);
        addButton(efficiency, 13);
        addButton(doubleOres, 15);

        update();
    }

    private void applyEfficiency(EnchantEnum enchantEnum) {
        int cost = enchantEnum.getCost();
        ItemMeta itemMeta = pickaxe.getItemMeta();

        if (StatEnum.COINS.getInt(id) < cost) {
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
        StatEnum.COINS.remove(id, cost);
        updateLore(pickaxe, enchantEnum.getName(), levelToAdd);
        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
        p.sendMessage("You enchanted your pickaxe with " + enchantEnum.getName());
    }

    private void applyEfficiency() {

        if (StatEnum.COINS.getInt(id) < 200) {
            p.sendMessage(CC.RED + "You do not have enough money for this upgrade.");
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            return;
        }

        int levelToAdd = 1;
        if (pickaxe.getItemMeta().hasEnchant(Enchantment.EFFICIENCY)) {
            int currentEnchantLevel = pickaxe.getEnchantmentLevel(Enchantment.EFFICIENCY);
            if (currentEnchantLevel >= Enchantment.EFFICIENCY.getMaxLevel()) {
                p.sendMessage(CC.RED + "You've reached the maximum level for this enchantment");
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                return;
            }
            levelToAdd = currentEnchantLevel + 1;
        }
        pickaxe.addEnchantment(Enchantment.EFFICIENCY, levelToAdd);
        StatEnum.COINS.remove(id, 200);
        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
        String keyName = Enchantment.EFFICIENCY.getKey().getKey();
        p.sendMessage("You enchanted your pickaxe with " + keyName.substring(0, 1).toUpperCase() + keyName.substring(1));
    }

    private void updateLore(ItemStack itemStack, String enchantPrefix, int level) {
        final ItemMeta meta = itemStack.getItemMeta();

        final List<String> lore = Objects.requireNonNullElse(meta.getLore(), new ArrayList<>());

        boolean loreExists = false;
        final String enchantLine = CC.GRAY + enchantPrefix + " " + ChatHelper.intToRoman(level);

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

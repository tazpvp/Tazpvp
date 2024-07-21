/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2023, n-tdi
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.tazpvp.tazpvp.guis.Shop;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.DataTypes;
import net.tazpvp.tazpvp.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.EnchantmentBookBuilder;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Maxim extends GUI {

    private int slotNum;
    private int num;
    private Player p;
    private final String prefix = CC.RED + "[Maxim] " + CC.WHITE;

    private final List<Material> wool = List.of(
            Material.ORANGE_WOOL,
            Material.PURPLE_WOOL,
            Material.YELLOW_WOOL,
            Material.LIME_WOOL,
            Material.GRAY_WOOL,
            Material.CYAN_WOOL,
            Material.BROWN_WOOL,
            Material.RED_WOOL,
            Material.BLACK_WOOL
    );
    private final List<Material> wood = List.of(
            Material.OAK_PLANKS,
            Material.ACACIA_PLANKS,
            Material.BIRCH_PLANKS,
            Material.BIRCH_PLANKS,
            Material.DARK_OAK_PLANKS,
            Material.JUNGLE_PLANKS,
            Material.SPRUCE_PLANKS
    );

    public Maxim(Player p) {
        super("Maxim", 6);
        this.p = p;
        addItems();
        open(p);
    }

    private void addItems() {
        slotNum = 10;
        num = 1;

        fill(0, 6*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        addButton(Button.create(ItemBuilder.of(Material.ENCHANTING_TABLE, 1)
                .name(CC.AQUA + "" + CC.BOLD + "Rebirth Shop")
                .lore(CC.GRAY + "Special items you can",CC.GRAY + "buy after you rebirth.")
                .glow(true)
                .build(), (e) -> {
            new Prestige(p);
        }), 40);

        setButton(1, "Azure Vapor", "Extinguish flames.", Material.BLUE_ORCHID, 10, true, true);
        setButton(3, "Inker", "Blind the enemies you slap.", Material.INK_SAC, 40, true, true);
        setButton(5, "Sticky Web", "Slow down your enemies.", Material.COBWEB, 10,false, false);
        setButton(1, "Lighter", "Set things afire.", Material.FLINT_AND_STEEL, 100, false, true);
        setButton(1, "Shulker", "Extra Storage.", Material.SHULKER_BOX, 2500, false, false);
        setButton(32, "Exp Bottle", "Mend your armor.", Material.EXPERIENCE_BOTTLE, 64, false, false);
        setChangingButton("Plank", "Placeable Blocks", wood, 30, 64);

        setButton(1, "Hatchet", "Break wooden blocks.", Material.GOLDEN_AXE, 40, false, true);
        setButton(1, "Shear", "Break wool blocks.", Material.SHEARS, 20, false, true);
        setButton(5, "Arrow", "Projectiles.", Material.ARROW, 50, false, false);
        setButton(5, "Steak", "We have the meats.", Material.COOKED_BEEF, 30, false, false);
        setButton(5, "Gold Carrot", "Good nutrition.", Material.GOLDEN_CARROT, 100, false, false);
        setButton(5, "Push Bomb", "Push everyone away from you.", Material.TNT, 225, true, true);
        setChangingButton("Wool", "Placeable Blocks", wool, 30, 64);

        setButton(5, "Spectral Arrow", "Highlight targets.", Material.SPECTRAL_ARROW, 90, false, false);
        setButton(1, "Crossbow", "Stronger than the bow.", Material.CROSSBOW, 90, false, false);
        setButton(1, "Sharpness", "Deal more sword damage.", Material.ENCHANTED_BOOK, 230, Enchantment.SHARPNESS);
        setButton(1, "Protection", "Take less damage.", Material.ENCHANTED_BOOK, 375, Enchantment.PROTECTION);
        setButton(1, "Power", "Deal more damage with your bow.", Material.ENCHANTED_BOOK, 600, Enchantment.POWER);
        setButton(1, "Mending", "Heal armor with xp bottles.", Material.ENCHANTED_BOOK, 100, Enchantment.MENDING);
        setButton(1, "Fire Aspect", "Hit and set things on fire.", Material.ENCHANTED_BOOK, 450, Enchantment.FIRE_ASPECT);

        update();
    }

    private void setButton(int amount, String name, String lore, Material material, int cost, boolean glow, boolean custom) {
        String name2 = ChatFunctions.gradient("#db3bff", name, true);
        ItemStack item;
        if (custom) {
            item = ItemBuilder.of(material, amount).name(name2).lore(CC.GRAY + lore).build();
        } else {
            item = ItemBuilder.of(material, amount).build();
        }

        addButton(Button.create(ItemBuilder.of(material, amount)
                .name(CC.YELLOW + "" + CC.BOLD + name)
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: " + cost + " Coins")
                .glow(glow)
                .build(), (e) -> {
            checkMoney(name2, cost, item, null);
        }), slotNum);
        calcSlot();
    }

    private void setButton(int amount, String name, String lore, Material material, int cost, Enchantment enchant) {
        String name2 = ChatFunctions.gradient("#db3bff", name, true);

        addButton(Button.create(ItemBuilder.of(material, amount)
                .name(CC.YELLOW + "" + CC.BOLD + name)
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: " + cost + " Coins", CC.RED + "Drag the enchant onto", CC.RED + "an item to combine")
                .build(), (e) -> {
            checkMoney(name2, cost, ItemBuilder.of(material, amount).build(), enchant);
        }), slotNum);
        calcSlot();
    }

    private void setChangingButton(String name, String text, List<Material> list, int cost, int amount) {
        int slot = slotNum;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getDestroyed()) {
                    this.cancel();
                } else {
                    clearSlot(25);
                    int num = new Random().nextInt(list.size());
                    addButton(Button.create(ItemBuilder.of(list.get(num), 1)
                            .name(CC.YELLOW + "" + CC.BOLD + name)
                            .lore(CC.GOLD + text, " ", CC.GRAY + "Cost: " + cost + " Coins")
                            .build(), (e) -> {

                        if (list.contains(Material.RED_WOOL)) {
                            if (!ChatFunctions.hasPremium(p, prefix)) return;
                        }
                        String name2 = ChatFunctions.gradient("#db3bff", name, true);
                        checkMoney(name2, cost, ItemBuilder.of(list.get(num), amount).build(), null);
                    }), slot);
                }
            }
        }.runTaskTimer(Tazpvp.getInstance(), 1, 20);
        calcSlot();
    }

    private void checkMoney(String name, int cost, ItemStack item, @Nullable Enchantment enchantment) {
        if (PersistentData.getInt(p, DataTypes.COINS) >= cost) {
            PersistentData.remove(p, DataTypes.COINS, cost);
            if (enchantment == null) {
                p.getInventory().addItem(item);
            } else {
                p.getInventory().addItem(new EnchantmentBookBuilder().enchantment(enchantment, 1).build());
            }
            p.sendMessage(prefix + "You purchased: " + name);
            p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1, 1);
        } else {
            p.sendMessage(prefix + "You don't have enough money");
        }
    }

    public void calcSlot() {
        if (num % 7 == 0) {
            slotNum += 2;
            num = 0;
        }
        slotNum ++;
        num ++;
    }
}

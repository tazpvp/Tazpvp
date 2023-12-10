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
import net.tazpvp.tazpvp.items.UsableItem;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
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

    public Maxim(Player p) {
        super("Maxim", 6);
        this.p = p;
        addItems();
        open(p);
    }

    private void addItems() {
        List<Material> wool = List.of(
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
        List<Material> wood = List.of(
                Material.OAK_PLANKS,
                Material.ACACIA_PLANKS,
                Material.BIRCH_PLANKS,
                Material.BIRCH_PLANKS,
                Material.DARK_OAK_PLANKS,
                Material.JUNGLE_PLANKS,
                Material.SPRUCE_PLANKS
        );

        slotNum = 10;
        num = 1;

        fill(0, 6*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        setButton("Azure Vapor", "Extinguish flames.", Material.BLUE_ORCHID, 30, 1);
        setButton("Sticky Web", "Slow down your enemies.", Material.COBWEB, 30, 5);
        setButton("Ink Splash", "Blind your enemies.", Material.INK_SAC, 30, 3);
        setButton("Lighter", "Set things afire.", Material.FLINT_AND_STEEL, 30, 1);
        setButton("Rusty Shield", "One-time-use shield.", Material.SHIELD, 30, 1);
        setButton("Exp Bottle", "Mend your armor.", Material.EXPERIENCE_BOTTLE, 30, 1);
        setChangingButton("Wooden Planks", "Placeable Blocks", wood, 30, 64);

        setButton("Hatchet", "Break wooden blocks.", Material.GOLDEN_AXE, 30, 1);
        setButton("Shears", "Break wool blocks.", Material.SHEARS, 30, 1);
        setButton("Arrows", "Projectiles.", Material.ARROW, 30, 5);
        setButton("Steak", "We have the meats.", Material.COOKED_BEEF, 30, 5);
        setButton("Gold Carrot", "Good nutrition.", Material.GOLDEN_CARROT, 30, 5);
        setButton("Gold Apple", "Only for rich people.", Material.GOLDEN_APPLE, 30, 1);
        setChangingButton("RGB Blocks", "RGB Placeable Blocks", wool, 30, 64);

        setButton("Spectral Arrow", "Highlight targets.", Material.SPECTRAL_ARROW, 30, 1);
        setButton("Crossbow", "Stronger than the bow.", Material.CROSSBOW, 30, 1);
        setButton("Mending", "Heal armor with xp bottles.", Material.ENCHANTED_BOOK, 30, 1, Enchantment.MENDING);
        setButton("Sharpness", "Deal more sword damage.", Material.ENCHANTED_BOOK, 30, 1, Enchantment.DAMAGE_ALL);
        setButton("Unbreaking", "Fortify your tools.", Material.ENCHANTED_BOOK, 30, 1, Enchantment.DURABILITY);
        setButton("Protection", "Take less damage.", Material.ENCHANTED_BOOK, 30, 1, Enchantment.PROTECTION_ENVIRONMENTAL);
        setButton("Projectile Protection", "Take less damage to projectiles.", Material.ENCHANTED_BOOK, 30, 1, Enchantment.PROTECTION_PROJECTILE);

        setButton("Fire Protection", "Take less damage to fire.", Material.ENCHANTED_BOOK, 30, 1, Enchantment.PROTECTION_FIRE);
        setButton("Sweeping Edge", "Increase attack range.", Material.ENCHANTED_BOOK, 30, 1, Enchantment.SWEEPING_EDGE);
        setButton("Punch", "Shoot players back further.", Material.ENCHANTED_BOOK, 30, 1, Enchantment.ARROW_KNOCKBACK);
        setButton("Knockback", "Hit players back further.", Material.ENCHANTED_BOOK, 30, 1, Enchantment.KNOCKBACK);
        setButton("Flame", "Shoot and set things on fire.", Material.ENCHANTED_BOOK, 30, 1, Enchantment.ARROW_FIRE);
        setButton("Fire Aspect", "Hit and set things on fire.", Material.ENCHANTED_BOOK, 30, 1, Enchantment.FIRE_ASPECT);
        setButton("Shard", "Valuable gem.", Material.AMETHYST_SHARD, 560, 3);

        update();
    }

    private void setButton(String name, String text, Material mat, int cost, int amount) {
        addButton(Button.create(ItemBuilder.of(mat, amount).name(CC.YELLOW + "" + CC.BOLD + name).lore(CC.GOLD + text, " ", CC.GRAY + "Cost: $" + cost).build(), (e) -> {
            String name2 = ChatFunctions.gradient("#db3bff", name, true);
            checkMoney(cost, name2, mat, amount, null);
        }), slotNum);
        calcSlot();
    }

    private void setButton(String name, String text, Material mat, int cost, int amount, Enchantment enchantment) {
        addButton(Button.create(ItemBuilder.of(mat, amount).name(CC.YELLOW + "" + CC.BOLD + name).lore(CC.GOLD + text, " ", CC.GRAY + "Cost: $" + cost).build(), (e) -> {
            String name2 = ChatFunctions.gradient("#db3bff", name, true);
            checkMoney(cost, name2, mat, amount, enchantment);
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
                            .lore(CC.GOLD + text, " ", CC.GRAY + "Cost: " + cost)
                            .build(), (e) -> {

                        checkMoney(cost, name, list.get(num), amount, null);

                    }), slot);
                }
            }
        }.runTaskTimer(Tazpvp.getInstance(), 1, 20);
        calcSlot();
    }

    private void checkMoney(int cost, String name, Material mat, int amount, @Nullable Enchantment enchantment) {
        if (PersistentData.getInt(p, DataTypes.COINS) >= cost) {
            PersistentData.remove(p, DataTypes.COINS, cost);
            if (enchantment == null) {
                p.getInventory().addItem(ItemBuilder.of(mat, amount).name(name).build());
            } else {
                p.getInventory().addItem(new EnchantmentBookBuilder().enchantment(enchantment, 1).build());
            }

            p.sendMessage("you purchased " + name);
            p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1, 1);
        } else {
            p.sendMessage("You don't have enough money");
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

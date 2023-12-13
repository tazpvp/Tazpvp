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
import net.tazpvp.tazpvp.utils.data.Rank;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
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

        setButton(1, "Azure Vapor", "Extinguish flames.", Material.BLUE_ORCHID, 10, true);
        setButton(5, "Sticky Web", "Slow down your enemies.", Material.BLUE_ORCHID, 10,false);
        setButton(3, "Ink Splash", "Blind your enemies.", Material.BLUE_ORCHID, 40, true);
        setButton(1, "Lighter", "Set things afire.", Material.BLUE_ORCHID, 100, false);
        setButton(3, "Chorus Fruit", "Teleport?", Material.BLUE_ORCHID, 20, false);
        setButton(32, "Exp Bottle", "Mend your armor.", Material.BLUE_ORCHID, 64, false);
        setChangingButton("Plank", "Placeable Blocks", wood, 30, 64);

        setButton(1, "Hatchet", "Break wooden blocks.", Material.BLUE_ORCHID, 40, false);
        setButton(1, "Shear", "Break wool blocks.", Material.BLUE_ORCHID, 20, false);
        setButton(5, "Arrow", "Projectiles.", Material.BLUE_ORCHID, 50, false);
        setButton(5, "Steak", "We have the meats.", Material.BLUE_ORCHID, 30, false);
        setButton(5, "Gold Carrot", "Good nutrition.", Material.BLUE_ORCHID, 30, false);
        setButton(1, "Gold Apple", "Only for rich people.", Material.BLUE_ORCHID, 170, false);
        setChangingButton("Wool", "Placeable Blocks", wool, 30, 64);

        setButton(5, "Spectral Arrow", "Highlight targets.", Material.BLUE_ORCHID, 90, false);
        setButton(1, "Crossbow", "Stronger than the bow.", Material.BLUE_ORCHID, 30, false);
        setButton(5, "Push Bomb", "Instantly push everyone away from you.", Material.BLUE_ORCHID, 225, true);
        setButton(1, "Sharpness", "Deal more sword damage.", Material.BLUE_ORCHID, 230, Enchantment.DAMAGE_ALL);
        setButton(1, "Unbreaking", "Fortify your tools.", Material.BLUE_ORCHID, 30, Enchantment.DURABILITY);
        setButton(1, "Protection", "Take less damage.", Material.BLUE_ORCHID, 70, Enchantment.PROTECTION_ENVIRONMENTAL);
        setButton(1, "Projectile Protection", "Take less projectile damage.", Material.BLUE_ORCHID, 30, Enchantment.PROTECTION_PROJECTILE);

        setButton(1, "Fire Protection", "Take less damage to fire.", Material.BLUE_ORCHID, 30, Enchantment.PROTECTION_FIRE);
        setButton(1, "Sweeping Edge", "Increase attack range.", Material.BLUE_ORCHID, 120, Enchantment.SWEEPING_EDGE);
        setButton(1, "Punch", "Shoot players back further.", Material.BLUE_ORCHID, 150, Enchantment.ARROW_KNOCKBACK);
        setButton(1, "Knockback", "Hit players back further.", Material.BLUE_ORCHID, 175, Enchantment.KNOCKBACK);
        setButton(1, "Flame", "Shoot and set things on fire.", Material.BLUE_ORCHID, 250, Enchantment.ARROW_FIRE);
        setButton(1, "Fire Aspect", "Hit and set things on fire.", Material.BLUE_ORCHID, 250, Enchantment.FIRE_ASPECT);
        setButton(1, "Mending", "Heal armor with xp bottles.", Material.BLUE_ORCHID, 40, Enchantment.MENDING);

        update();
    }

    private void setButton(int amount, String name, String lore, Material material, int cost, boolean glow) {
        String name2 = ChatFunctions.gradient("#db3bff", name, true);
        ItemStack item = ItemBuilder.of(material, amount).name(name2).lore(lore).glow(glow).build();

        addButton(Button.create(ItemBuilder.of(item.getType(), item.getAmount())
                .name(CC.YELLOW + "" + CC.BOLD + name)
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: $" + cost)
                .glow(glow)
                .build(), (e) -> {
            checkMoney(name2, cost, item, null);
        }), slotNum);
        calcSlot();
    }

    private void setButton(int amount, String name, String lore, Material material, int cost, Enchantment enchant) {
        String name2 = ChatFunctions.gradient("#db3bff", name, true);
        ItemStack item = ItemBuilder.of(material, amount).name(name).lore(lore).build();

        addButton(Button.create(ItemBuilder.of(item.getType(), item.getAmount())
                .name(CC.YELLOW + "" + CC.BOLD + name)
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: $" + cost)
                .build(), (e) -> {
            checkMoney(name2, cost, item, enchant);
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

                        if (list.contains(Material.RED_WOOL)) {
                            if (!ChatFunctions.hasPremium(p, prefix)) return;
                        }
                        String name2 = ChatFunctions.gradient("#db3bff", name, true);
                        checkMoney(name2, cost, ItemBuilder.of(list.get(num), amount).name(name2).build(), null);
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

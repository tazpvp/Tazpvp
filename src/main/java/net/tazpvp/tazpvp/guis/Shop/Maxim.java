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
import org.bukkit.Bukkit;
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
        Tazpvp.getObservers().forEach(observer -> observer.gui(p, prefix));
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

        setButton("Azure Vapor", "Extinguish flames.", 10, null,
                ItemBuilder.of(Material.BLUE_ORCHID).glow(true).build());
        setButton("Sticky Web", "Slow down your enemies.", 10, null,
                ItemBuilder.of(Material.COBWEB, 5).build());
        setButton("Ink Splash", "Blind your enemies.", 40, null,
                ItemBuilder.of(Material.INK_SAC, 3).glow(true).build());
        setButton("Lighter", "Set things afire.", 100, null,
                ItemBuilder.of(Material.FLINT_AND_STEEL).build());
        setButton("Chorus Fruit", "Teleport?", 20, null,
                ItemBuilder.of(Material.CHORUS_FRUIT).build());
        setButton("Exp Bottle", "Mend your armor.", 64, null,
                ItemBuilder.of(Material.EXPERIENCE_BOTTLE, 32).build());
        setChangingButton("Plank", "Placeable Blocks", wood, 30, 64);

        setButton("Hatchet", "Break wooden blocks.", 40, null,
                ItemBuilder.of(Material.GOLDEN_AXE).build());
        setButton("Shear", "Break wool blocks.", 20, null,
                ItemBuilder.of(Material.SHEARS).build());
        setButton("Arrow", "Projectiles.", 50, null,
                ItemBuilder.of(Material.ARROW, 5).build());
        setButton("Steak", "We have the meats.", 30, null,
                ItemBuilder.of(Material.BEEF, 5).build());
        setButton("Gold Carrot", "Good nutrition.", 30, null,
                ItemBuilder.of(Material.GOLDEN_CARROT, 5).build());
        setButton("Gold Apple", "Only for rich people.", 170, null,
                ItemBuilder.of(Material.GOLDEN_APPLE).build());
        setChangingButton("Wool", "Placeable Blocks", wool, 30, 64);

        setButton("Spectral Arrow", "Highlight targets.", 90, null,
                ItemBuilder.of(Material.SPECTRAL_ARROW, 5).build());
        setButton("Crossbow", "Stronger than the bow.", 30, null,
                ItemBuilder.of(Material.CROSSBOW).build());
        setButton("Push Bomb", "Instantly push everyone away from you.", 225, null,
                ItemBuilder.of(Material.TNT, 5).glow(true).build());
        setButton("Sharpness", "Deal more sword damage.", 230, Enchantment.DAMAGE_ALL,
                ItemBuilder.of(Material.ENCHANTED_BOOK).build());
        setButton("Unbreaking", "Fortify your tools.", 30, Enchantment.DURABILITY,
                ItemBuilder.of(Material.ENCHANTED_BOOK).build());
        setButton("Protection", "Take less damage.", 70, Enchantment.PROTECTION_ENVIRONMENTAL,
                ItemBuilder.of(Material.ENCHANTED_BOOK).build());
        setButton("Projectile Protection", "Take less projectile damage.", 30, Enchantment.PROTECTION_PROJECTILE,
                ItemBuilder.of(Material.ENCHANTED_BOOK).build());

        setButton("Fire Protection", "Take less damage to fire.", 30, Enchantment.PROTECTION_FIRE,
                ItemBuilder.of(Material.ENCHANTED_BOOK).build());
        setButton("Sweeping Edge", "Increase attack range.", 120, Enchantment.SWEEPING_EDGE,
                ItemBuilder.of(Material.ENCHANTED_BOOK).build());
        setButton("Punch", "Shoot players back further.", 150, Enchantment.ARROW_KNOCKBACK,
                ItemBuilder.of(Material.ENCHANTED_BOOK).build());
        setButton("Knockback", "Hit players back further.", 175, Enchantment.KNOCKBACK,
                ItemBuilder.of(Material.ENCHANTED_BOOK).build());
        setButton("Flame", "Shoot and set things on fire.", 250, Enchantment.ARROW_FIRE,
                ItemBuilder.of(Material.ENCHANTED_BOOK).build());
        setButton("Fire Aspect", "Hit and set things on fire.", 250, Enchantment.FIRE_ASPECT,
                ItemBuilder.of(Material.ENCHANTED_BOOK).build());
        setButton("Mending", "Heal armor with xp bottles.", 40, Enchantment.MENDING,
                ItemBuilder.of(Material.ENCHANTED_BOOK).build());

        update();
    }

    private void setButton(String name, String lore, int cost, @Nullable Enchantment enchant, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        String name2 = ChatFunctions.gradient("#db3bff", name, true);
        item.getItemMeta().setDisplayName(name2);

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

                        PlayerWrapper pw = PlayerWrapper.getPlayer(p);
                        if (list.contains(Material.RED_WOOL)) {
                            if (ChatFunctions.requiresPremium(p, prefix)) {
                                return;
                            }
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

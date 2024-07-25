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

package net.tazpvp.tazpvp.npc.characters.shop.gui.subgui;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.enums.Items;
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

public class ItemShop extends GUI {

    private int slotNum;
    private int num;
    private Player p;
    private final String prefix = CC.RED + "[Maxim] " + CC.WHITE;
    private final PlayerStatEntity playerStatEntity;
    private final PlayerStatService playerStatService;

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

    public ItemShop(Player p, PlayerStatService playerStatService) {
        super("Maxim", 6);
        this.p = p;
        this.playerStatService = playerStatService;
        this.playerStatEntity = playerStatService.getOrDefault(p.getUniqueId());
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
            new PrestigeShop(p);
        }), 40);

        addBuyButton(Items.AZURE_VAPOR, 10, 1, true);
        addBuyButton(Items.INKER, 40, 3, true);
        addBuyButton(Items.STICKY_WEB, 10, 5, true);
        addBuyButton(Items.LIGHTER, 100, 1, false);
        addBuyButton(Items.EXP_BOTTLE, 64, 32, false);
        addBuyButton(Items.HATCHET, 40, 1, false);
        setChangingButton("Plank", "Placeable Blocks", wood, 30, 64);

        addBuyButton(Items.SHEAR, 20, 1, false);
        addBuyButton(Items.ARROW, 50, 5, false);
        addBuyButton(Items.GOLD_CARROT, 100, 5, false);
        addBuyButton(Items.PUSH_BOMB, 225, 5, true);
        addBuyButton(Items.SPECTRAL_ARROW, 90, 5, false);
        setChangingButton("Wool", "Placeable Blocks", wool, 30, 64);

        addBuyButton(Items.SHARPNESS, 230, 1, Enchantment.SHARPNESS);
        addBuyButton(Items.PROTECTION, 375, 1, Enchantment.PROTECTION);
        addBuyButton(Items.POWER, 600, 1, Enchantment.POWER);
        addBuyButton(Items.MENDING, 100, 1, Enchantment.MENDING);
        addBuyButton(Items.FIRE_ASPECT, 450, 1, Enchantment.FIRE_ASPECT);

        update();
    }

    private void addBuyButton(Items customItem, int cost, int amount, boolean glow) {
        ItemStack item = customItem.getShopItem(cost, amount, glow);
        addButton(Button.create(item, (e) -> {
            checkMoney(cost, customItem, null);
        }), slotNum);
        calcSlot();
    }

    private void addBuyButton(Items customItem, int cost, int amount, Enchantment enchant) {
        ItemStack item = customItem.getShopEnchant(cost, amount);
        addButton(Button.create(item, (e) -> {
            checkMoney(cost, customItem, enchant);
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

    private void checkMoney(int cost, Items item, @Nullable Enchantment enchantment) {
        if (playerStatEntity != null) {
            if (playerStatEntity.getCoins() >= cost) {
                playerStatEntity.setCoins(playerStatEntity.getCoins() - cost);
                if (enchantment == null) {
                    p.getInventory().addItem(item.getItem());
                } else {
                    p.getInventory().addItem(new EnchantmentBookBuilder().enchantment(enchantment, 1).build());
                }
                p.sendMessage(prefix + "You purchased: " + item.getName());
                p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1, 1);
            } else {
                p.sendMessage(prefix + "You don't have enough money");
            }
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

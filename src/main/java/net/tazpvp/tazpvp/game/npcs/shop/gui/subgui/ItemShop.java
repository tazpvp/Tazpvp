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

package net.tazpvp.tazpvp.game.npcs.shop.gui.subgui;

import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.ItemEnum;
import net.tazpvp.tazpvp.enums.StatEnum;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.EnchantmentBookBuilder;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public class ItemShop extends GUI {

    private int slotNum;
    private int count;
    private final Player p;
    private final UUID id;
    private final String prefix = CC.RED + "[Maxim] " + CC.WHITE;

    public ItemShop(Player p) {
        super("Maxim", 6);
        this.p = p;
        this.id = p.getUniqueId();
        addItems();
        open(p);
    }

    private void addItems() {
        slotNum = 10;
        count = 1;

        fill(0, 6*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        addButton(Button.create(ItemBuilder.of(Material.ENCHANTING_TABLE, 1)
                .name(CC.AQUA + "" + CC.BOLD + "Rebirth Shop")
                .lore(CC.GRAY + "Special items you can",CC.GRAY + "buy after you rebirth.")
                .glow(true)
                .build(), (_) -> new PrestigeShop(p)), 40);

        addBuyButton(1, 10, true, ItemEnum.AZURE_VAPOR);
        addBuyButton(3, 40, true, ItemEnum.INKER);
        addBuyButton(5, 10, true, ItemEnum.STICKY_WEB);
        addBuyButton(5, 225, true, ItemEnum.PUSH_BOMB);
        addBuyButton(32, 64, false, ItemEnum.EXP_BOTTLE);
        addBuyButton(1, 40, false, ItemEnum.HATCHET);
        addMenuButton(ItemEnum.BLOCKS);

        addBuyButton(530, ItemEnum.SHARPNESS);
        addBuyButton(375, ItemEnum.PROTECTION);
        addBuyButton(450, ItemEnum.FIRE_ASPECT);
        addBuyButton(160, ItemEnum.UNBREAKING);
        addBuyButton(220, ItemEnum.SWEEPING_EDGE);
        addBuyButton(275, ItemEnum.KNOCKBACK);
        addBuyButton(450, ItemEnum.FLAME);

        update();
    }

    private void addBuyButton(int amount, int cost, boolean glow, ItemEnum customItem) {
        ItemStack item = customItem.getShopItem(cost, amount, glow);
        addButton(Button.create(item, (_) -> checkMoney(cost, amount, customItem, null)), slotNum);
        calcSlot();
    }

    private void addBuyButton(int cost, ItemEnum customItem) {
        ItemStack item = customItem.getShopEnchant(cost, 1);
        addButton(Button.create(item, (_) -> checkMoney(cost, 1, customItem, customItem.getEnchant())), slotNum);
        calcSlot();
    }

    private void addMenuButton(ItemEnum customItem) {
        ItemStack item = customItem.getItem(1);
        addButton(Button.create(item, (_) -> new BlockShop(p)), slotNum);
        calcSlot();
    }

    private void checkMoney(int cost, int amount, ItemEnum item, @Nullable Enchantment enchantment) {
        if (StatEnum.COINS.getInt(id) >= cost) {
            StatEnum.COINS.remove(id, cost);
            if (enchantment == null) {
                dropItem(p, item.getItem(amount));
            } else {
                p.getInventory().addItem();
                dropItem(p, new EnchantmentBookBuilder().enchantment(enchantment, 1).build());
            }
            p.sendMessage(prefix + "You purchased: " + item.getName());
            p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1, 1);

        } else {
            p.sendMessage(prefix + "You don't have enough money");
        }
    }

    public void calcSlot() {
        if (count % 7 == 0) {
            slotNum += 2;
            count = 0;
        }
        slotNum ++;
        count++;
    }

    private void dropItem(Player p, ItemStack item) {
        PlayerInventory inventory = p.getInventory();
        Map<Integer, ItemStack> remainingItems = inventory.addItem(item);
        if (!remainingItems.isEmpty()) {
            World world = p.getWorld();
            for (ItemStack leftover : remainingItems.values()) {
                world.dropItemNaturally(p.getLocation(), leftover);
            }
        }
    }
}

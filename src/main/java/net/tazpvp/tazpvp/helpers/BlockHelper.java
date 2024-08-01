/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2022, n-tdi
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

package net.tazpvp.tazpvp.helpers;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.enums.EnchantEnum;
import net.tazpvp.tazpvp.objects.OreObject;
import net.tazpvp.tazpvp.objects.PickaxeObject;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockHelper {

    public static final List<OreObject> ores = new ArrayList<>(Arrays.asList(
            new OreObject(20*9, 2, 1, Material.COAL_ORE, Material.COAL, "stone"),
            new OreObject(20*13, 3, 2, Material.GOLD_ORE, Material.RAW_GOLD, "iron"),
            new OreObject(20*16, 4, 3, Material.LAPIS_ORE, Material.LAPIS_LAZULI, "diamond"),
            new OreObject(20*21, 10, 4, Material.DIAMOND_ORE, Material.DIAMOND, "netherite"),
            new OreObject(20*25, 15, 4, Material.EMERALD_ORE, Material.EMERALD, "netherite")
    ));

    public static final List<PickaxeObject> pickaxes = new ArrayList<>(Arrays.asList(
            new PickaxeObject(new ItemStack(Material.STONE_PICKAXE), 400, 1, Material.IRON_PICKAXE),
            new PickaxeObject(new ItemStack(Material.IRON_PICKAXE), 700, 2, Material.DIAMOND_PICKAXE),
            new PickaxeObject(new ItemStack(Material.DIAMOND_PICKAXE), 1000, 3, Material.NETHERITE_PICKAXE),
            new PickaxeObject(new ItemStack(Material.NETHERITE_PICKAXE), 1500, 4, Material.NETHERITE_PICKAXE)
    ));

    public static void respawnOre(Player p, Block block, OreObject ore) {
        int time = ore.getTime();
        Material givenItem = ore.getMat();
        Material smelted = ore.getSmelted();
        int amount = 1;
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(Material.BEDROCK);
            }
        }.runTaskLater(Tazpvp.getInstance(), 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(ore.getMat());
            }
        }.runTaskLater(Tazpvp.getInstance(), time);

        if (getPickaxe(p) != null) {
            final ItemStack pickaxe = getPickaxe(p);
            final ItemMeta storageMeta = pickaxe.getItemMeta();
            if (storageMeta.hasEnchant(EnchantEnum.DOUBLE_ORES.getEnchant()))
                amount = 2;
            if (storageMeta.hasEnchant(EnchantEnum.AUTO_SMELT.getEnchant()))
                givenItem = smelted;
        }
        giveOre(p, givenItem, amount);
    }

    public static ItemStack getPickaxe(Player p) {
        for (PickaxeObject pick : pickaxes) {
            if (p.getInventory().getItemInMainHand().getType().equals(pick.getItem().getType())) {
                return p.getInventory().getItemInMainHand();
            } else if (p.getInventory().getItemInOffHand().getType().equals(pick.getItem().getType())) {
                return p.getInventory().getItemInOffHand();
            }
        }
        return null;
    }

    public static ItemStack getOreInHand(Player p) {
        for (OreObject ore : ores) {
            if (p.getInventory().getItemInMainHand().getType().equals(ore.getMat())) {
                return p.getInventory().getItemInMainHand();
            } else if (p.getInventory().getItemInOffHand().getType().equals(ore.getMat())) {
                return p.getInventory().getItemInOffHand();
            }
        }
        return null;
    }

    private static void giveOre(Player p, Material mat, int amount) {
        p.getInventory().addItem(new ItemStack(mat, amount));
        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
    }

    public static OreObject getOreFrom(Material mat) {
        for (OreObject ore : ores) {
            if (mat.equals(ore.getMat()) || mat.equals(ore.getSmelted())) {
                return ore;
            }
        }
        return null;
    }

    public static boolean isSmelted(Material mat) {
        for (OreObject ore : ores) {
            if (mat.equals(ore.getSmelted())) {
                return true;
            }
        }
        return false;
    }
}

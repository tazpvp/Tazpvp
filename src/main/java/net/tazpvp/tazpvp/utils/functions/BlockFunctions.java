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

package net.tazpvp.tazpvp.utils.functions;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.WeakHashMap;

public class BlockFunctions {

    public static WeakHashMap<Material, Integer> ores = new WeakHashMap<>();

    public static List<Material> pickaxes = List.of(
        Material.WOODEN_PICKAXE,
        Material.STONE_PICKAXE,
        Material.IRON_PICKAXE,
        Material.DIAMOND_PICKAXE,
        Material.GOLDEN_PICKAXE
    );

    public static void registerOres() {
        ores.put(Material.GOLD_ORE, 20*9);
        ores.put(Material.REDSTONE_ORE, 20*13);
        ores.put(Material.LAPIS_ORE, 20*16);
        ores.put(Material.DIAMOND_ORE, 20*21);
        ores.put(Material.EMERALD_ORE, 20*25);
    }

    public static void respawnOre(Player p, Block block, Material mat, Material smelted, int time) {

        block.setType(Material.BEDROCK);
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(mat);
            }
        }.runTaskLater(Tazpvp.getInstance(), time);

        if (getPickaxe(p) != null) {
            ItemStack pickaxe = getPickaxe(p);
            if (pickaxe.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) && pickaxe.containsEnchantment(Enchantment.SILK_TOUCH)){
                giveOre(p, smelted, 2);
            } else if (pickaxe.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)){
                giveOre(p, mat, 2);
            } else if (pickaxe.containsEnchantment(Enchantment.SILK_TOUCH)){
                giveOre(p, mat, 1);
            }
            giveOre(p, mat, 1);
            return;
        }
        giveOre(p, mat, 1);
    }

    private static ItemStack getPickaxe(Player p) {
        if (pickaxes.contains(p.getInventory().getItemInMainHand().getType())) {
            return p.getInventory().getItemInMainHand();
        } else if (pickaxes.contains(p.getInventory().getItemInOffHand().getType())) {
            return p.getInventory().getItemInOffHand();
        }
        return null;
    }

    private static void giveOre(Player p, Material mat, int amount) {
        p.getInventory().addItem(new ItemStack(mat, amount));
        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
    }

    public static Material getSmelted(Material block) {
        if (block == Material.GOLD_ORE) return Material.RAW_GOLD;
        if (block == Material.REDSTONE_ORE) return Material.REDSTONE;
        if (block == Material.LAPIS_ORE) return Material.LAPIS_LAZULI;
        if (block == Material.DIAMOND_ORE) return Material.DIAMOND;
        if (block == Material.EMERALD_ORE) return Material.EMERALD;
        return null;
    }
}

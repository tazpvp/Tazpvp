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

import net.tazpvp.tazpvp.utils.PDCUtil;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.List;
import java.util.UUID;

public class PlayerFunctions {

    public static List<Material> kitItems = List.of(
            Material.DIAMOND_HELMET,
            Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS,
            Material.DIAMOND_BOOTS,
            Material.DIAMOND_SWORD,
            Material.BOW,
            Material.IRON_PICKAXE
    );

    public static Integer getMaxHealth(Player p) {
        if (PersistentData.getInt(p, DataTypes.REBIRTH) >= 1)
            return 22;
        else
            return 20;
    }

    public static void healPlr(Player p) {
        if (PersistentData.getInt(p, DataTypes.REBIRTH) >= 1)
            p.setHealthScale(22.0);
        else
            p.setHealthScale(20.0);
    }

    public static void feedPlr(Player p) {
        p.setFoodLevel(20); //TODO: get max food
    }

    public static void kitPlayer(Player p) {
        Inventory inv = p.getInventory();

        p.getEquipment().setHelmet(ItemBuilder.of(Material.DIAMOND_HELMET, 1, "Helmet").build());
        p.getEquipment().setChestplate(ItemBuilder.of(Material.DIAMOND_CHESTPLATE, 1, "Chestplate").build());
        p.getEquipment().setLeggings(ItemBuilder.of(Material.DIAMOND_LEGGINGS, 1, "Leggings").build());
        p.getEquipment().setBoots(ItemBuilder.of(Material.DIAMOND_BOOTS, 1, "Boots").build());

        inv.addItem(ItemBuilder.of(Material.DIAMOND_SWORD, 1, "Sword").enchantment(Enchantment.DAMAGE_ALL, 3).build());
        inv.addItem(ItemBuilder.of(Material.BOW, 1, "Bow").build());
        inv.addItem(ItemBuilder.of(Material.IRON_PICKAXE, 1, "Pickaxe").build());

        inv.addItem(new ItemStack(Material.COOKED_BEEF, 20));
        inv.addItem(new ItemStack(Material.OAK_PLANKS, 64));

        inv.setItem(8, new ItemStack(Material.ARROW, 32));
    }
}

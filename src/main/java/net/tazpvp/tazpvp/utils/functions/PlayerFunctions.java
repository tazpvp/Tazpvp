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

import net.tazpvp.tazpvp.booster.ActiveBoosterManager;
import net.tazpvp.tazpvp.booster.BoosterBonus;
import net.tazpvp.tazpvp.booster.BoosterTypes;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.LooseData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.enums.GameItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.List;
import java.util.UUID;

import static net.tazpvp.tazpvp.utils.data.PersistentData.getInt;

public class PlayerFunctions {

    public static List<Material> kitItems = List.of(
            Material.DIAMOND_HELMET,
            Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS,
            Material.DIAMOND_BOOTS,
            Material.DIAMOND_SWORD,
            Material.BOW,
            Material.STONE_PICKAXE
    );

    public static Double getMaxHealth(Player p) {
        return p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
    }

    public static void healPlr(Player p) {
        if (PersistentData.getInt(p.getUniqueId(), DataTypes.PLAYTIMEUNIX) <= 5 * 60 * 60 * 1000) {
            p.setHealthScale(22.0);
        } else {
            p.setHealthScale(20.0);
        }
        p.setHealth(getMaxHealth(p));
    }

    public static void feedPlr(Player p) {
        p.setFoodLevel(20);
    }

    public static void resetHealth(Player p) {
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        healPlr(p);
    }

    public static ItemStack[] getKitItems(Player p) {
        return new ItemStack[] {
                ItemBuilder.of(Material.DIAMOND_SWORD, 1, CC.WHITE + p.getName() + "'s Sword").enchantment(Enchantment.DAMAGE_ALL, 1).build(),
                ItemBuilder.of(Material.BOW, 1, CC.WHITE + p.getName() + "'s Bow").build(),
                ItemBuilder.of(Material.STONE_PICKAXE, 1, CC.WHITE + p.getName() + "'s Pickaxe").build(),
                new ItemStack(Material.COOKED_BEEF, 20),
                new ItemStack(Material.OAK_PLANKS, 64),
                new ItemStack(Material.ARROW, 32)
        };
    }

    public static void kitPlayer(Player p) {
        Inventory inv = p.getInventory();

        armorPlayer(p);

        for (ItemStack kitItem : getKitItems(p)) {
            if (kitItem.getType() == Material.ARROW) {
                inv.setItem(8, kitItem);
            }
            inv.addItem(kitItem);
        }
    }

    public static void armorPlayer(Player p) {
        p.getEquipment().setHelmet(ItemBuilder.of(Material.DIAMOND_HELMET, 1, CC.WHITE + "Hard Hat").build());
        p.getEquipment().setChestplate(ItemBuilder.of(Material.DIAMOND_CHESTPLATE, 1, CC.WHITE + "Tunic").build());
        p.getEquipment().setLeggings(ItemBuilder.of(Material.DIAMOND_LEGGINGS, 1, CC.WHITE + "Pants").build());
        p.getEquipment().setBoots(ItemBuilder.of(Material.DIAMOND_BOOTS, 1, CC.WHITE + "Sandles").build());
    }

    public static int countShards(Player p) {
        int shardCount = 0;
        for (ItemStack i : p.getInventory()) {
            if (i == null) continue;
            if (i.getType() == Material.AMETHYST_SHARD) {
                shardCount = shardCount + i.getAmount();
            }
        }
        return shardCount;
    }

    public static void takeShards(Player p, int cost) {

        for (int n = 0; n < cost; n++) {
            for (ItemStack i : p.getInventory()) {
                if (i == null) continue;
                if (i.getType() == Material.AMETHYST_SHARD) {
                    if (i.getAmount() >= 2) {
                        i.setAmount(i.getAmount() - 1);
                    } else {
                        p.getInventory().remove(i);
                    }
                    break;
                }
            }
        }
    }

    public static void levelUp(UUID ID, float value) {
        Player p = Bukkit.getPlayer(ID);
        if (p == null) return;
        if (value >= LooseData.getExpLeft(ID)) {
            final BoosterBonus coinsBonus = ActiveBoosterManager.getInstance().calculateBonus(100, List.of(BoosterTypes.COINS, BoosterTypes.MEGA));
            final int coins = (int) coinsBonus.result();

            int num = (int) value - LooseData.getExpLeft(ID);
            PersistentData.set(ID, DataTypes.XP, num);
            PersistentData.add(ID, DataTypes.LEVEL);
            PersistentData.add(ID, DataTypes.COINS, coins);
            p.getInventory().addItem(GameItems.SHARD.getItem());
            p.setLevel(PersistentData.getInt(ID, DataTypes.LEVEL));
            p.setExp((float) num / LooseData.getExpLeft(p.getUniqueId()));
            ChatFunctions.announce(p, CC.AQUA + "" + CC.BOLD + "  LEVEL UP " + CC.DARK_AQUA + "Combat Lvl. " + CC.AQUA + getInt(ID, DataTypes.LEVEL), Sound.ENTITY_PLAYER_LEVELUP);
            p.sendMessage(CC.DARK_GRAY + "  ▶ " + CC.GOLD + coins + " Coins " + coinsBonus.prettyPercentMultiplier());
            p.sendMessage(CC.DARK_GRAY + "  ▶ " + CC.DARK_AQUA + "1 Shard");
            p.sendMessage("");
        } else {
            p.setExp(value / LooseData.getExpLeft(p.getUniqueId()));
        }
    }

    public static void addHealth(Player p, int amount) {
        if ((p.getHealth() + amount) >= PlayerFunctions.getMaxHealth(p)) {
            healPlr(p);
            p.setHealth(PlayerFunctions.getMaxHealth(p));
        } else {
            p.setHealth(p.getHealth() + amount);
        }
    }
}

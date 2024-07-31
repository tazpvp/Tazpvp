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
import net.tazpvp.tazpvp.data.LooseData;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.game.booster.ActiveBoosterManager;
import net.tazpvp.tazpvp.game.booster.BoosterBonus;
import net.tazpvp.tazpvp.game.booster.BoosterTypes;
import net.tazpvp.tazpvp.game.items.StaticItems;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.Bukkit;
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

public class PlayerFunctions {

    private static final PlayerStatService playerStatService = Tazpvp.getInstance().getPlayerStatService();

    private static Double getMaxHealth(Player p) {
        return p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
    }

    public static void healPlr(Player p) {
        PlayerStatEntity playerStatEntity = Tazpvp.getInstance().getPlayerStatService().getOrDefault(p.getUniqueId());
        long playTime = playerStatEntity.getPlaytime();
        int thirtyMinutesInMs = 30 * 60 * 1000;

        if (playTime <= thirtyMinutesInMs) {
            long remainingTimeInMs = thirtyMinutesInMs - playTime;
            long remainingMinutes = remainingTimeInMs / (60 * 1000);

            p.sendMessage("");
            p.sendMessage(CC.YELLOW + "You have " + remainingMinutes + " minutes of starter health buff remaining.");
            p.sendMessage("");

            p.setHealthScale(24.0);
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
                ItemBuilder.of(Material.DIAMOND_SWORD, 1, kitItemName(p,"Sword"))
                        .enchantment(Enchantment.SHARPNESS, 1).build(),
                ItemBuilder.of(Material.BOW, 1, kitItemName(p,"Bow"))
                        .enchantment(Enchantment.INFINITY, 1).build(),
                ItemBuilder.of(Material.STONE_PICKAXE, 1, kitItemName(p,"Pickaxe")).build(),
                ItemBuilder.of(Material.CROSSBOW, 1, kitItemName(p,"Crossbow")).build(),
                ItemBuilder.of(Material.OAK_PLANKS, 64).build(),
                ItemBuilder.of(Material.ARROW, 32).build()
        };
    }

    private static String kitItemName(Player p, String text) {
        return ChatFunctions.gradient("#04f000", p.getName() + "'s " + text, true);
    }

    public static void kitPlayer(Player p) {
        PlayerStatEntity pStatEntity = playerStatService.getOrDefault(p.getUniqueId());
        Inventory inv = p.getInventory();
        int thirtyMinutesInMs = 30 * 60 * 1000;

        if (pStatEntity.getPlaytime() <= thirtyMinutesInMs) {
            p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
        }

        armorPlayer(p);

        for (ItemStack kitItem : getKitItems(p)) {
            if (kitItem.getType() == Material.ARROW) {
                inv.setItem(8, kitItem);
            }
            inv.addItem(kitItem);
        }
    }

    public static void armorPlayer(Player p) {
        ItemStack[] armor = new ItemStack[] {
                ItemBuilder.of(Material.DIAMOND_HELMET, 1, CC.WHITE + "Hard Hat")
                        .enchantment(Enchantment.MENDING, 1).build(),
                ItemBuilder.of(Material.DIAMOND_CHESTPLATE, 1, CC.WHITE + "Tunic")
                        .enchantment(Enchantment.MENDING, 1).build(),
                ItemBuilder.of(Material.DIAMOND_LEGGINGS, 1, CC.WHITE + "Pants")
                        .enchantment(Enchantment.MENDING, 1).build(),
                ItemBuilder.of(Material.DIAMOND_BOOTS, 1, CC.WHITE + "Sandles")
                        .enchantment(Enchantment.MENDING, 1).build()
        };
        p.getInventory().setArmorContents(armor);
    }

    public static void levelUp(UUID ID, float value) {
        Player p = Bukkit.getPlayer(ID);
        PlayerStatEntity pStatEntity = playerStatService.getOrDefault(ID);
        if (p == null) return;
        if (value >= LooseData.getExpLeft(ID)) {
            final BoosterBonus coinsBonus = ActiveBoosterManager.getInstance()
                    .calculateBonus(100, List.of(BoosterTypes.COINS, BoosterTypes.MEGA));
            final int coins = (int) coinsBonus.result();

            int num = (int) value - LooseData.getExpLeft(ID);
            pStatEntity.setXp(num);
            pStatEntity.setLevel(pStatEntity.getLevel() + 1);
            pStatEntity.setCoins(pStatEntity.getCoins() + coins);
            p.getInventory().addItem(StaticItems.SHARD.item(1));
            p.setLevel(pStatEntity.getLevel());
            p.setExp((float) num / LooseData.getExpLeft(ID));
            ChatFunctions.announce(p, CC.AQUA + "" + CC.BOLD + "  LEVEL UP " + CC.DARK_AQUA + "Combat Lvl. " + CC.AQUA + pStatEntity.getLevel(), Sound.ENTITY_PLAYER_LEVELUP);
            p.sendMessage(CC.DARK_GRAY + "  ▶ " + CC.GOLD + coins + " Coins " + coinsBonus.prettyPercentMultiplier());
            p.sendMessage(CC.DARK_GRAY + "  ▶ " + CC.DARK_AQUA + "1 Shard");
            p.sendMessage("");
        } else {
            p.setExp(value / LooseData.getExpLeft(ID));
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

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
import net.tazpvp.tazpvp.enums.ItemEnum;
import net.tazpvp.tazpvp.enums.ScoreboardEnum;
import net.tazpvp.tazpvp.game.booster.ActiveBoosterManager;
import net.tazpvp.tazpvp.game.booster.BoosterBonus;
import net.tazpvp.tazpvp.game.booster.BoosterTypes;
import net.tazpvp.tazpvp.enums.StaticItems;
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

import java.util.List;
import java.util.UUID;

public class PlayerHelper {

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
                ItemEnum.KIT_SWORD.getKitTool(p.getName(), Enchantment.SHARPNESS),
                ItemEnum.KIT_BOW.getKitTool(p.getName(), Enchantment.INFINITY),
                ItemEnum.KIT_PICKAXE.getKitTool(p.getName()),
                ItemEnum.KIT_CROSSBOW.getKitTool(p.getName()),
                ItemEnum.BLOCKS.getItem(64),
                ItemEnum.ARROW.getItem(15),
        };
    }

    public static void kitPlayer(Player p) {
        PlayerStatEntity pStatEntity = playerStatService.getOrDefault(p.getUniqueId());
        Inventory inv = p.getInventory();
        int thirtyMinutesInMs = 30 * 60 * 1000;

        armorPlayer(p);

        for (ItemStack kitItem : getKitItems(p)) {
            if (kitItem.getType() == Material.ARROW) {
                inv.setItem(8, kitItem);
            }
            inv.addItem(kitItem);
        }

        if (pStatEntity.getPlaytime() <= thirtyMinutesInMs) {
            p.getInventory().addItem(ItemEnum.GOLDEN_APPLE.getItem(2));
        }

    }

    public static void armorPlayer(Player p) {
        p.getInventory().setHelmet(ItemEnum.KIT_HELMET.getKitArmor());
        p.getInventory().setChestplate(ItemEnum.KIT_CHESTPLATE.getKitArmor());
        p.getInventory().setLeggings(ItemEnum.KIT_LEGGINGS.getKitArmor());
        p.getInventory().setBoots(ItemEnum.KIT_BOOTS.getKitArmor());
    }

    public static void levelUp(UUID ID) {
        Player p = Bukkit.getPlayer(ID);
        PlayerStatEntity playerStatEntity = playerStatService.getOrDefault(ID);
        float value = playerStatEntity.getXp();
        if (p == null) return;
        if (value >= LooseData.getExpLeft(ID)) {
            final BoosterBonus coinsBonus = ActiveBoosterManager.getInstance()
                    .calculateBonus(100, List.of(BoosterTypes.COINS, BoosterTypes.MEGA));
            final int coins = (int) coinsBonus.result();

            int num = (int) value - LooseData.getExpLeft(ID);
            playerStatEntity.setXp(num);
            playerStatEntity.setLevel(playerStatEntity.getLevel() + 1);
            playerStatEntity.setCoins(playerStatEntity.getCoins() + coins);
            ScoreboardHelper.updateSuffix(p, ScoreboardEnum.LEVEL, playerStatEntity.getLevel() + "");
            playerStatService.save(playerStatEntity);

            p.setLevel(playerStatEntity.getLevel());
            p.setExp((float) num / LooseData.getExpLeft(ID));
            ChatHelper.announce(p, CC.AQUA + "" + CC.BOLD + "  LEVEL UP " + CC.DARK_AQUA + "Combat Lvl. " + CC.AQUA + playerStatEntity.getLevel(), Sound.ENTITY_PLAYER_LEVELUP);
            p.sendMessage(CC.DARK_GRAY + "  ▶ " + CC.GOLD + coins + " Coins " + coinsBonus.prettyPercentMultiplier());
            p.sendMessage(CC.DARK_GRAY + "  ▶ " + CC.DARK_AQUA + "1 Shard");
            p.sendMessage("");

        } else {
            p.setExp(value / LooseData.getExpLeft(ID));
        }
    }

    public static void addHealth(Player p, int amount) {
        if ((p.getHealth() + amount) >= PlayerHelper.getMaxHealth(p)) {
            healPlr(p);
            p.setHealth(PlayerHelper.getMaxHealth(p));
        } else {
            p.setHealth(p.getHealth() + amount);
        }
    }
}

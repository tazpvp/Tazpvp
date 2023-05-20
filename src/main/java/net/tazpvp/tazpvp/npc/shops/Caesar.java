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

package net.tazpvp.tazpvp.npc.shops;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.BlockFunctions;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import net.tazpvp.tazpvp.utils.objects.Ore;
import net.tazpvp.tazpvp.utils.objects.Pickaxe;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Caesar extends NPC {

    private static final List<Player> doubleClick = new ArrayList<>();

    public Caesar() {
        super(CC.GOLD + "Caesar", new Location(
                Bukkit.getWorld("arena"), -3, 90, 132, -155.5F, 0),
                Villager.Profession.WEAPONSMITH,
                Villager.Type.JUNGLE,
                Sound.ITEM_GOAT_HORN_SOUND_0);
    }

    @Override
    public void interact(@Nonnull PlayerInteractAtEntityEvent e, @Nonnull Player p) {

        if (BlockFunctions.getPickaxe(p) != null) {
            ItemStack tool = BlockFunctions.getPickaxe(p);
            int shardCount = PlayerFunctions.countShards(p);

            if (tool.getType().equals(Material.GOLDEN_PICKAXE)) {
                p.sendMessage("You already have the best upgrade.");
                return;
            }

            for (Pickaxe pickaxe : BlockFunctions.pickaxes) {
                if (pickaxe.getMat().equals(tool.getType())) {
                    int cost = pickaxe.getCost();
                    if (shardCount >= cost) {
                        if (doubleClick.contains(p)) {

                            tool.setType(pickaxe.getUpgrade());

                            PlayerFunctions.takeShards(p, cost);

                            p.closeInventory();
                            p.sendMessage("Thanks, here is your new pickaxe.");
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);

                            doubleClick.remove(p);

                            Tazpvp.getObservers().forEach(observer -> observer.talent(p));

                        } else {

                            p.sendMessage("Are you sure you want to upgrade your pickaxe for " + cost + " Shards?");

                            doubleClick.add(p);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    doubleClick.remove(p);
                                }
                            }.runTaskLater(Tazpvp.getInstance(), 20*5);
                        }
                    } else {
                        p.sendMessage("You do not have enough shards. You need " + (cost - shardCount) + " more shards.");
                    }
                }
            }
        } else if (BlockFunctions.getOreInHand(p) != null) {
            Ore ore = BlockFunctions.getOreFrom(BlockFunctions.getOreInHand(p).getType());
            int amount = 0;
            for (ItemStack i : p.getInventory()) {
                if (i != null && i.getType().equals(ore.getMat())) {
                    amount = amount + i.getAmount();
                    i.setAmount(0);
                }
            }
            PersistentData.add(p.getUniqueId(), DataTypes.COINS, (amount * ore.getCost()));
            p.sendMessage("Here you go, take $" + (amount * ore.getCost()));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        } else {
            p.sendMessage("Click me with your pickaxe and I will upgrade it.");
        }
    }
}

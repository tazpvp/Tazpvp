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

package net.tazpvp.tazpvp.npc.npcs;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.npc.NPC;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.BlockFunctions;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
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
                Bukkit.getWorld("arena"), 9.5, 100, -0.5),
                Villager.Profession.ARMORER,
                Villager.Type.JUNGLE,
                Sound.ITEM_GOAT_HORN_SOUND_0);
    }

    @Override
    public void interact(@Nonnull PlayerInteractAtEntityEvent e, @Nonnull Player p) {

        if (BlockFunctions.getPickaxe(p) != null) {

            ItemStack pickaxe = BlockFunctions.getPickaxe(p);
            int cost = BlockFunctions.pickaxes.get(pickaxe.getType());
            int shardCount = PlayerFunctions.countShards(p);

            if (doubleClick.contains(p)) {
                if (shardCount >= cost) {

                    PlayerFunctions.takeShards(p, cost);

                    if (pickaxe.getType() == Material.WOODEN_PICKAXE) { pickaxe.setType(Material.IRON_PICKAXE);}
                    if (pickaxe.getType() == Material.STONE_PICKAXE) { pickaxe.setType(Material.IRON_PICKAXE);}
                    if (pickaxe.getType() == Material.IRON_PICKAXE) { pickaxe.setType(Material.DIAMOND_PICKAXE);}
                    if (pickaxe.getType() == Material.DIAMOND_PICKAXE) { pickaxe.setType(Material.GOLDEN_PICKAXE);}

                    p.closeInventory();
                    p.sendMessage("Thanks, here is your new pickaxe.");

                    Tazpvp.getObservers().forEach(observer -> observer.talent(p));

                } else {
                    p.sendMessage("You do not have enough shards.");
                }
            } else {

                p.sendMessage("Are you sure you would like to upgrade your pickaxe for $" + cost + "?");
                p.sendMessage("Click me again to continue.");

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        doubleClick.add(p);
                    }
                }.runTaskLater(Tazpvp.getInstance(), 20);
            }
        } else {
            p.sendMessage("Click me with your pickaxe and I will upgrade it.");
        }
    }
}

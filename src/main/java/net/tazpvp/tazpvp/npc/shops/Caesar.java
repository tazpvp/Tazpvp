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
import net.tazpvp.tazpvp.npc.dialogue.Dialogues;
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
                Sound.ITEM_GOAT_HORN_SOUND_0,
                new Dialogues(
                        "Caesar",
                        List.of(
                                "I've made some enemies in my time. Good thing Ores can't speak..",
                                "Need to sell? HMU ig.",
                                "Need coins? Can't PVP? Mine you bum.",
                                "Upgrades are available for your crappy pickaxe..",
                                "Only in dreams, are you able to get a good deal for your work.",
                                "I took Maxim's toothbrush. It's not my fault my teeth were stinky.",
                                "What are you gonna do without upgrades? I stole my shards from Bub."
                        ),
                        List.of(
                                "Mine ore, sell ore, repeat.",
                                "Upgrade your pickaxe to mine more rare ores.",
                                "Beware of enemies! They may try to sneak up on you.", // Does dying with a upgraded pickaxe make you lose your upgrades? @rownox
                                "I dunno what a \"premium\" is, but buy it for the ability to sell all your ores at once!",
                                "Sell and upgrade, all I was trained to do.",
                                "Why fight when you can mine! When you're done mining, hand over your ores here.",
                                "Hey there miner! Welcome to the biggest time waste."
                        ),
                        List.of(
                                "Mine for dimes! At least you get paid for your hard work! Enjoy it.",
                                "Take a break from the fighting, mine your anger out.",
                                "Gotta get Guild upgrades some how! Might as well mine for the cash.",
                                "Upgrade your pickaxe, make your life so much easier.",
                                "Just a few more coins till you next upgrade! Get to work!",
                                "Tell me, are you ready for an adventure! I know the mines are ready for you!",
                                "Ever need to sell quicker? Buy premium to sell all your ores at once, become worry free!",
                                "PVP is overrated, just mine! Mining calms the soul and also rewards you with that sweet cash."
                        )
                ));
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

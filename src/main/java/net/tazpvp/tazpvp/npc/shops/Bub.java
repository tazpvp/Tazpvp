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

import net.tazpvp.tazpvp.npc.dialogue.Dialogues;
import net.tazpvp.tazpvp.items.StaticItems;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class Bub extends NPC {

    public Bub() {
        super(ChatFunctions.gradient("#0082fc", "Bub", true),
                new Location(Bukkit.getWorld("arena"), 1, 79, 77),
                Villager.Profession.CLERIC,
                Villager.Type.SNOW,
                Sound.ITEM_GOAT_HORN_SOUND_0,
                new Dialogues(
                        CC.AQUA + "[Bub] " + CC.DARK_AQUA,
                        List.of(
                                "Showed off my shards to Caesar, he stole a few and ran.",
                                "Killing is so much better when you're rewarded for it..",
                                "Don't come back.. I never wanted to see you in the first place.",
                                "You smell worse than Caesar, I bet your player heads are fake",
                                "Can't even kill a player? No Shards for you.",
                                "A baby decaying smells better than the player heads you collect.",
                                "Only minted heads allowed.. you scum."
                        ),
                        List.of(
                                "I like heads, I'll even pay you for them.",
                                "Got a rare head? Take a Shard and get paid.",
                                "That'll be one head.",
                                "Bring me more player heads!",
                                "Shards are my favorite because I like getting player heads for them",
                                "Kill a player, get lucky, and bring me their head.",
                                "Be careful not to loose the Shard",
                                "Watch where you're pointing that!"
                        ),
                        List.of(
                                "Unlock abilities never seen before with Shards!",
                                "Pay to win? More like play to win! Am I right or am I right?",
                                "Don't ask where I got the Shards, just give me player heads for them!",
                                "Use Shards to beat the person always killing you.",
                                "Got some player heads! I'll take em'!",
                                "You'll want these Shards more then just wearing a silly head.",
                                "Skinning people isn't illegal right? Right?",
                                "Got a player head? Does it smell good? Then i'll take it!"
                        )
                ));
    }

    @Override
    public void interact(@Nonnull PlayerInteractAtEntityEvent e, @Nonnull Player p) {
        sellItem(p);
    }

    public static void sellItem(Player p) {
        ItemStack item = p.getInventory().getItemInMainHand();
        int num = item.getAmount();
        int reward;

        if (item.getType() == Material.PLAYER_HEAD) {
            reward = num * 30;
        } else if (item.getType() == Material.AMETHYST_SHARD) {
            reward = num * 100;
        } else {
            p.sendMessage(CC.GREEN + "[Bub]" + CC.WHITE + " Right click me with player heads or shards and I'll give you money.");
            return;
        }

        p.sendMessage(CC.GREEN + "[Bub]" + CC.WHITE + " Pleasure doing business." + CC.GOLD + " + $" + reward);
        item.setAmount(0);
        PersistentData.add(p, DataTypes.COINS, reward);
        p.playSound(p.getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 1);
    }

}

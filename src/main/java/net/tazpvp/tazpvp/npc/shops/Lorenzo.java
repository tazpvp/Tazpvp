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

import net.tazpvp.tazpvp.guis.Menu;
import net.tazpvp.tazpvp.npc.dialogue.Dialogues;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import javax.annotation.Nonnull;
import java.util.List;

public class Lorenzo extends NPC {

    public Lorenzo() {
        super(CC.GOLD + "Lorenzo", new Location(Bukkit.getWorld("arena"), 12.5, 99, 20.5, 135, 0),
                Villager.Profession.FLETCHER,
                Villager.Type.TAIGA,
                Sound.ITEM_GOAT_HORN_SOUND_0,
                new Dialogues(
                        "Lorenzo",
                        List.of(
                                "If you need an advantage, buy it here I guess..",
                                "Why do I reward you for playing the game?",
                                "Guilds are cool, if you have friends..",
                                "Who is Caesar and why does he mine so much? Can't he sell talents and be normal.",
                                "Got spare cash? Use it on talents if you want..",
                                "What do you want?"
                        ),
                        List.of(
                                "Track your progress here.",
                                "Need something?",
                                "Cosmetics make you pretty.",
                                "Progressing feeling slow? Claim some achievements.",
                                "Guilds are the best way to stand out!",
                                "Create a guild and dominate the leaderboard.",
                                "Achieve anything special? Claim the reward you worked hard for!"
                        ),
                        List.of(
                                "Got a Guild? Check it out here!",
                                "Want revenge on the person who killed you? Check out the Revenge talent!",
                                "Looks like you've been working hard! Come get a reward.",
                                "Guilds make you way cooler! Make sure to bring friends.",
                                "Check out Maxim for some temporary upgrade that will scare your foes!",
                                "Challenge the leaderboard, create a guild for unique competition!",
                                "Who doesn't like a versatile fighter? Buy some talents!"
                        )
                ));
    }

    @Override
    public void interact(@Nonnull PlayerInteractAtEntityEvent e, @Nonnull Player p) {
        new Menu(p);
    }
}

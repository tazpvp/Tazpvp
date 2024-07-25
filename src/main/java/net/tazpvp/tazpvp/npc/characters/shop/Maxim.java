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

package net.tazpvp.tazpvp.npc.characters.shop;

import net.tazpvp.tazpvp.npc.characters.shop.gui.Shop;
import net.tazpvp.tazpvp.npc.dialogue.Dialogues;
import net.tazpvp.tazpvp.npc.characters.NPC;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import javax.annotation.Nonnull;
import java.util.List;

public class Maxim extends NPC {

    public Maxim() {
        super(ChatFunctions.gradient("#fc6400", "Maxim", true), new Location(Bukkit.getWorld("arena"), -11.5, 99, 20.5, -135, 0),
                Villager.Profession.FARMER,
                Villager.Type.SAVANNA,
                Sound.ITEM_GOAT_HORN_SOUND_0,
                new Dialogues(
                        CC.RED + "[Maxim] " + CC.WHITE,
                        List.of(
                                "I sell stuff I guess..",
                                "erhm.",
                                "Buy items if you want..",
                                "Business is.. well..",
                                "Who is Caesar? Why is my wife with him?",
                                "Does anything even matter?",
                                "Novelty items.. or soon to be.",
                                "I blame Ntdi for this life.."
                        ),
                        List.of(
                                "Need something?",
                                "New sale, 0% off the entire store.",
                                "Quality items, fair prices.",
                                "No pain, no shame.",
                                "Items if you want.",
                                "Get your power-ups and items here",
                                "Buy Buy Buy!",
                                "New items, fresh stock"
                        ),
                        List.of(
                                "Anything you need, I got it!",
                                "Hello traveler! I'm here if you need anything.",
                                "Get the upper hand with better items!",
                                "Powerful items, useful power-ups, the options are endless!",
                                "I love my shop, selling items is the best!",
                                "You want it? I got it.",
                                "Howdy! Need some super cool blocks?",
                                "How about some food? Fighters get hungry too!"
                        )
                ));
    }

    @Override
    public void interact(@Nonnull PlayerInteractAtEntityEvent e, @Nonnull Player p) {
        new Shop(p);
    }
}

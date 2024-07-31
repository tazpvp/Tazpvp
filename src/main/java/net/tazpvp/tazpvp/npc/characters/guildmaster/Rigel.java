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

package net.tazpvp.tazpvp.npc.characters.guildmaster;

import net.tazpvp.tazpvp.data.services.GuildService;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.npc.characters.NPC;
import net.tazpvp.tazpvp.npc.characters.guildmaster.gui.GuildMenu;
import net.tazpvp.tazpvp.npc.dialogue.Dialogues;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import javax.annotation.Nonnull;
import java.util.List;

public class Rigel extends NPC {

    private final GuildService guildService;
    private final PlayerStatService playerStatService;

    public Rigel(GuildService guildService, PlayerStatService playerStatService) {
        super(ChatHelper.gradient("#068fff", "Lorenzo", true), new Location(Bukkit.getWorld("arena"), 5, 99, 10, 135, 0),
                Villager.Profession.FLETCHER,
                Villager.Type.TAIGA,
                Sound.ITEM_GOAT_HORN_SOUND_0,
                new Dialogues(
                        CC.DARK_AQUA + "[Lorenzo] " + CC.AQUA,
                        List.of(
                                "Guilds are cool, if you have friends..",
                                "What do you want?"
                        ),
                        List.of(
                                "Need something?",
                                "Guilds are the best way to stand out!",
                                "Create a guild and dominate the leaderboard."
                        ),
                        List.of(
                                "Got a Guild? Check it out here!",
                                "Guilds make you way cooler! Make sure to bring friends.",
                                "Check out Maxim for some temporary upgrade that will scare your foes!",
                                "Challenge the leaderboard, create a guild for unique competition!"
                        )
                ));
        this.guildService = guildService;
        this.playerStatService = playerStatService;
    }

    @Override
    public void interact(@Nonnull PlayerInteractAtEntityEvent e, @Nonnull Player p) {
        new GuildMenu(p, guildService, playerStatService);
    }
}

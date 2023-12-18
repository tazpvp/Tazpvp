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
 *
 */

package net.tazpvp.tazpvp.utils.discord.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.tazpvp.tazpvp.utils.discord.bot.commands.LeaderboardCommand;

@RequiredArgsConstructor
public class BotThread extends Thread {
    private final String token;
    @Getter
    private Guild guild;

    @SneakyThrows
    @Override
    public void run() {

        JDA jda = JDABuilder.createDefault(this.token)
                .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.SCHEDULED_EVENTS, CacheFlag.MEMBER_OVERRIDES, CacheFlag.FORUM_TAGS, CacheFlag.ACTIVITY, CacheFlag.ROLE_TAGS, CacheFlag.CLIENT_STATUS)
                .setEnabledIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new LeaderboardCommand())
                .build();

        // optionally block until JDA is ready
        jda.awaitReady();

        this.guild = jda.getGuildById(535281648980459550L);

        getGuild().updateCommands().addCommands(
                Commands.slash("leaderboard", "View a leaderboard")
                        .addOptions(
                                new OptionData(OptionType.STRING, "type", "The type of leaderboard you want to view")
                                        .addChoice("Kills", "KILLS")
                                        .addChoice("Deaths", "DEATHS")
                                        .addChoice("Coins", "COINS")
                                        .addChoice("Levels", "LEVELS")
                                        .setRequired(true)
                        )
        ).queue();
    }
}

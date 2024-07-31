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

package net.tazpvp.tazpvp.utils.discord.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.helpers.ChatFunctions;
import net.tazpvp.tazpvp.utils.leaderboard.LeaderboardEnum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandsAndListeners extends ListenerAdapter {
    private final long channelId;
    private final long suggestionChannelId;
    private final Map<Long, Long> cooldownIdMap;

    public CommandsAndListeners(long channelId, long suggestionChannelId) {
        this.channelId = channelId;
        this.suggestionChannelId = suggestionChannelId;
        this.cooldownIdMap = new HashMap<>();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        final MessageChannelUnion messageChannelUnion = event.getChannel();

        if (messageChannelUnion.getIdLong() != channelId) {
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        final String prefix = ChatFunctions.gradient("#db3bff", "DISCORD", true);
        final String name = ChatColor.GRAY + event.getAuthor().getEffectiveName();
        final String message = ChatColor.WHITE + event.getMessage().getContentStripped();

        final String completion = String.format("%s %s %s", prefix, name, message);

        Bukkit.broadcastMessage(completion);
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("leaderboard")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            StringBuilder stringBuilder = new StringBuilder();

            if (event.getOption("type") == null) {
                event.reply("No type specified..").setEphemeral(true).queue();
                return;
            }

            String typeString = event.getOption("type").getAsString();

            LeaderboardEnum leaderboardEnum = LeaderboardEnum.valueOf(typeString);

            int count = 0;

            List<PlayerStatEntity> playerStatEntityList = Tazpvp.getInstance().getPlayerStatService().getTop10Most(leaderboardEnum.getColumnName());

            for (PlayerStatEntity playerStatEntity : playerStatEntityList) {
                if (count == 1) {
                    embedBuilder.setThumbnail("https://crafatar.com/renders/head/" + playerStatEntity.getUuid());
                }
                stringBuilder.append(count + ". **" + Bukkit.getOfflinePlayer(playerStatEntity.getUuid()).getName() + "** - " + leaderboardEnum.getStatEntityIntegerFunction().apply(playerStatEntity)).append("\n");
                count++;
            }

            embedBuilder.setTitle(typeString + " LEADERBOARD");
            embedBuilder.setDescription(stringBuilder.toString());

            event.replyEmbeds(embedBuilder.build()).queue();
        } else if (event.getName().equals("suggest")) {
            if (event.getOption("suggestion") == null) {
                event.reply("No suggestion found...").setEphemeral(true).queue();
                return;
            }

            final long userId = event.getUser().getIdLong();

            if (cooldownIdMap.containsKey(userId) ) {
                if (cooldownIdMap.get(userId) < System.currentTimeMillis()) {
                    cooldownIdMap.remove(userId);
                } else {
                    event.reply("You are currently on cooldown. Please wait **6 Hours** before your next suggestion").setEphemeral(true).queue();
                    return;
                }
            } else {
                cooldownIdMap.put(userId, System.currentTimeMillis() + 6 * 60 * 60 * 1000);
            }

            final String suggestion = event.getOption("suggestion").getAsString();
            final String senderUsername = event.getUser().getEffectiveName();

            final EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(senderUsername + "'s Suggestion");
            embedBuilder.setColor(Color.pink);
            embedBuilder.setDescription(suggestion);

            event.reply("Suggestion suggested!").setEphemeral(true).queue();

            event.getGuild().getTextChannelById(suggestionChannelId)
                    .sendMessageEmbeds(embedBuilder.build()).queue(message -> {
                        message.addReaction(Emoji.fromUnicode("U+1F44D")).queue();
                        message.addReaction(Emoji.fromUnicode("U+1F44E")).queue();
                    });
        }
    }
}

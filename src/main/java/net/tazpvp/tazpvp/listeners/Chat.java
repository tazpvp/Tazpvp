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

package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.Profanity;
import net.tazpvp.tazpvp.utils.TimeUtil;
import net.tazpvp.tazpvp.utils.data.*;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        PlayerWrapper pw = PlayerWrapper.getPlayer(p);

        if (PunishmentData.isMuted(uuid)) {
            final String howLongAgo = TimeUtil.howLongAgo(PunishmentData.getTimeRemaining(uuid));

            System.out.println(PunishmentData.getTimeRemaining(uuid));
            System.out.println(howLongAgo);

            p.sendMessage("You are currently muted for " + howLongAgo);
            e.setCancelled(true);
            return;
        }

        if (pw.getRank() == Rank.DEFAULT && pw.getLastMessageSent().equalsIgnoreCase(e.getMessage())) {
            e.setCancelled(true);
            p.sendMessage(CC.RED + "Please do not spam our chats.");
            return;
        }

        String message = e.getMessage();
        if (Profanity.sayNoNo(p, message)) {
            e.setCancelled(true);
            return;
        }

        pw.setLastMessageSent(message);

        Rank rank = PlayerRankData.getRank(uuid);

        String format = "&GRAY[{LEVEL}&GRAY] {PREFIX}%s{SUFFIX} &GRAY&M%s";
        format = format
                .replace("&GRAY", CC.GRAY.toString())
                .replace("&GOLD", CC.YELLOW.toString())
                .replace("&M",
                        (rank != Rank.DEFAULT
                                ? CC.WHITE.toString()
                                : CC.GRAY.toString())
                )
                .replace("{LEVEL}", String.valueOf(PersistentData.getInt(uuid, DataTypes.LEVEL)))
                .replace("{PREFIX}",
                        (rank.getPrefix() == null
                                ? ""
                                : rank.getPrefix() + " ")
                )
                .replace("{SUFFIX}", pw.getGuildTag().toUpperCase());


        for (Player h : Bukkit.getOnlinePlayers()) {
            Pattern pattern = Pattern.compile(h.getName(), Pattern.CASE_INSENSITIVE);
            Matcher match = pattern.matcher(message);
            if (match.find()) {
                h.playSound(h.getLocation(), Sound.BLOCK_BELL_USE, 1, 1);
                message.replace(h.getName(), CC.YELLOW + h.getName());
            }
        }

        LooseData.setChatCount(p.getUniqueId(), LooseData.getChatCount(p.getUniqueId()) + 1);

        Tazpvp.getObservers().forEach(observer -> observer.chat(p, e.getMessage()));

        e.setMessage(message);
        e.setFormat(format);
    }
}

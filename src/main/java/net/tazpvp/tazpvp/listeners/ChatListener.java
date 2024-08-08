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

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.LooseData;
import net.tazpvp.tazpvp.data.implementations.PunishmentServiceImpl;
import net.tazpvp.tazpvp.data.services.PunishmentService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.StatEnum;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.helpers.PunishmentHelper;
import net.tazpvp.tazpvp.utils.Profanity;
import net.tazpvp.tazpvp.utils.TimeUtil;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener implements Listener {

    List<String> swearWords = List.of(
            "fuck", "shit", "damn", "ass", "bitch", "bastard", "bollocks", "cunt", "cock",
            "dick", "piss", "arsehole", "wanker", "slut", "whore", "twat", "nigger",
            "faggot", "dyke", "kike", "spic", "chink", "retard", "moron", "idiot", "stupid",
            "gook", "slope", "coon", "raghead", "sand nigger", "wetback", "beaner", "jap",
            "squaw", "injun", "half-breed", "mulatto", "mudblood", "yellow", "cracker",
            "honky", "whitey", "redneck", "hillbilly", "nigga"
    );

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        PlayerWrapper pw = PlayerWrapper.getPlayer(p);

        String[] messageWords = e.getMessage().split(" ");
        List<String> messageWordsList = new ArrayList<>(Arrays.asList(messageWords));

        for (String word : swearWords)  {
            if (messageWordsList.contains(word.toLowerCase())) {
                e.setCancelled(true);
                p.sendMessage(CC.RED + "Please refrain from breaking the chat rules.");
                return;
            }
        }

        final PunishmentService punishmentService = new PunishmentServiceImpl();
        TextComponent component = new TextComponent(CC.GRAY + "Join the discord to appeal [Click here]");
        component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/56rdkbSqa8"));

        if (punishmentService.getPunishment(uuid) == PunishmentService.PunishmentType.MUTED) {

            final long timestamp = punishmentService.getTimeRemaining(uuid);
            final String howLongAgo = TimeUtil.howLongAgo(timestamp);

            if (timestamp < 0L) {
                PunishmentHelper.unmute(uuid);
            } else {
                p.sendMessage(CC.RED + "You are currently muted for " + howLongAgo);
                p.spigot().sendMessage(component);
                e.setCancelled(true);

                return;
            }
        }

        if (punishmentService.getPunishment(uuid) == PunishmentService.PunishmentType.BANNED) {
            if (punishmentService.getTimeRemaining(p.getUniqueId()) > 0) {
                p.sendMessage(CC.RED + "You cannot chat while banned.");
                p.spigot().sendMessage(component);
                e.setCancelled(true);
                return;
            }
        }

        if (pw.getRank().getName().equals("default") && pw.getLastMessageSent().equalsIgnoreCase(e.getMessage())) {
            e.setCancelled(true);
            p.sendMessage(CC.RED + "Please do not spam our chats.");
            return;
        }

        final String message = e.getMessage();
        if (Profanity.sayNoNo(p, message)) {
            e.setCancelled(true);
            return;
        }

        pw.setLastMessageSent(message);

        String format = "&GOLD{RANK} &GRAY[{LEVEL}&GRAY] {PREFIX} %s{SUFFIX} &GRAY&M%s";

        String rank = ChatHelper.getRankingPrefix(p);
        String level = StatEnum.LEVEL.getInt(uuid) + "";

        if (pw.getRankPrefix() == null) {
            String tempFormat = "&GOLD{RANK} &GRAY{RANK} [{LEVEL}&GRAY] %s{SUFFIX} &GRAY&M%s";
            format = tempFormat
                    .replace("&GRAY", CC.GRAY.toString())
                    .replace("&GOLD", CC.YELLOW.toString())
                    .replace("&M", CC.WHITE.toString())
                    .replace("{RANK}", rank)
                    .replace("{LEVEL}", level)
                    .replace("{SUFFIX}", pw.getGuildTag().toUpperCase());
        } else {
            format = format
                    .replace("&GRAY", CC.GRAY.toString())
                    .replace("&GOLD", CC.YELLOW.toString())
                    .replace("&M", CC.WHITE.toString())
                    .replace("{RANK}", rank)
                    .replace("{LEVEL}", level)
                    .replace("{PREFIX}", CC.trans(pw.getRankPrefix()))
                    .replace("{SUFFIX}", pw.getGuildTag().toUpperCase());
        }

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

        if (pw.isStaffChatActive()) {
            e.setCancelled(true);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("tazpvp.staffchat")) {
                    player.sendMessage(CC.LIGHT_PURPLE.toString() + CC.BOLD + "[Staff] " + CC.getLastColors(pw.getRankPrefix()) + pw.getRankPrefix() + " " + p.getName() + CC.WHITE + " " + e.getMessage());
                }
            }
        } else {
            Tazpvp.getBotThread().receiveMinecraftChat(
                    (pw.getRankPrefix() != null ? CC.stripColor(pw.getRankPrefix()) + " ": "")+ p.getName(),
                    e.getMessage());
        }
    }
}

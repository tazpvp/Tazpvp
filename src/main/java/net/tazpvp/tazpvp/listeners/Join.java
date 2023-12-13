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
import net.tazpvp.tazpvp.utils.PlayerNameTag;
import net.tazpvp.tazpvp.utils.PlaytimeUtil;
import net.tazpvp.tazpvp.utils.data.*;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.enums.ColorCodes;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import net.tazpvp.tazpvp.utils.functions.ScoreboardFunctions;
import net.tazpvp.tazpvp.utils.objects.CombatTag;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import world.ntdi.nrcore.NRCore;

public class Join implements Listener {

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        PersistentData.initPlayer(p);
        ScoreboardFunctions.initScoreboard(p);
        PlayerWrapper.addPlayer(p);

        new PlayerNameTag().initializePlayerNameTag(p);

        final PunishmentService punishmentService = new PunishmentServiceImpl();
        final PunishmentService.PunishmentType punishmentType = punishmentService.getPunishment(p.getUniqueId());

        TextComponent component = new TextComponent(CC.GREEN + "Join the discord to appeal [Click here]");
        component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/56rdkbSqa8"));

        if (punishmentType == PunishmentService.PunishmentType.PERM_BAN) {
            p.setGameMode(GameMode.SPECTATOR);
            ChatFunctions.announce(p, CC.RED + "You've been banned.", Sound.BLOCK_ANVIL_LAND);

            p.spigot().sendMessage(component);
            p.sendMessage("");
        } else if (punishmentType == PunishmentService.PunishmentType.BANNED) {
            if (punishmentService.getTimeRemaining(p.getUniqueId()) > 0) {
                p.setGameMode(GameMode.SPECTATOR);
                ChatFunctions.announce(p, CC.RED + "You've been banned.", Sound.BLOCK_ANVIL_LAND);

                p.spigot().sendMessage(component);
                p.sendMessage("");
            }
        }

        PlaytimeUtil.playerJoined(p);
        PlayerFunctions.resetHealth(p);
        PlayerFunctions.feedPlr(p);

        if (!p.hasPlayedBefore()) {
            PlayerFunctions.kitPlayer(p);
        }

        p.teleport(NRCore.config.spawn);

        CombatTag.tags.put(p.getUniqueId(), new CombatTag(p.getUniqueId()));

        p.setLevel(PersistentData.getInt(p.getUniqueId(), DataTypes.LEVEL));
        if (PersistentData.getFloat(p.getUniqueId(), DataTypes.XP) >= LooseData.getExpLeft(p.getUniqueId())) {
            float num = PersistentData.getFloat(p.getUniqueId(), DataTypes.XP) - LooseData.getExpLeft(p.getUniqueId());
            PlayerFunctions.levelUp(p.getUniqueId(), num);
        } else if (PersistentData.getFloat(p.getUniqueId(), DataTypes.XP) < 0) {
            PersistentData.set(p.getUniqueId(), DataTypes.XP, 0);
        } else {
            p.setExp(PersistentData.getFloat(p.getUniqueId(), DataTypes.XP) / LooseData.getExpLeft(p.getUniqueId()));
        }


//        int ranking = 1; //TODO: Use database and rankdata
//        String prefix = Tazpvp.getChat().getPlayerPrefix(p);
//        String suffix = Tazpvp.getChat().getPlayerSuffix(p);
//        ChatColor color = ChatColor.GRAY; //TODO: Use database and rankdata
//
//        new PlayerNameTag().initializePlayerNameTag(e.getPlayer(), ranking, prefix, suffix, color);

        p.setPlayerListHeaderFooter(
                CC.DARK_AQUA + "                                      " + "\n                 " +
                        ChatFunctions.gradient(ColorCodes.SERVER.toString(), "TAZPVP.NET", true) + "               " + "\n",
                "\n" +
                        ChatFunctions.gradient(ColorCodes.DISCORD.toString(), "✉ ᴊᴏɪɴ ᴜꜱ /ᴅɪꜱᴄᴏʀᴅ", false) + "\n" +
                        ChatFunctions.gradient(ColorCodes.STORE.toString(), "✘ ꜱᴜʙꜱᴄʀɪʙᴇ /ꜱᴛᴏʀᴇ", false) + "\n");


        final String name = p.getName();
        final String plus = CC.GREEN + "[" + CC.GOLD + "+" + CC.GREEN + "]";
        final String message = plus + " " + name;

        e.setJoinMessage(message);
    }
}

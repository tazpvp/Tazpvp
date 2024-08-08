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

package net.tazpvp.tazpvp.commands.moderation.ban;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.implementations.PunishmentServiceImpl;
import net.tazpvp.tazpvp.data.services.PunishmentService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.PunishmentHelper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.ChatUtils;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class BanCommand extends NRCommand {

    public BanCommand() {
        super(new Label("ban", "tazpvp.ban"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (args.length <= 1) {
            sendIncorrectUsage(sender, "/ban <user> <time> <reason>");
            return true;
        }

        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (target.isOnline()) {
            Player target2 = target.getPlayer();
            if (target2 != null) {
                if (target2.hasPermission("tazpvp.ban")) {
                    sender.sendMessage(CC.RED + "You cannot ban this person.");
                    return true;
                }
            }
        }

        final PunishmentService punishmentService = new PunishmentServiceImpl();
        final PunishmentService.PunishmentType punishmentType = punishmentService.getPunishment(target.getUniqueId());

        if (punishmentType == PunishmentService.PunishmentType.BANNED) {
            if (punishmentService.getTimeRemaining(target.getUniqueId()) > 0) {
                sender.sendMessage(CC.RED + "This player is already banned.");
                return true;
            }
        }


        if (args.length < 3) {
            PunishmentHelper.ban(target, args[1], sender);
        } else {
            PunishmentHelper.ban(target, args[1], sender, ChatUtils.builder(args, 2));
        }

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Completer.onlinePlayers(args[0]);
        } else if (args.length == 2) {
            return List.of("30m", "12h", "128d");
        }
        return List.of("<reason>");
    }
}

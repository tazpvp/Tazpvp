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

package net.tazpvp.tazpvp.commands.admin.npc;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.game.npcs.achievements.Achievements;
import net.tazpvp.tazpvp.game.npcs.guilds.Guilds;
import net.tazpvp.tazpvp.game.npcs.shop.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class NpcCommand extends NRCommand {

    public NpcCommand(Tazpvp tazpvp) {
        super(new Label("npc", "tazpvp.npc"));
        setNativeExecutor((sender, args) -> {

            if (!(sender instanceof Player)) {
                sendNoPermission(sender);
                return true;
            }

            if (!sender.hasPermission(getLabel().getPermission())) {
                sendNoPermission(sender);
                return true;
            }

            if (args[0].equalsIgnoreCase("maxim")) {
                new Shop();
            } else if (args[0].equalsIgnoreCase("lorenzo")) {
                new Achievements();
            } else if (args[0].equalsIgnoreCase("caesar")) {
            } else if (args[0].equalsIgnoreCase("rigel")) {
                new Guilds(tazpvp.getGuildService());
            }

            return true;
        });
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return List.of("maxim", "lorenzo", "caesar", "rigel");
        }
        return List.of();
    }
}

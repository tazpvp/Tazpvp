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

package net.tazpvp.tazpvp.commands.network;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class HelpCommand extends NRCommand {
    public HelpCommand() {
        super(new Label("help", null));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            return true;
        }

        String title = CC.YELLOW + "" + CC.BOLD;
        String text = CC.GRAY + "";
        String highlight = CC.YELLOW + "";

        if (args.length < 1) {
            p.sendMessage(title + "NEED HELP?");
            p.sendMessage(text + "Type the command again with a category like so:");
            p.sendMessage(text + "/help <type>");
            p.sendMessage(title + "Categories");
            p.sendMessage(highlight + "- " + text + "Money");
            p.sendMessage(highlight + "- " + text + "Enchants");
            p.sendMessage(highlight + "- " + text + "Talents");
            p.sendMessage(highlight + "- " + text + "Achievements");
            p.sendMessage("");
            return true;
        }

        String type = args[0];

        if (type.equalsIgnoreCase("money")) {
            p.sendMessage(title + "Economy");
            p.sendMessage(text + "Ways to get coins:");
            p.sendMessage(highlight + "- " + text + "Killing & Kill assisting");
            p.sendMessage(highlight + "- " + text + "Selling Shards to " + highlight + "Bub");
            p.sendMessage(highlight + "- " + text + "Mining and selling ores");
            p.sendMessage(highlight + "- " + text + "Opening crates");
            p.sendMessage("");
        } else if(type.equalsIgnoreCase("enchants")) {
            p.sendMessage(title + "Enchantments");
            p.sendMessage(text + "To enchant your items, simply click and drag enchantments onto an item.");
            p.sendMessage("");
        } else if(type.equalsIgnoreCase("talents")) {
            p.sendMessage(title + "Talents");
            p.sendMessage(text + "These perks will give you a slight advantage in PvP for a cost.");
            p.sendMessage("");
        } else if(type.equalsIgnoreCase("achievements")) {
            p.sendMessage(title + "Achievements");
            p.sendMessage(text + "A way to measure progress and get prizes.");
            p.sendMessage("");
        } else if(type.equalsIgnoreCase("commands")) {
            p.sendMessage(title + "Commands");
            p.sendMessage(highlight + "/spawn" + text + "- Teleport to the server spawn");
            p.sendMessage(highlight + "/report <player>" + text + "- Tell staff members about a cheater");
            p.sendMessage(highlight + "/" + text + "- ");
            p.sendMessage(highlight + "/" + text + "- ");
            p.sendMessage(highlight + "/" + text + "- ");
            p.sendMessage(highlight + "/" + text + "- ");
            p.sendMessage("");
        } else if(type.equalsIgnoreCase("rewards")) {
            p.sendMessage(title + "Rewards");
            p.sendMessage(text + "Ways to get free rewards:");
            p.sendMessage(highlight + "- " + text + "Boosting the discord server will give you premium for free");
            p.sendMessage(highlight + "- " + text + "The AFK pit will give you a key and money every 10 minutes");
            p.sendMessage(highlight + "- " + text + "Voting for the server using /vote will give you a key");
            p.sendMessage("");
        }

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return List.of("commands", "money", "enchants", "talents", "achievements", "rewards");
        }
        return List.of();
    }
}

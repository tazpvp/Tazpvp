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

package net.tazpvp.tazpvp.commands.admin;

import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

import java.util.ArrayList;
import java.util.List;

public class StatCommand extends CommandCore implements CommandFunction {
    public StatCommand() {
        super("stats", "stats", "stat");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length >= 4) {
            Player target = Bukkit.getPlayer(args[3]);
            stats(args, target);
        } else if (sender instanceof Player p) {
            stats(args, p);
        }
    }

    private void stats(String[] args, Player p) {
        if (args[0].equalsIgnoreCase("give")) {
            if (args[1].equalsIgnoreCase("coins")) {
                PersistentData.add(p.getUniqueId(), DataTypes.COINS, Integer.parseInt(args[2]));
            }
        } else if (args[0].equalsIgnoreCase("reset")) {

        }
    }

    @Override
    public List<String> tabCompletion(CommandSender commandSender, String[] strings) {
        if (strings.length == 0) {
            return List.of("give");
        } else if (strings.length == 1) {
            return List.of("coins");
        } else if (strings.length == 2) {
            return List.of("10", "100", "1000", "10000");
        } else if (strings.length == 3) {
            List<String> pList = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                String name = p.getName();
                pList.add(name);
            }
            return pList;
        }
        return List.of("");
    }
}

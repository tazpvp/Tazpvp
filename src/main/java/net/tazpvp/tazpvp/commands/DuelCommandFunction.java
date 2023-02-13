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

package net.tazpvp.tazpvp.commands;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.duels.Duel;
import net.tazpvp.tazpvp.duels.DuelUtils;
import net.tazpvp.tazpvp.duels.type.Classic;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javatuples.Pair;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

import java.util.ArrayList;
import java.util.List;

public class DuelCommandFunction extends CommandCore implements CommandFunction {

    String help = "Commands:\n" + "/duel <player> <type>\n" + "/duel accept";

    public DuelCommandFunction() {
        super("duel", null, "duel");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    putInDuel(args[1], p, target);
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("accept")) {
                    Pair<Boolean, Duel> duelPair = requested(p);

                    if (duelPair.getValue0()) {
                        Duel duel = duelPair.getValue1();

                        DuelUtils.ACTIVE_DUELS.add(duel);

                        duel.begin();
                        duel.getDUELERS().forEach(d -> {
                            Bukkit.getPlayer(d).sendMessage("Duel Commencing!");
                        });
                    } else {
                        p.sendMessage("No one sent you a duel request");
                    }
                } else {
                    p.sendMessage(help);
                }
            } else {
                p.sendMessage(help);
            }
        }
    }

    private Pair<Boolean, Duel> requested(Player p) {
        for (Duel duel : Tazpvp.duels.values()) {
            if (duel.getP2().equals(p.getUniqueId())) {
                return Pair.with(true, duel);
            }
        }
        return Pair.with(false, null);
    }

    private void putInDuel(String type, Player p, Player target) {
        if (type.equalsIgnoreCase("classic")) {
            Tazpvp.duels.put(p.getUniqueId(), new Classic(p.getUniqueId(), target.getUniqueId()));
        } else {
            p.sendMessage("Not a valid type");
            return;
        }
        target.sendMessage(p.getName() + " sent you a duel request.");
    }

    @Override
    public List<String> tabCompletion(CommandSender commandSender, String[] strings) {
        return List.of("");
    }
}

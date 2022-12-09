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
import net.tazpvp.tazpvp.events.EventUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.PlayerUtils;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

public class EventCommandFunction extends CommandCore implements CommandFunction {

    public EventCommandFunction() {
        super("event", null, "ev");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player p) {
            if (args[0].equalsIgnoreCase("create")) {
                if (!PlayerUtils.checkPerms(p, Tazpvp.prefix + "event.host")) return;
                for (String event : Tazpvp.events) {
                    if (args[1].equalsIgnoreCase(event)) {
                        if (Tazpvp.eventKey == null) Tazpvp.eventKey = args[1].toUpperCase();
                        return;
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("list")) {
                for (String event : Tazpvp.events) {
                    p.sendMessage(event);
                    return;
                }
            }
            else if (args[0].equalsIgnoreCase("begin")) {
                if (Tazpvp.eventKey != null) {
                    EventUtils.begin(Tazpvp.eventKey, Tazpvp.playerList);
                    return;
                }
            }
            else if (args[0].equalsIgnoreCase("join")) {
                if (Tazpvp.eventKey != null) {
                    Tazpvp.getObservers().forEach(observer -> observer.event(p));
                    Tazpvp.playerList.add(p.getUniqueId());
                    return;
                }
            }
        }
        return;
    }
}

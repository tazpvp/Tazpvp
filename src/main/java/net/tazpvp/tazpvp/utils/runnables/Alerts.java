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
 */

package net.tazpvp.tazpvp.utils.runnables;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import world.ntdi.nrcore.utils.ChatUtils;

public class Alerts {

    private static int num = 1;
    private static String prefix = "&8(&c!&8) &7";

    public static void alert() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    num = (num + 1) % 7;
                    switch(num){
                        case 0:
                            Bukkit.broadcastMessage(ChatUtils.chat(prefix + "Join our community by typing &3/discord"));
                            break;
                        case 1:
                            Bukkit.broadcastMessage(ChatUtils.chat(prefix + "Check out what you can do with premium! Type &3/premium"));
                            break;
                        case 2:
                            Bukkit.broadcastMessage(ChatUtils.chat(prefix + "Looking to apply for staff? Type &3/apply"));
                            break;
                        case 3:
                            Bukkit.broadcastMessage(ChatUtils.chat(prefix + "Want to support us? Get our advertisement using &3/ad"));
                            break;
                        case 4:
                            Bukkit.broadcastMessage(ChatUtils.chat(prefix + "Think someone is hacking? Report them using &3/report"));
                            break;
                        case 5:
                            Bukkit.broadcastMessage(ChatUtils.chat(prefix + "Hop in the AFK pit and claim rewards."));
                            break;
                        case 6:
                            Bukkit.broadcastMessage(ChatUtils.chat(prefix + "Died to a hacker? Get an inventory restore with &3/restore"));
                            break;
                    }
                }
            }
        }.runTaskTimer(Tazpvp.getInstance(), 20, 20*60*4);
    }
}

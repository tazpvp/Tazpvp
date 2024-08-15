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

package net.tazpvp.tazpvp.utils.passive;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.LinkedList;

public class Alerts {

    private static int num = 0;
    private static final String prefix = CC.DARK_GREEN + "(" + CC.GREEN + CC.BOLD + "TIP" + CC.DARK_GREEN + ") " + CC.GREEN;

    private static final LinkedList<String> texts = new LinkedList<>(Arrays.asList(
            "Chat with like-minded sigmas: " + CC.DARK_GREEN + "/discord" ,
            "Premium pass is pretty cool ngl " + CC.DARK_GREEN + "/premium",
            "Wanna apply for staff? " + CC.DARK_GREEN + "/apply",
            "I heard that if you advertise, you're cool " + CC.DARK_GREEN + "/ad",
            "Found a stinky hacker? " + CC.DARK_GREEN + "/report",
            "Is someone being annoying? use " + CC.DARK_GREEN + "/votemute",
            "Sit in the AFK pit to claim rewards over night.",
            "If you died to a cheater you can join our discord and get a restore.",
            "Party up with your friends to chat or join events: " + CC.DARK_GREEN + "/party"
    ));

    public static void initialize() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(prefix + texts.get(num));
                    p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
                }
                num++;
                if (num  >= texts.size()) num = 0;
            }
        }.runTaskTimer(Tazpvp.getInstance(), 20, 20*60*4);
    }
}

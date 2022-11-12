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

package net.tazpvp.tazpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class Death {

    final String prefix = CC.GRAY + "[" + CC.DARK_RED + "☠" + CC.GRAY + "] " + CC.DARK_GRAY;
    private final Player victim;
    private final Player killer;

    public Death(final Player victim, @Nullable final Player killer) {
        this.victim = victim;
        this.killer = killer;
    }

    public void killMessage(final Player p) {
        final String who = (p == killer) ? "You" : CC.GRAY + killer.getName();
        final String what = CC.DARK_GRAY + " killed " + CC.GRAY + victim.getName();
        String msg = prefix + who + what;

        p.sendMessage(msg);
    }

    public void deathMessage(final Player p) {
        final String who = (p == victim) ? "You" : CC.GRAY + victim.getName();
        String msg = prefix + who + " died.";

        p.sendMessage(msg);
    }

    public void MessageAll(String which) {
        for (Player op : Bukkit.getOnlinePlayers()) {
            if (which.equals("kill")) {
                killMessage(op);
            } else if (which.equals("death")) {
                deathMessage(op);
            }
        }
    }
}

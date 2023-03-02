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

package net.tazpvp.tazpvp.utils.functions;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.objects.CombatTag;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class CombatTagFunctions {

    public static void putInCombat(UUID victim, UUID attacker) {
        if (victim != attacker && victim != null) {
            if (getTag(victim) != null) {
                setTimer(getTag(victim), victim, 16);
                if (!getTag(victim).getAttackers().contains(attacker)) {
                    getTag(victim).getAttackers().add(attacker);
                }
                if (getTag(attacker) != null) {
                    setTimer(getTag(attacker), attacker, 16);
                } else {
                    putInCombat(attacker, victim);
                }
            } else {
                CombatTag tag = new CombatTag();

                Tazpvp.tags.put(victim, tag);
                tag.getAttackers().add(attacker);

                setTimer(tag, victim, 16);
                putInCombat(attacker, victim);
            }
        }
    }

    public static CombatTag getTag(UUID ID) {
        if (Tazpvp.tags.containsKey(ID)) {
            return Tazpvp.tags.get(ID);
        }
        return null;
    }

    public static void clearAttackers(CombatTag tag) {
        tag.getAttackers().clear();
    }

    public static void countDown(CombatTag tag, UUID id) {
        Player p = Bukkit.getPlayer(id);
        if (tag.getCountdown() > 0) {
            tag.setCountdown(tag.getCountdown() - 1);
            tag.getBar().setProgress((float) (tag.getCountdown() / 16));
        } else {
            clearAttackers(tag);
            tag.getBar().removePlayer(p);
            p.sendMessage("You are now out of combat.");
        }
    }

    public static void setTimer(CombatTag tag, UUID id, int time) {
        if (tag.getCountdown() <= 0) {
            tag.setCountdown(time);
            tag.setBar(Bukkit.createBossBar("Combat Tag: " + tag.getCountdown() + "s", BarColor.RED, BarStyle.SOLID));
            tag.getBar().setProgress(1);
            tag.getBar().addPlayer(Bukkit.getPlayer(id));
            new BukkitRunnable() {
                public void run() {
                    if (tag.getCountdown() > 0) {
                        countDown(tag, id);
                    } else {
                        countDown(tag, id);
                        cancel();
                    }
                }
            }.runTaskTimer(Tazpvp.getInstance(), 0, 20);
        } else {
            tag.setCountdown(time);
        }
    }
}

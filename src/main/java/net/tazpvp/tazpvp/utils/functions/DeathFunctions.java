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

package net.tazpvp.tazpvp.utils.functions;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.LooseData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.objects.Death;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.config.ConfigUtils;

import javax.annotation.Nullable;

public class DeathFunctions {

    static final String prefix = CC.GRAY + "[" + CC.DARK_RED + "☠" + CC.GRAY + "] " + CC.DARK_GRAY;

    /**
     * Runs for every death and runs all the checks for the victim and killer.
     * @param victim The person who died.
     * @param killer The person who killed.
     */
    public static void death(Player victim, @Nullable Entity killer) {
        if (killer != null) {
            if (killer instanceof Player pKiller) {
                Death death = new Death(victim, pKiller);

                if (pKiller == victim) {
                    MessageAll(false, victim, pKiller);
                } else {
                    PersistentData.add(pKiller.getUniqueId(), DataTypes.KILLS);
                    LooseData.addKs(pKiller.getUniqueId());

                    death.coffin();
                    death.rewards();
                    death.dropHead();

                    MessageAll(true, victim, pKiller);
                    addHealth(pKiller, 5);

                    pKiller.playSound(pKiller.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                }
            } else if (killer instanceof Mob mKiller) {
                MessageAll(false, victim, mKiller);
            }
        }
        PersistentData.add(victim, DataTypes.DEATHS);
        LooseData.resetKs(victim.getUniqueId());

        heal(victim);
        respawn(victim);
        Tazpvp.tags.get(victim.getUniqueId()).endCombat(null, false);
    }

    public static void respawn(Player p) {
        p.setGameMode(GameMode.SPECTATOR);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        p.sendTitle(CC.RED + "" + CC.BOLD + "YOU DIED", CC.GOLD + "Respawning...", 5, 50, 5);
        new BukkitRunnable() {
            public void run() {
                p.setGameMode(GameMode.SURVIVAL);
                p.teleport(ConfigUtils.spawn);
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*3);
    }

    public static void heal(Player p) {
        PlayerFunctions.healPlr(p);
        PlayerFunctions.feedPlr(p);

        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
    }

    public static void addHealth(Player p, int amount) {
        if ((p.getHealth() + 5) >= PlayerFunctions.getMaxHealth(p)) {
            PlayerFunctions.healPlr(p);
            p.setHealth(PlayerFunctions.getMaxHealth(p));
        } else {
            p.setHealth(p.getHealth() + amount);
        }
    }

    public static void killMessage(Player p, Player victim, Entity killer) {
        final String who = (p == killer) ? "You" : CC.GRAY + killer.getName();
        final String died = (p == victim) ? "you" : CC.GRAY + victim.getName();
        String msg = prefix + who + CC.DARK_GRAY + " killed " + died;

        p.sendMessage(msg);
    }

    public static void deathMessage(Player p, Player victim) {
        final String who = (p == victim) ? "You" : CC.GRAY + victim.getName();
        String msg = prefix + who + CC.DARK_GRAY + " died.";

        p.sendMessage(msg);
    }

    public static void MessageAll(boolean pKill, Player victim, @Nullable Entity killer) {
        for (Player op : Bukkit.getOnlinePlayers()) {
            if (pKill) {
                killMessage(op, victim, killer);
            } else {
                deathMessage(op, victim);
            }
        }
    }
}

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

import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.LooseData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.objects.CombatTag;
import net.tazpvp.tazpvp.utils.objects.Death;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.WeakHashMap;

public class DeathFunctions {
    public static WeakHashMap<UUID, CombatTag> tags = new WeakHashMap<>();

    public static void death(Player victim, @Nullable Entity killer) {

        Death death = new Death(victim, killer);
        UUID currentKiller = tags.get(victim.getUniqueId()).getAttackers().peekLast();

        if (currentKiller != null) {
            tags.get(victim.getUniqueId()).getAttackers().clear();
            death(victim, Bukkit.getPlayer(currentKiller));
            return;
        } else if (killer != null) {
            if (killer instanceof Player pKiller) {
                if (pKiller == victim) {
                    death.deathMessage(false);
                } else {
                    PersistentData.add(pKiller.getUniqueId(), DataTypes.KILLS);
                    LooseData.addKs(pKiller.getUniqueId());

                    if ((LooseData.getKs(pKiller.getUniqueId()) % 5) == 0) {
                        Bukkit.broadcastMessage(
                                CC.GOLD + killer.getName() + CC.YELLOW + " has a kill streak of " + CC.GOLD + LooseData.getKs(pKiller.getUniqueId()) + "\n" +
                                CC.GOLD + "Bounty: " + CC.YELLOW + "$" + (LooseData.getKs(pKiller.getUniqueId()) * 10)
                        );
                    }

                    death.coffin();
                    death.rewards();
                    death.dropHead();
                    death.storeInventory();

                    death.deathMessage(true);
                    PlayerFunctions.addHealth(victim, 10);

                    pKiller.playSound(pKiller.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                }
            } else if (killer instanceof Mob mKiller) {
                death.deathMessage(false);
            }
        }

        PersistentData.add(victim, DataTypes.DEATHS);
        LooseData.resetKs(victim.getUniqueId());

        death.heal();
        death.respawn();
        victim.getInventory().clear();
        PlayerFunctions.kitPlayer(victim);
        tags.get(victim.getUniqueId()).endCombat(null, false);
    }
}

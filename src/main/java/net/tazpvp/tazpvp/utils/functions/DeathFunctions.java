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
import net.tazpvp.tazpvp.duels.Duel;
import net.tazpvp.tazpvp.duels.DuelUtils;
import net.tazpvp.tazpvp.events.EventUtils;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.LooseData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.objects.Death;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.config.ConfigUtils;

import javax.annotation.Nullable;

public class DeathFunctions {


    /**
     * Runs for every death and runs all the checks for the victim and killer.
     * @param victim The person who died.
     * @param killer The person who killed.
     */

    public static void death(Player victim, @Nullable Player killer) {
        Death death = new Death(victim, killer);

        if (killer != null) {
            PersistentData.add(killer, DataTypes.KILLS);
            if (Bukkit.getOnlinePlayers().size() < 10) {
                if (killer == victim)
                    death.MessageAll("death");
                else
                    death.MessageAll("kill");
            } else {
                if (killer == victim)
                    death.deathMessage(victim);
                else
                    death.killMessage(killer);
            }
            LooseData.addKs(killer.getUniqueId());

            for (Duel duel : DuelUtils.ACTIVE_DUELS) {
                if (duel.getDUELERS().contains(killer.getUniqueId())) {
                    DuelUtils.end(victim, killer, duel.getDUELERS(), duel);
                }
            }

        }

        if (Tazpvp.playerList.contains(victim.getUniqueId())) {

            Tazpvp.playerList.remove(victim.getUniqueId());
            EventUtils.check();
        }

        PersistentData.add(victim, DataTypes.DEATHS);

        death.dropHead();
        death.rewards();
        death.coffin();
        death.heal();

        victim.teleport(ConfigUtils.spawn);
    }
}

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

import lombok.Getter;
import net.tazpvp.tazpvp.duels.Duel;
import net.tazpvp.tazpvp.guild.GuildUtils;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.KitData;
import net.tazpvp.tazpvp.utils.data.LooseData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.kit.SerializableInventory;
import net.tazpvp.tazpvp.utils.objects.Coffin;
import net.tazpvp.tazpvp.utils.objects.CombatTag;
import net.tazpvp.tazpvp.utils.objects.Death;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeathFunctions {
    @Getter
    private final static List<Coffin> coffins = new ArrayList<>();
    public static void death(UUID victim, UUID killer) {

        Death death = new Death(victim, killer);
        final UUID currentKiller = CombatTagFunctions.getLastAttacker(victim);
        PlayerWrapper wrapper = PlayerWrapper.getPlayer(victim);
        final Player pKiller = Bukkit.getPlayer(killer);
        final Player pVictim = Bukkit.getPlayer(victim);

        if (currentKiller != null) {
            CombatTag.tags.get(victim).getAttackers().clear();
            death(victim, currentKiller);
            return;
        }

        if (killer != null) {
            if (killer != victim) {
                if (wrapper.isDueling()) {
                    Duel duel = Duel.getDuel(victim);
                    duel.setWinner(killer);
                    duel.setLoser(victim);
                    duel.end();
                    return;
                }

                PersistentData.add(killer, DataTypes.KILLS);
                LooseData.addKs(killer);

                if ((LooseData.getKs(killer) % 5) == 0) {
                    Bukkit.broadcastMessage(
                            CC.GOLD + pKiller.getName() + CC.YELLOW + " has a kill streak of " +
                                    CC.GOLD + LooseData.getKs(killer) + "\n" +
                                    CC.GOLD + "Bounty: " + CC.YELLOW + "$" + (LooseData.getKs(killer) * 10)
                    );
                }

                if (GuildUtils.isInGuild(pKiller)) {
                    GuildUtils.getGuildPlayerIn(killer).addKills(1);
                }

                if (GuildUtils.isInGuild(pVictim)) {
                    GuildUtils.getGuildPlayerIn(victim).addDeaths(1);
                }

                death.playParticle();
                death.coffin();
                death.dropHead();
                death.storeInventory();
                PlayerFunctions.addHealth(pKiller, 10);
                pKiller.playSound(pKiller.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            }
            death.deathMessage();
        }

        PersistentData.add(victim, DataTypes.DEATHS);
        LooseData.resetKs(victim);

        combatRespawn(pVictim, death);
    }

    public static void death(UUID victim) {

        Death death = new Death(victim, null);
        PlayerWrapper wrapper = PlayerWrapper.getPlayer(victim);
        final Player pVictim = Bukkit.getPlayer(victim);

        if (wrapper.isDueling()) {
            Duel duel = Duel.getDuel(victim);
            duel.setWinner(Duel.getOtherDueler(victim));
            duel.setLoser(victim);
            duel.end();
            return;
        }

        death.deathMessage();

        PersistentData.add(victim, DataTypes.DEATHS);
        LooseData.resetKs(victim);

        combatRespawn(pVictim, death);
    }

    private static void combatRespawn(final Player pVictim, final Death death) {
        death.respawn();
        death.rewards();
        pVictim.getInventory().clear();

        final String kitSerial = KitData.getSerial(pVictim.getUniqueId());
        if (kitSerial == null || kitSerial.isEmpty()) {
            PlayerFunctions.kitPlayer(pVictim);
        } else {
            SerializableInventory serializableInventory = SerializableInventory.convertFromString(kitSerial);
            serializableInventory.addItems(pVictim.getInventory(), PlayerFunctions.getKitItems(pVictim));

            PlayerFunctions.armorPlayer(pVictim);
        }

        PlayerFunctions.resetHealth(pVictim);
        PlayerFunctions.feedPlr(pVictim);
        CombatTag.tags.get(pVictim.getUniqueId()).endCombat(null, false);
    }

    public static void acceptClick(PlayerInteractEvent e) {
        for (Coffin coffin : getCoffins()) {
            if (e.getClickedBlock() != null) {
                if (e.getClickedBlock().getLocation().distance(coffin.getLocation()) < 1D) {
                    coffin.doTheDo(e.getPlayer());
                }
            }
        }
    }

    public static void addCoffin(Coffin coffin) {
        coffins.add(coffin);
        System.out.println(coffins);
    }
}

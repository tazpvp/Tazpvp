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

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.LooseData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.objects.CombatTag;
import net.tazpvp.tazpvp.utils.objects.Death;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.config.ConfigUtils;

import javax.annotation.Nullable;
import java.util.UUID;

public class DeathFunctions {


    public static void death(Player victim, @Nullable Entity killer) {

        Death death = new Death(victim, killer);

        if (killer != null) {
            if (killer instanceof Player pKiller) {
                if (pKiller == victim) {
                    death.deathMessage(false);
                } else {
                    PersistentData.add(pKiller.getUniqueId(), DataTypes.KILLS);
                    LooseData.addKs(pKiller.getUniqueId());

                    death.coffin();
                    death.rewards();
                    death.dropHead();

                    death.deathMessage(true);
                    death.addHealth(10);

                    pKiller.playSound(pKiller.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                }
            } else if (killer instanceof Mob mKiller) {
                death.deathMessage(false);
            }
        } else {
            if (!Tazpvp.tags.get(victim.getUniqueId()).getAttackers().isEmpty()) {
                UUID currentKiller = Tazpvp.tags.get(victim.getUniqueId()).getAttackers().peek();
                death.setKiller(Bukkit.getPlayer(currentKiller));
                death.rewards();
            }
        }

        PersistentData.add(victim, DataTypes.DEATHS);
        LooseData.resetKs(victim.getUniqueId());

        death.heal();
        death.respawn();
        victim.getInventory().clear();
        PlayerFunctions.kitPlayer(victim);
        Tazpvp.tags.get(victim.getUniqueId()).endCombat(null, false);
    }
}

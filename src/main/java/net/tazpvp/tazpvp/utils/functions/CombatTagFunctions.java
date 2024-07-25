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

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.objects.CombatTag;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.UUID;

public class CombatTagFunctions {

    public static void putInCombat(@Nullable UUID victim, @Nullable UUID attacker) {
        if (victim != attacker) {
            if (victim == null) {
                getTag(attacker).setTimer(null);
            } else if (attacker == null) {
                getTag(victim).setTimer(null);
            } else {
                getTag(victim).setTimer(attacker);
                getTag(attacker).setTimer(victim);
            }
        }
    }

    public static CombatTag getTag(UUID ID) {
        if (!CombatTag.tags.containsKey(ID)) {
            CombatTag.tags.put(ID, new CombatTag(ID));
        }
        return CombatTag.tags.get(ID);
    }

    public static UUID getLastAttacker(UUID ID) {
        if (getTag(ID).getAttackers().size() < 1) return null;
        return getTag(ID).getAttackers().peekLast();
    }

    public static void initCombatTag() {
        new BukkitRunnable() {
            public void run() {
                for (UUID id : CombatTag.tags.keySet()) {
                    CombatTag.tags.get(id).countDown();
                }
            }
        }.runTaskTimer(Tazpvp.getInstance(), 0, 20);
    }

    public static boolean isInCombat(UUID ID) {
        if (getTag(ID) == null) return false;
        return !getTag(ID).getAttackers().isEmpty();
    }
}

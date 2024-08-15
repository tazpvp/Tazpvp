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

package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.enums.RegionEnum;
import net.tazpvp.tazpvp.game.items.UsableItem;
import net.tazpvp.tazpvp.helpers.CombatTagHelper;
import net.tazpvp.tazpvp.objects.DeathObject;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.UUID;

public class DamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player victim)) {
            if (event.getEntity() instanceof Villager) {
                event.setCancelled(true);
            }
            return;
        }

        final PlayerWrapper victimWrapper = PlayerWrapper.getPlayer(victim);

        if (RegionEnum.spawnRegion.contains(victim.getLocation())) {
            event.setCancelled(true);
            return;
        }

        double finalDamage = event.getFinalDamage();
        boolean isFallingDamage = (event.getCause() == EntityDamageEvent.DamageCause.FALL);

        if (!victimWrapper.isLaunching() && compare(event, isFallingDamage)) {
            return;
        }

        if (victimWrapper.isVanished()) {
            event.setCancelled(true);
            return;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            event.setCancelled(true);
            return;
        }

        if (event instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
            handleEntityDamageByEntity(victim, entityDamageByEntityEvent, finalDamage);
        } else {
            handleNonEntityDamage(victim, event, finalDamage);
        }
    }

    private boolean compare(EntityDamageEvent event, boolean condition) {
        if (condition) {
            event.setCancelled(true);
        }
        return condition;
    }

    private void handleEntityDamageByEntity(Player victim, EntityDamageByEntityEvent event, double finalDamage) {
        if (event.getDamager() instanceof Player killer) {
            final PlayerWrapper killerWrapper = PlayerWrapper.getPlayer(killer);

            if (killerWrapper.isVanished()) {
                event.setCancelled(true);
                return;
            }

            checkDeath(victim.getUniqueId(), killer.getUniqueId(), event, finalDamage);
            ItemStack weapon = killer.getInventory().getItemInMainHand();

            if (UsableItem.getCustomItem(weapon) != null) {
                UsableItem item = UsableItem.getCustomItem(weapon);
                if (item == null) return;
                item.onLeftClick(killer, weapon, victim);
            }

        } else if (event.getDamager() instanceof Arrow arrow) {
            if (arrow.getShooter() instanceof Player pShooter) {
                checkDeath(victim.getUniqueId(), pShooter.getUniqueId(), event, finalDamage);
            }
        } else {
            checkDeath(victim.getUniqueId(), null, event, finalDamage);
        }
    }

    private void handleNonEntityDamage(Player victim, EntityDamageEvent event, double finalDamage) {
        final UUID id = victim.getUniqueId();

        if ((victim.getHealth() - finalDamage) <= 0) {
            event.setCancelled(true);

            final UUID lastAttacker = CombatTagHelper.getLastAttacker(id);
            if (lastAttacker != null) {
                new DeathObject(id, lastAttacker);
            } else {
                new DeathObject(id, null);
            }
        } else {
            if (PlayerWrapper.getPlayer(victim).getDuel() == null) {
                CombatTagHelper.putInCombat(id);
            }
        }
    }

    private void checkDeath(UUID victim, @Nullable UUID killer, EntityDamageEvent event, double finalDamage) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(victim);
        Player pVictim = Bukkit.getPlayer(victim);

        if (pVictim == null) return;
        if (pw.getDuel() != null) {
            if ((pVictim.getHealth() - finalDamage) <= 0) {
                event.setCancelled(true);
                pw.getDuel().end(victim);
            }
            return;
        }

        CombatTagHelper.putInCombat(victim, killer);

        if ((pVictim.getHealth() - finalDamage) <= 0) {
            event.setCancelled(true);
            if (pVictim.getName().startsWith(".")) {
                pVictim.setHealth(0);
                return;
            }
            if (killer != null) {
                new DeathObject(victim, killer);
            } else {
                new DeathObject(victim, null);
            }
        }
    }
}

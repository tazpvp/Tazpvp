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
 *
 */

package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class ProjectileListener implements Listener {
    private static final Map<Projectile, Particle> arrowsToFollow = new HashMap<>();

    public ProjectileListener(JavaPlugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!arrowsToFollow.isEmpty()) {
                    for (Map.Entry<Projectile, Particle> entry : arrowsToFollow.entrySet()) {
                        Location location = entry.getKey().getLocation();
                        location.getWorld().spawnParticle(entry.getValue(), location, 2);
                    }
                }
            }
        }.runTaskTimer(plugin, 1, 2);
    }

    @EventHandler
    public void onProjectileLaunch(final ProjectileLaunchEvent e) {
        if (e.getEntityType() == EntityType.ARROW) {
            if (e.getEntity().getShooter() instanceof Player p) {
                final PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);
                if (!playerWrapper.getRank().getName().equals("default")) {
                    final String particleText = playerWrapper.getUserRankEntity().getArrowParticle();

                    if (particleText != null) {
                        arrowsToFollow.put(e.getEntity(), Particle.valueOf(particleText));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onProjectileLand(final ProjectileHitEvent e) {
        if (e.getEntityType() == EntityType.ARROW) {
            arrowsToFollow.remove(e.getEntity());
        }
    }
}

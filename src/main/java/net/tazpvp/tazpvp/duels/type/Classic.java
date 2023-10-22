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

package net.tazpvp.tazpvp.duels.type;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.duels.Duel;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.ArmorManager;
import world.ntdi.nrcore.utils.world.WorldUtil;

import java.util.List;
import java.util.UUID;

public class Classic extends Duel {

    Player p1;
    Player p2;

    public Classic(UUID P1, UUID P2) {
        super(P1, P2, "classic");
        p1 = Bukkit.getPlayer(super.getP1());
        p2 = Bukkit.getPlayer(super.getP2());
        super.setWorldName("duel_" + p1.getName() + "_" + p2.getName());
    }

    @Override
    public void initialize() {
        new WorldUtil().cloneWorld("duelMap1", super.getWorldName());

        getDUELERS().forEach(uuid -> PlayerWrapper.getPlayer(uuid).setDuel(this));
    }

    @Override
    public void begin() {

        World world = Bukkit.getWorld(super.getWorldName());

        setStarting(true);

        List<UUID> duelers = super.getDUELERS();

        duelers.forEach(id -> {
            Player p = Bukkit.getPlayer(id);
            ArmorManager.storeAndClearInventory(p);
            PlayerFunctions.resetHealth(p);
            PlayerFunctions.feedPlr(p);
            p.setGameMode(GameMode.SURVIVAL);
        });

        p1.teleport(new Location(world, 0.5, 10, 14.5, 180, 0));
        p2.teleport(new Location(world, 0.5, 10, -13.5, 0, 0));

        for (UUID id : super.getDUELERS()) {
            Player p = Bukkit.getPlayer(id);
            Inventory inv = p.getInventory();

            p.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            p.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            p.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            p.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

            inv.addItem(new ItemStack(Material.DIAMOND_SWORD));
            inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 6));
        }


        new BukkitRunnable() {
            int countDown = 5;
            @Override
            public void run() {
                if (countDown > 0) {
                    duelers.forEach(id -> {
                        Player p = Bukkit.getPlayer(id);
                        if (p != null) {
                            p.sendTitle(CC.BOLD + "" + CC.GOLD + countDown, "", 5, 10, 5);
                        }
                        countDown -= 1;
                    });
                } else {
                    duelers.forEach(id -> {
                        Player p = Bukkit.getPlayer(id);
                        if (p != null) {
                            p.sendTitle(CC.BOLD + "" + CC.GOLD + "BEGIN", "", 5, 10, 5);
                        }
                    });
                    setStarting(false);
                    end();
                }
            }
        }.runTaskTimer(Tazpvp.getInstance(), 20, 20);
    }
}

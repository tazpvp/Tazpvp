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

package net.tazpvp.tazpvp.duels;

import lombok.Getter;
import lombok.Setter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.ArmorManager;
import world.ntdi.nrcore.utils.world.WorldUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;

public abstract class Duel {

    @Getter
    private final UUID P1;
    @Getter
    private final UUID P2;
    @Getter
    private final String NAME;
    @Getter
    private final List<UUID> DUELERS;
    @Getter @Setter
    private UUID winner;
    @Getter @Setter
    private UUID loser;
    @Getter @Setter
    private String worldName;
    @Getter @Setter
    private boolean starting;

    public Duel(@Nonnull final UUID P1, @Nonnull final UUID P2, @Nonnull final String NAME) {
        this.P1 = P1;
        this.P2 = P2;
        this.NAME = NAME;

        this.DUELERS = new ArrayList<>();
        this.DUELERS.add(P1);
        this.DUELERS.add(P2);
    }

    public abstract void initialize();
    public abstract void begin();

    public void end(final UUID loser) {
        setWinner(getOtherDueler(loser));
        setLoser(loser);
        end();
    }
    public void end() {
        final Player winner = Bukkit.getPlayer(getWinner());
        final Player loser = Bukkit.getPlayer(getLoser());
        final OfflinePlayer offlineWinner = Bukkit.getOfflinePlayer(getWinner());
        final OfflinePlayer offlineLoser = Bukkit.getOfflinePlayer(getLoser());

        ChatFunctions.announce(offlineWinner.getName() + " won the duel against " + offlineLoser.getName());
        if (loser != null) {
            ArmorManager.setPlayerContents(loser, true);
            loser.teleport(NRCore.config.spawn);
            PlayerWrapper.getPlayer(loser).setDuel(null);
            PlayerFunctions.resetHealth(loser);
        }

        if (winner != null) {
            winner.sendTitle(CC.BOLD + "" + CC.GOLD + "YOU WIN", "", 20, 20, 20);
        }

        final Duel duel = this;

        new BukkitRunnable() {
            public void run() {
                if (winner != null) {
                    ArmorManager.setPlayerContents(winner, true);
                    winner.teleport(NRCore.config.spawn);

                    PlayerWrapper pw = PlayerWrapper.getPlayer(winner);
                    pw.setDuel(null);
                    PlayerFunctions.resetHealth(winner);
                }

                new WorldUtil().deleteWorld(getWorldName());
                duels.remove(duel);
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*5);
    }

    public void abort() {
        DUELERS.forEach(p -> {
            Player plr = Bukkit.getPlayer(p);
            if (plr != null) {
                plr.teleport(NRCore.config.spawn);
                ArmorManager.setPlayerContents(plr, true);
            }
        });

        new WorldUtil().deleteWorld(getWorldName());
        duels.remove(this);
    }

    public UUID getOtherDueler(final UUID id) {
        return (P1.equals(id) ? P2 : P1);
    }

    public static WeakHashMap<Duel, UUID> duels = new WeakHashMap<>();
}

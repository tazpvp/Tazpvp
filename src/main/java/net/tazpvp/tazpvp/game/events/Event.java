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

package net.tazpvp.tazpvp.game.events;

import lombok.Getter;
import lombok.Setter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.PlayerHelper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.world.WorldUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Deprecated
public abstract class Event implements Listener {

    public static List<String> eventTypes = new ArrayList<>();
    @Getter
    @Setter
    public static Event currentEvent;
    @Getter
    private List<UUID> participantList = new ArrayList<>();
    @Getter
    private List<UUID> aliveList = new ArrayList<>();

    private final UUID uuid;

    private final String NAME;

    public Event(@Nonnull final String NAME) {
        this.NAME = NAME;
        this.uuid = UUID.randomUUID();
        Tazpvp.getInstance().getServer().getPluginManager().registerEvents(this, Tazpvp.getInstance());
    }

    public abstract void begin();

    public void endGame(@Nullable final Player winner) {
        if (winner == null) {
            Bukkit.broadcastMessage("Nobody won");
        } else {
            Bukkit.broadcastMessage(CC.YELLOW + "" + CC.BOLD + winner.getDisplayName() + " won");
        }

        final List<UUID> participantListCopy = new ArrayList<>(getParticipantList());

        new BukkitRunnable() {
            @Override
            public void run() {
                for (final UUID id : participantListCopy) {
                    Bukkit.broadcastMessage(Bukkit.getPlayer(id).getName());
                    final Player player = Bukkit.getPlayer(id);
                    PlayerHelper.teleport(player, NRCore.config.spawn);
                    player.setGameMode(GameMode.SURVIVAL);
                }
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*5);

        new BukkitRunnable() {
            @Override
            public void run() {
                new WorldUtil().deleteWorld(uuid + "-" + getNAME());
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*10);

        participantList.clear();
        aliveList.clear();
        HandlerList.unregisterAll(this);
        Event.currentEvent = null;
    }

    public void checkIfGameOver() {
        if (getAliveList().size() <= 1) {
            final Player winner = Bukkit.getPlayer(getAliveList().get(0));

            if (winner != null && getAliveList().contains(winner.getUniqueId())) {
                endGame(winner);
            } else {
                endGame(null);
            }
        }
    }

    public void addAliveList(final UUID uuid) {
        this.aliveList.add(uuid);
    }

    public void removeAliveList(final UUID uuid) {
        this.aliveList.remove(uuid);
    }

    public void addParticipant(final UUID uuid) {
        this.participantList.add(uuid);
    }

    public void removeParticipant(final UUID uuid) {
        this.participantList.remove(uuid);
    }
}

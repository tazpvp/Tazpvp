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

package net.tazpvp.tazpvp.events;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.events.types.FFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public final class EventUtils {

    /**
     * Begin an event
     * @param name the Name of the event
     * @param playerList the list of participating players
     * @return a new event object
     */
    public static Event begin(String name, List<UUID> playerList) {
        if (name.equals("FFA"))
            return new FFA(playerList);
        return null;
    }

    /**
     * Check if the event is of decent size
     */
    public static void check() {
        if (Tazpvp.playerList.size() == 1) {
            end(getWinner());
        }
    }

    /**
     * Get the winner of the event
     * @return The winning Player
     */
    public static Player getWinner() {
        for (UUID uuid : Tazpvp.playerList) {
            return Bukkit.getPlayer(uuid);
        }
        return null;
    }

    /**
     * End an event
     * @param winner winner of the event
     */
    public static void end(@Nullable Player winner) {
        Tazpvp.eventKey = "";
        Tazpvp.playerList.clear();
        if (winner != null)
            Bukkit.broadcastMessage(winner.getName() + " won");
        else
            Bukkit.broadcastMessage("Nobody won");
    }
}

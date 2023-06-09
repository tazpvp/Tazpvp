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
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.World;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.ArmorManager;

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
    private Player winner;
    @Getter @Setter
    private Player loser;
    @Getter @Setter
    private String worldName;

    public Duel(@Nonnull final UUID P1, @Nonnull final UUID P2, @Nonnull final String NAME) {
        this.P1 = P1;
        this.P2 = P2;
        this.NAME = NAME;

        this.DUELERS = new ArrayList<>();
        this.DUELERS.add(P1);
        this.DUELERS.add(P2);
    }

    public abstract void begin();

    public abstract void end();

    public static WeakHashMap<Duel, UUID> duels = new WeakHashMap<>();
    public static Duel getDuel(UUID id) {
        for (Duel duel : duels.keySet()) {
            if (duel.DUELERS.contains(id)) {
                return duel;
            }
        }
        return null;
    }

}

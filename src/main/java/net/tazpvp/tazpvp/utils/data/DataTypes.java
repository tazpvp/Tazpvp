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

package net.tazpvp.tazpvp.utils.data;

import lombok.Getter;

import javax.annotation.Nonnull;

public enum DataTypes {
    COINS("coins", 2, true),
    XP("xp", 3, true),
    LEVEL("level", 4, true),
    KILLS("kills", 5, true),
    DEATHS("deaths", 6, true),
    TOPKILLSTREAK("top_ks", 7, true),
    PRESTIGE("rebirth", 8, true),
    DUELWINS("duel_wins", 9, true),
    DIVISION("division", 10, true),
    PLAYTIMEUNIX("playtime", 11, true),
    DAILYCRATEUNIX("daily_crate", 12, true),
    GUILD_ID("guild_id", 13, false),
    TALENTS("talents", 14, false),
    ACHIEVEMENTS("achievements", 15, false),
    LOADOUT("loadout", 16, false);

    @Getter
    private final String columnName;
    @Getter
    private final int columnIndex;
    @Getter
    private final boolean quantitative;

    DataTypes(@Nonnull final String columnName, final int columnIndex, final boolean quantitative) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.quantitative = quantitative;
    }
}

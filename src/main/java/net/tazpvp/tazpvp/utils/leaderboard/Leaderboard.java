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

package net.tazpvp.tazpvp.utils.leaderboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.tazpvp.tazpvp.utils.Sorting;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Leaderboard {
    @Getter
    private final DataTypes dataTypes;
    @Getter
    private final Map<UUID, Integer> sortedPlacement;

    public Leaderboard(DataTypes dataTypes) {
        this.dataTypes = dataTypes;

        if (!dataTypes.isQuantitative()) {
            throw new IllegalArgumentException("Must be quantitative integer");
        }

        Map<UUID, Integer> unsortedMap = putFirstEntries(10, PersistentData.getWithId(dataTypes));

        this.sortedPlacement = Sorting.sortByValueDesc(unsortedMap);
    }

    public <K,V> Map<K,V> putFirstEntries(int max, Map<K,V> source) {
        int count = 0;
        Map<K,V> target = new HashMap<K,V>();
        for (Map.Entry<K,V> entry:source.entrySet()) {
            if (count >= max) break;

            target.put(entry.getKey(), entry.getValue());
            count++;
        }
        return target;
    }



    @AllArgsConstructor
    public static class Placement implements Comparable<Placement> {
        @Getter
        private final int points;
        @Getter
        private final UUID uuid;

        @Override
        public int compareTo(Placement placement) {
            return getPoints() - placement.getPoints();
        }
    }

    public enum LeaderboardEnum {
        COINS(DataTypes.COINS, "Coins"),
        DEATHS(DataTypes.DEATHS, "Deaths"),
        KILLS(DataTypes.KILLS, "Kills"),
        LEVELS(DataTypes.LEVEL, "Levels");

        private final DataTypes dataTypes;
        private final String type;

        LeaderboardEnum(DataTypes dataTypes, String type) {
            this.dataTypes = dataTypes;
            this.type = type;
        }

        public Leaderboard getLeaderboard() {
            return new Leaderboard(dataTypes);
        }

        public String getType() {
            return type;
        }
    }
}

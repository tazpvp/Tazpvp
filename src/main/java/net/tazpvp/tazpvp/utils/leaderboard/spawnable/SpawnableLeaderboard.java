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

package net.tazpvp.tazpvp.utils.leaderboard.spawnable;

import lombok.Getter;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.leaderboard.Leaderboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import world.ntdi.nrcore.utils.holograms.Hologram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpawnableLeaderboard {
    @Getter
    private final DataTypes dataTypes;
    @Getter
    private final Hologram hologram;
    @Getter
    private final Location location;
    @Getter
    private final String title;

    public SpawnableLeaderboard(DataTypes dataTypes, String title, Location location) {
        this.dataTypes = dataTypes;
        this.location = location;
        this.title = title;

        this.hologram = new Hologram(getLocation(), false,
                CC.GRAY + "-< " + ChatFunctions.hexColor("#8aff70", getTitle(), false) + CC.GRAY + " >-",
                formatLine(1),
                formatLine(2),
                formatLine(3),
                formatLine(4),
                formatLine(5),
                formatLine(6),
                formatLine(7),
                formatLine(8),
                formatLine(9),
                formatLine(10)
                );
    }

    private String formatLine(int number, String name, int points) {
        return formatLine(number, name + " " +CC.WHITE + points);
    }

    private String formatLine(int number) {
        return formatLine(number, "Loading...");
    }

    private String formatLine(int number, String text) {
        return ChatFunctions.hexColor("#29e226", number + ". " + text, false);
    }

    public void update() {
        Leaderboard leaderboard = new Leaderboard(dataTypes);

        int count = 1;

        Map<UUID, Integer> sortedMap = leaderboard.getSortedPlacement();

        List<String> lines = new ArrayList<>();

        lines.add(CC.GRAY + "-< " + ChatFunctions.hexColor("#8aff70", getTitle(), false) + CC.GRAY + " >-");

        for (Map.Entry<UUID, Integer> entry : sortedMap.entrySet()) {
            lines.add(formatLine(count, Bukkit.getOfflinePlayer(entry.getKey()).getName(), entry.getValue()));
            count++;
        }

        while (lines.size() < 11) {
            lines.add(formatLine(count));
            count++;
        }

        hologram.updateHologram(lines.toArray(String[]::new));
    }
}

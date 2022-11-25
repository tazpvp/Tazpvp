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

package net.tazpvp.tazpvp.utils.functions;

import net.tazpvp.tazpvp.utils.data.PlayerData;
import net.tazpvp.tazpvp.utils.data.QuantitativeData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

public class ScoreboardFunctions {

    private static Scoreboard board = null;
    private static Objective objective = null;

    @SuppressWarnings("all")
    public static void initScoreboard(Player p) {

        board = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = board.registerNewObjective("sb", "dummy", "tazpvp");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        newLine(QuantitativeData.LEVEL, p, "Level:").setScore(6);
        newLine(QuantitativeData.COINS, p, "Coins:").setScore(5);
        newLine(QuantitativeData.XP, p, "Exp:").setScore(4);
        objective.getScore(" ").setScore(3);
        KDRScore(p, "KDR:").setScore(2);
        newLine(QuantitativeData.KILLS, p, "Kills:").setScore(1);
        newLine(QuantitativeData.DEATHS, p, "Deaths:").setScore(0);

        p.setScoreboard(board);
    }

    private static Score newLine(QuantitativeData q, Player p, String prefix) {

        String ID = UUID.randomUUID().toString();

        Team team = board.registerNewTeam(q.getColumnName());

        team.addEntry(ID);
        team.setPrefix(prefix + " ");
        team.setSuffix(String.valueOf(PlayerData.get(p, q)));

        return objective.getScore(ID);
    }

    private static Score KDRScore(Player p, String prefix) {
        String ID = UUID.randomUUID().toString();
        Team team = board.registerNewTeam("kdr");

        team.addEntry(ID);
        team.setPrefix(prefix + " ");
        team.setSuffix(String.valueOf(PlayerData.kdrFormula((int) PlayerData.get(p, QuantitativeData.KILLS), (int) PlayerData.get(p, QuantitativeData.DEATHS))));

        return objective.getScore(ID);
    }
}

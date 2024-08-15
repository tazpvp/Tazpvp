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

package net.tazpvp.tazpvp.helpers;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.LooseData;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.ScoreboardEnum;
import net.tazpvp.tazpvp.enums.StatEnum;
import net.tazpvp.tazpvp.enums.Theme;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

public class ScoreboardHelper {

    private static Scoreboard board = null;
    private static Objective objective = null;


    @SuppressWarnings("all")
    public static void initScoreboard(Player p) {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        UUID id = p.getUniqueId();

        if (board.getObjective("health") == null) {
            Objective healthObjective = board.registerNewObjective("health", Criteria.HEALTH, CC.RED + "❤", RenderType.INTEGER);
            healthObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }

        Team team = board.registerNewTeam("name");
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);

        PlayerStatEntity playerStatEntity = Tazpvp.getInstance().getPlayerStatService().getOrDefault(p.getUniqueId());

        objective = board.registerNewObjective("statboard", "dummy",  Theme.SERVER.gradient("TAZPVP.NET", true));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("                         ").setScore(8);
        createLine(ScoreboardEnum.RANK, ChatHelper.getRankingPrefix(p)+ "").setScore(7);
        createLine(ScoreboardEnum.LEVEL, StatEnum.LEVEL.getInt(id) + "").setScore(6);
        createLine(ScoreboardEnum.COINS, StatEnum.COINS.getInt(id) + "").setScore(5);
        objective.getScore(" ").setScore(4);
        createLine(ScoreboardEnum.KILLS, StatEnum.KILLS.getInt(id) + "").setScore(3);
        createLine(ScoreboardEnum.DEATHS, StatEnum.DEATHS.getInt(id) + "").setScore(2);
        createLine(ScoreboardEnum.KDR, LooseData.kdrFormula(StatEnum.KILLS.getInt(id), StatEnum.DEATHS.getInt(id)) + "").setScore(1);
        objective.getScore("   ").setScore(0);

        p.setScoreboard(board);
    }

    private static String scoreTitle(String text) {
        return Theme.SERVER.gradient(text, false);
    }

    private static Score createLine(ScoreboardEnum scoreboardEnum, String suffix) {
        String ID = scoreboardEnum.getId();
        Team team = board.registerNewTeam(scoreboardEnum.getName());

        team.addEntry(ID);
        team.setPrefix(scoreTitle(scoreboardEnum.getPrefix()) + CC.GRAY + " ");
        team.setSuffix(suffix);

        return objective.getScore(ID);
    }

    public static void updateSuffix(Player p, ScoreboardEnum scoreboardEnum, String suffix) {
        Team team = p.getScoreboard().getTeam(scoreboardEnum.getName());
        if (team != null) {
            team.setSuffix(suffix);
        }
    }
}

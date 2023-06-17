package net.tazpvp.tazpvp.utils;

import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import world.ntdi.nrcore.utils.ChatUtils;

public class PlayerNameTag {

    private char[] ranking = new char[]
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * Initialize a Player's overhead name
     * @param p The player
     */
    public void initializePlayerNameTag(Player p) {
        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (p.getUniqueId().equals(otherPlayer.getUniqueId())) continue;

            updateScoreboard(p, otherPlayer);
            updateScoreboard(otherPlayer, p);
        }

        updateScoreboard(p, p);
    }


    private void updateScoreboard(Player p1, Player p2) {
        PlayerWrapper otherWrapper = PlayerWrapper.getPlayer(p2);

        String teamName = getRankRanking(otherWrapper.getRank().getRank()) + p2.getUniqueId();
        Team team = p1.getScoreboard().getTeam(teamName);
        if (team != null) {
            team.unregister();
        }

        team = p1.getScoreboard().registerNewTeam(teamName);

        team.setDisplayName(p2.getName());

        team.setPrefix(ChatUtils.chat(otherWrapper.getPrefix()));
        team.setSuffix(ChatUtils.chat(otherWrapper.getGuildTag()));
        team.setColor(otherWrapper.getRank().getColor());

        team.addPlayer(p2);
    }

    /**
     * For sorting tab list which ranks
     * @param rank Ranking number
     * @return Ranked value string
     */
    private String getRankRanking(final int rank) {
        int count = 26 / rank;
        String value = "";
        for (int i = count - 1; i >= 0; i--) {
            value = value + ranking[i];
        }
        return value;
    }
}

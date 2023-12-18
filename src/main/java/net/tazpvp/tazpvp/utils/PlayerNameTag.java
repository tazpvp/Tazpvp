package net.tazpvp.tazpvp.utils;

import net.tazpvp.tazpvp.data.Rank;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import world.ntdi.nrcore.utils.ChatUtils;

public class PlayerNameTag {
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
        if (otherWrapper == null) {
            Bukkit.getLogger().severe("GYATT! NTDI BIG ERROR! Player 1 " + p1.getName() + " Player 2 " + p2.getName());
            return;
        }

        String teamName = "" + otherWrapper.getRank().getRank() + p2.getUniqueId();
        Team team = p1.getScoreboard().getTeam(teamName);
        if (team != null) {
            team.unregister();
        }

        team = p1.getScoreboard().registerNewTeam(teamName);

        team.setDisplayName(p2.getName());


        String prefix;
        String prefixSeparator;

        if (otherWrapper.getRankPrefix() == null) {
            prefix = "";
            prefixSeparator = "";
        } else {
            prefix = ChatUtils.chat(otherWrapper.getRankPrefix());
            prefixSeparator = " ";
        }

        if (otherWrapper.getRank() == Rank.PREMIUM) {
            team.setColor(ChatColor.GREEN);
        } else if (otherWrapper.getRank() == Rank.DEFAULT) {
            team.setColor(ChatColor.GRAY);
        }

        final String suffix = otherWrapper.getGuildTag();
        final String suffixSeparator = suffix.isBlank() ? "" : " ";

        team.setPrefix(prefix + prefixSeparator);
        team.setSuffix(suffixSeparator + suffix);

        final String gradient = ChatFunctions.gradient("#c16bff", "l", true);
        final boolean isBold = gradient.toCharArray()[gradient.toCharArray().length-2] == 'b';

        team.addPlayer(p2);
    }
}

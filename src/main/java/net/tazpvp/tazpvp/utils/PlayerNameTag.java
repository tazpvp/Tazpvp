package net.tazpvp.tazpvp.utils;

import com.google.common.base.Preconditions;
import net.tazpvp.tazpvp.data.Rank;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
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

        otherWrapper.refreshRankEntity();

        String teamName = "" + otherWrapper.getRank().getHierarchy() + p2.getUniqueId();
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

        if (otherWrapper.getRank().getName().equals("premium")) {
            team.setColor(ChatColor.GREEN);
        } else if (otherWrapper.getRank().getName().equals("default")) {
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

    public ChatColor getLastColors(@NotNull String input) {

        int length = input.length();

        // Search backwards from the end as it is faster
        for (int index = length - 1; index > -1; index--) {
            char section = input.charAt(index);
            if (section == ChatColor.COLOR_CHAR && index < length - 1) {
                char c = input.charAt(index + 1);

                return ChatColor.getByChar(c);
            }
        }

        return null;
    }
}

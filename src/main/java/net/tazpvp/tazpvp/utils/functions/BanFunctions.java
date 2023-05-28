package net.tazpvp.tazpvp.utils.functions;

import net.tazpvp.tazpvp.utils.TimeToken;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BanFunctions {

    public static void ban(Player target, String time) {
        ban(target, time, "Unfair Advantage");
    }

    public static void ban(Player target, String time, String reason) {
        BanList banList = Bukkit.getBanList(BanList.Type.NAME);
        TimeToken timeToken = new TimeToken(time);

        if (timeToken.isPermanent()) {

        }
    }
}

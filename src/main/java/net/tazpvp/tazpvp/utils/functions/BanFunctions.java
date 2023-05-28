package net.tazpvp.tazpvp.utils.functions;

import net.tazpvp.tazpvp.utils.TimeToken;
import net.tazpvp.tazpvp.utils.data.PunishmentData;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BanFunctions {

    public static void ban(Player target, String time) {
        ban(target, time, "Unfair Advantage");
    }

    public static void ban(Player target, String time, String reason) {
        TimeToken timeToken = new TimeToken(time);

        if (timeToken.isPermanent()) {
            target.kickPlayer("You got banned xd join discord xd lol \n" + "hello");
        }
    }
}

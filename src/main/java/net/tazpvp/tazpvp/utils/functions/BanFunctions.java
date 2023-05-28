package net.tazpvp.tazpvp.utils.functions;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class BanFunctions {

    public static void ban(Player target, String time) {
        ban(target, time, "Unfair Advantage");
    }

    public static void ban(Player target, String time, String reason) {
        BanList banList = Bukkit.getBanList(BanList.Type.NAME);
    }
}

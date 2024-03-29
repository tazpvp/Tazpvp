package net.tazpvp.tazpvp.utils.passive;

import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.enums.ColorCodes;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import world.ntdi.nrcore.utils.holograms.Hologram;

import java.util.ArrayList;
import java.util.List;

public class Holograms {

    public static List<Hologram> holograms = new ArrayList<>();

    private static String[] welcome = {
            CC.WHITE + "Welcome to " + ChatFunctions.gradient(ColorCodes.SERVER.toString(), "Tazpvp", true),
            "Type " + ChatFunctions.hexColor(ColorCodes.SERVER.toString(), "/help ", true), "for assistance"
    };
    public static void holograms() {
        holograms.add(
                new Hologram(new Location(Bukkit.getWorld("arena"), 0.5, 99, 26.5), false, welcome)
        );
        holograms.add(new Hologram(ChatFunctions.gradient("#8fff17", "AFK PIT | 1x Key", true), new Location(Bukkit.getWorld("arena"), 14, 99, 7), false));
    }

    public static void removeHolograms() {
        for (Entity entity : Bukkit.getWorld("arena").getEntities()) {
            if (entity.getType() == EntityType.ARMOR_STAND) {
                entity.remove();
            }
        }
    }
}

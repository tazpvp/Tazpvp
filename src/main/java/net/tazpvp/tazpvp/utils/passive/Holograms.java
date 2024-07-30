package net.tazpvp.tazpvp.utils.passive;

import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.Theme;
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
            CC.WHITE + "Welcome to " +  Theme.SERVER.gradient("Tazpvp", true),
            "Type " + CC.AQUA + "/help", CC.WHITE + "for a guide!"
    };
    private static String[] afkPit = {
            ChatFunctions.gradient("#8fff17", "AFK PIT", true),
            ChatFunctions.gradient("#8fff17", "1x Key Rewards", true),
    };
    public static void holograms() {
        holograms.add(
                new Hologram(new Location(Bukkit.getWorld("arena"), 0.5, 99, 26.5), false, welcome)
        );
        holograms.add(
                new Hologram(new Location(Bukkit.getWorld("arena"), 14, 99, 7), false, afkPit)
        );
    }

    public static <World> void removeHolograms() {
        org.bukkit.World world = Bukkit.getWorld("arena");
        if (world == null) return;
        for (Entity entity : world.getEntities()) {
            if (entity.getType() == EntityType.ARMOR_STAND) {
                entity.remove();
            }
        }
    }
}

package net.tazpvp.tazpvp.utils.passive;

import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.Theme;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import world.ntdi.nrcore.utils.holograms.Hologram;

import java.util.ArrayList;
import java.util.List;

public class Holograms {

    public final static List<Hologram> holograms = new ArrayList<>();

    private final static String[] welcome = {
            CC.WHITE + "Welcome to " +  Theme.SERVER.gradient("Tazpvp", true),
            "Type " + CC.AQUA + "/help", CC.WHITE + "for a guide!"
    };
    private final static String[] afkPit = {
            ChatHelper.gradient("#8fff17", "AFK PIT", true),
            ChatHelper.gradient("#8fff17", "1x Key Rewards", true),
    };
    public static void initialize() {
        holograms.add(
                new Hologram(new Location(Bukkit.getWorld("arena"), 0.5, 99, 26.5), false, welcome)
        );
        holograms.add(
                new Hologram(new Location(Bukkit.getWorld("arena"), 14, 99, 7), false, afkPit)
        );
    }

    public static void removeHolograms() {
        World world = Bukkit.getWorld("arena");
        if (world == null) return;
        for (Entity entity : world.getEntities()) {
            if (entity.getType() == EntityType.ARMOR_STAND) {
                entity.remove();
            }
        }
    }
}

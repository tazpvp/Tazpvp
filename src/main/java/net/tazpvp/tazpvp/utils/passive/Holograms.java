package net.tazpvp.tazpvp.utils.passive;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import world.ntdi.nrcore.utils.holograms.Hologram;

import java.util.ArrayList;
import java.util.List;

public class Holograms {

    public static List<Hologram> holograms = new ArrayList<>();
    public static void holograms() {
        holograms.add(new Hologram("Welcome", new Location(Bukkit.getWorld("arena"), 0.5, 99, 26.5), false));
        holograms.add(new Hologram("AFK PIT", new Location(Bukkit.getWorld("arena"), 14, 99, 7), false));
    }

    public static void removeHolograms() {
        for (Entity entity : Bukkit.getWorld("arena").getEntities()) {
            if (entity.getType() == EntityType.ARMOR_STAND) {
                entity.remove();
            }
        }
    }
}

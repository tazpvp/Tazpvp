package net.tazpvp.tazpvp.utils.passive;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import world.ntdi.nrcore.utils.holograms.Hologram;

public class Holograms {
    public static void holograms() {

        new Hologram("Welcome", new Location(Bukkit.getWorld("arena"), 0.5, 100, 26.5), false);
    }


}

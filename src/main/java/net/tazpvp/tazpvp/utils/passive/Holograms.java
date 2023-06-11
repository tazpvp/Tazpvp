package net.tazpvp.tazpvp.utils.passive;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import world.ntdi.nrcore.utils.holograms.Hologram;

import java.util.ArrayList;
import java.util.List;

public class Holograms {

    public static List<Hologram> holograms = new ArrayList<>();
    public static void holograms() {
        holograms.add(new Hologram("Welcome", new Location(Bukkit.getWorld("arena"), 0.5, 99, 26.5), false));
        holograms.add(new Hologram("AFK PIT", new Location(Bukkit.getWorld("arena"), 14, 99, 6), false));
    }

    public static void removeHolograms() {
        for (Hologram hologram : holograms) {
            hologram.deleteHologram();
        }
    }
}

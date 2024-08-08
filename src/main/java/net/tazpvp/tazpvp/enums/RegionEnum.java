package net.tazpvp.tazpvp.enums;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import world.ntdi.nrcore.utils.region.Cuboid;

public enum RegionEnum {

    SPAWN(25, 137, -31,-24, 93, 25),
    AFK(16, 98, 9, 11, 95, 4);

    private final int x1;
    private final int y1;
    private final int z1;
    private final int x2;
    private final int y2;
    private final int z2;

    public static Cuboid spawnRegion = SPAWN.getRegion();
    public static Cuboid afkRegion = AFK.getRegion();

    RegionEnum(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public Cuboid getRegion() {
        World world = Bukkit.getWorld("arena");
        if (world == null) return null;
        return new Cuboid(
                new Location(world, x1, y1, z1),
                new Location(world, x2, y2, z2)
        );
    }
}

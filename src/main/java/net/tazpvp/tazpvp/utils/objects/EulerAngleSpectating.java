package net.tazpvp.tazpvp.utils.objects;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class EulerAngleSpectating {
    @Getter
    private final Location A;
    @Getter
    private Location result;
    private final Random random;

    public EulerAngleSpectating(Location a) {
        this.random = new Random();
        A = a;
        int tries = 0;
        boolean isSafe = false;
        while (!isSafe) {
            if (tries > 10) {
                result = new Location(getA().getWorld(), -42.5, 108, 76.5, -88.5F, 25.7F);
                isSafe = true;
            }
            Location location = calculateResult();
            if (location.getBlock().getType() == Material.AIR) {
                result = location;
                isSafe = true;
            }
            tries++;
        }
    }

    public void faceLocation(Player p) {
        Vector direction = getA().clone().subtract(p.getEyeLocation()).toVector();
        Location loc = p.getLocation().setDirection(direction);
        p.teleport(loc);
    }

    private Location calculateResult() {
        final World world = getA().getWorld();
        final double x = getA().getX() + calculateNegative(5);
        final double y = getA().getY() + 6;
        final double z = getA().getZ() + calculateNegative(5);
        return new Location(world, x, y, z);
    }

    private int calculateNegative(int toCalc) {
        final int choice = random.nextInt(2);
        if (choice == 1) {
            return -1 * toCalc;
        }
        return toCalc;
    }
}

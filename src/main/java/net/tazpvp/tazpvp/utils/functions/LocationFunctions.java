package net.tazpvp.tazpvp.utils.functions;

import org.bukkit.Location;

import java.util.Random;

public class LocationFunctions {
    public static Location getRandomLocationAround(Location targetLocation, double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than zero.");
        }

        Random random = new Random();
        double angle = random.nextDouble() * 2 * Math.PI;
        double distance = Math.sqrt(random.nextDouble()) * radius;

        double xOffset = distance * Math.cos(angle);
        double zOffset = distance * Math.sin(angle);

        double x = targetLocation.getX() + xOffset;
        double y = targetLocation.getY();
        double z = targetLocation.getZ() + zOffset;

        return new Location(targetLocation.getWorld(), x, y, z);
    }
}

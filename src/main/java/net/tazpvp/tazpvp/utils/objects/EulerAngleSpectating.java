package net.tazpvp.tazpvp.utils.objects;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.Random;

public class EulerAngleSpectating {
    @Getter
    private final Location A;
    @Getter
    private final Location result;
    private final Random random;

    public EulerAngleSpectating(Location a) {
        this.random = new Random();
        A = a;
        final World world = a.getWorld();
        final double x = a.getX() + calculateNegative(5);
        final double y = a.getY() + 6;
        final double z = a.getZ() + calculateNegativeOrZero(5);
        final float yaw = yawFromLocation(x, y, z);
        final float pitch = 45F;
        result = new Location(world, x, y, z, yaw, pitch);
    }

    private int calculateNegative(int toCalc) {
        final int choice = random.nextInt(2);
        return (choice == 0 ? toCalc * -1 : toCalc);
    }

    private int calculateNegativeOrZero(int toCalc) {
        final int choice = random.nextInt(3);
        switch (choice) {
            case 0 -> {
                return toCalc;
            }
            case 2 -> {
                return -1 * toCalc;
            }
            default -> {
                return 0;
            }
        }
    }

    private float yawFromLocation(double x, double y, double z) {
        Vector direction = getA().toVector().subtract(new Vector(x, y, z));
        return (float) Math.toDegrees(Math.atan2(-direction.getZ(), direction.getX()));
    }
}

package net.tazpvp.tazpvp.game.bosses.zorg.attacks;

import net.tazpvp.tazpvp.game.bosses.CustomBoss;
import net.tazpvp.tazpvp.game.bosses.attacks.Attack;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Random;

public class SonicBoomAttack implements Attack {

    private final Random random = new Random();

    @Override
    public void attack(CustomBoss boss) {
        final WitherSkeleton skeleton = (WitherSkeleton) boss.getBoss();
        final Location origin = skeleton.getEyeLocation();
        final Vector direction = origin.getDirection();
        direction.multiply(10);
        direction.normalize();

        final RayTraceResult result = skeleton.getWorld().rayTraceEntities(origin, direction, 10,
                entity -> entity instanceof Player && !entity.getUniqueId().equals(skeleton.getUniqueId()));

        if (result != null && result.getHitEntity() != null) {
            final Player target = (Player) result.getHitEntity();

            if (target.getGameMode() == GameMode.SURVIVAL) {
                for (int i = 0; i < 30; i++) {
                    Location loc = origin.clone().add(direction.clone().multiply(i));

                    loc.getWorld().spawnParticle(Particle.CRIT, loc, 1, (Math.random() - 0.5) * 0.5, (Math.random() - 0.5) * 0.5, (Math.random() - 0.5) * 0.5, 0.05);
                    loc.getWorld().spawnParticle(Particle.EXPLOSION, loc, 1, (Math.random() - 0.5) * 0.5, (Math.random() - 0.5) * 0.5, (Math.random() - 0.5) * 0.5, 0.05);
                }

                origin.getWorld().playSound(origin, Sound.ENTITY_SQUID_SQUIRT, 1.0f, 1.0f);
                origin.getWorld().spawnParticle(Particle.EXPLOSION, origin, 1, 0, 0, 0, 0.05);

                target.damage(random.nextInt(2) + 2);
            }
        }
    }
}

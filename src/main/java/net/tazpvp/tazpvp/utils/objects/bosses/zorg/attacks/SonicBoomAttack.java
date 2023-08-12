package net.tazpvp.tazpvp.utils.objects.bosses.zorg.attacks;

import lombok.With;
import net.tazpvp.tazpvp.utils.objects.bosses.BOSSSSS;
import net.tazpvp.tazpvp.utils.objects.bosses.attacks.Attack;
import org.bukkit.Particle;
import org.bukkit.entity.Boss;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.util.RayTraceResult;

import java.util.Random;

public class SonicBoomAttack implements Attack {

    private final Random random = new Random();

    @Override
    public void attack(BOSSSSS boss) {
        WitherSkeleton skeleton = (WitherSkeleton) boss.getBoss();
        final RayTraceResult result = boss.getBoss().getWorld().rayTraceEntities(
                skeleton.getEyeLocation().add(boss.getBoss().getLocation().getDirection()),
                skeleton.getEyeLocation().getDirection(),
                10,
                entity -> {
                    if (entity.getUniqueId().equals(boss.getBoss().getUniqueId())) {
                        return false;
                    } else return entity.getType() == EntityType.PLAYER;
                }
        );

        if (result == null || result.getHitEntity() == null) {
            return;
        }

        Player target = (Player) result.getHitEntity();

        boss.getBoss().getWorld().spawnParticle(Particle.SONIC_BOOM, boss.getBoss().getLocation(), 1);
        target.damage(random.nextInt(3, 5));
    }
}

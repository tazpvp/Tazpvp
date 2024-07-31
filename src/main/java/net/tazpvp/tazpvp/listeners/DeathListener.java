package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.game.bosses.BossManager;
import net.tazpvp.tazpvp.game.bosses.zorg.attacks.SummonUndeadAttack;
import net.tazpvp.tazpvp.helpers.DeathFunctions;
import net.tazpvp.tazpvp.objects.DeathObject;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(final EntityDeathEvent event) {
        final Entity entity = event.getEntity();

        final UUID uuid = entity.getUniqueId();

        final Location location = entity.getLocation();

        event.setDroppedExp(0);
        event.getDrops().clear();

        if (BossManager.getSpawnedBoss() == null) {
            return;
        }

        if (entity instanceof Player player) {
            final PlayerWrapper pw = PlayerWrapper.getPlayer(player);
            if (pw.getDuel() != null) {
                pw.getDuel().end(player.getUniqueId());
            }

            final Player killer = player.getKiller();

            if (killer != null) {
                new DeathObject(player.getUniqueId(), killer.getUniqueId());
            } else {
                new DeathObject(player.getUniqueId(), null);
            }

            ((PlayerDeathEvent) event).setDeathMessage(null);
        }

        final UUID bossUUID = BossManager.getSpawnedBoss().getBoss().getUniqueId();

        if (uuid.equals(bossUUID)) {
            if (event.getEntity().getKiller() != null) {
                Tazpvp.getObservers().forEach(observer -> observer.kill_zorg(event.getEntity().getKiller()));
            }

            BossManager.bossDied();
            World world = entity.getWorld();
            world.dropItemNaturally(location.add(0, 1, 0), DeathFunctions.deathItem());

            if (!SummonUndeadAttack.undeadList.isEmpty()) {
                for (Zombie z : SummonUndeadAttack.undeadList) {
                    z.setHealth(0);
                }
            }
            SummonUndeadAttack.undeadList.clear();
        }
    }
}

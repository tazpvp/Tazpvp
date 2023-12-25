package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.game.bosses.BossManager;
import net.tazpvp.tazpvp.game.bosses.zorg.attacks.SummonUndeadAttack;
import net.tazpvp.tazpvp.game.duels.Duel;
import net.tazpvp.tazpvp.utils.functions.DeathFunctions;
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

public class Death implements Listener {
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
            final PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(player);
            if (playerWrapper.getDuel() != null) {
                final Duel duel = playerWrapper.getDuel();
                duel.setLoser(player.getUniqueId());
                duel.setWinner(duel.getOtherDueler(player.getUniqueId()));
                duel.end();
            }

            final Player killer = player.getKiller();

            if (killer != null) {
                DeathFunctions.death(player.getUniqueId(), killer.getUniqueId());
            } else {
                DeathFunctions.death(player.getUniqueId());
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
            world.dropItemNaturally(location.add(0, 1, 0), net.tazpvp.tazpvp.utils.objects.Death.deathItem());

            if (!SummonUndeadAttack.undeadList.isEmpty()) {
                for (Zombie z : SummonUndeadAttack.undeadList) {
                    z.setHealth(0);
                }
            }
            SummonUndeadAttack.undeadList.clear();
        }
    }
}

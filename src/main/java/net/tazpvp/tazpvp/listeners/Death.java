package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.bosses.BossManager;
import net.tazpvp.tazpvp.bosses.zorg.attacks.SummonUndeadAttack;
import net.tazpvp.tazpvp.duels.Duel;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class Death implements Listener {
    @EventHandler
    public void onDeath(final EntityDeathEvent event) {
        final Entity entity = event.getEntity();

        final UUID uuid = entity.getUniqueId();

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
        }

        final UUID bossUUID = BossManager.getSpawnedBoss().getBoss().getUniqueId();

        if (uuid.equals(bossUUID)) {
            BossManager.bossDied();
            if (!SummonUndeadAttack.undeadList.isEmpty()) {
                for (Zombie z : SummonUndeadAttack.undeadList) {
                    SummonUndeadAttack.undeadList.clear();
                    z.setHealth(0);
                }
            }
        }
    }
}

package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.utils.objects.Zorg;
import net.tazpvp.tazpvp.utils.passive.CustomBosses;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.swing.text.html.parser.Entity;

public class Death implements Listener {

    public void onDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        for (Zorg boss : CustomBosses.bosses) {
            if (boss.getZorg() == entity) {
                CustomBosses.respawnZorg();
            }
        }


    }
}

package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.LooseData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Grinder extends Observable {
    @Override
    public void mine(Player p, Material material) {
        if (!PersistentData.getAchievements(p.getUniqueId()).is("Grinder")) {
            if (LooseData.getMineCount(p.getUniqueId()) >= 100) {
                Achievements ach = PersistentData.getAchievements(p.getUniqueId());
                ach.set("Grinder", true);
                PersistentData.setAchievements(p, ach);
                Achievements.announce(p, "Grinder");
            }
        }
    }
}

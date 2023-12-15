package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.entity.Player;

public class Error extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        if (!PersistentData.getAchievements(killer.getUniqueId()).is("Error")) {
            if (PersistentData.getInt(victim.getUniqueId(), DataTypes.DEATHS) >= 100) {
                Achievements ach = PersistentData.getAchievements(killer.getUniqueId());
                ach.set("Error", true);
                PersistentData.setAchievements(killer, ach);
                Achievements.announce(killer, "Error");
            }
        }
    }
}

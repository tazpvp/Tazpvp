package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.entity.Player;

public class Zorging extends Observable {
    @Override
    public void kill_zorg(Player p) {
        Achievements achievements = PersistentData.getAchievements(p.getUniqueId());
        achievements.set("Zorging", true);
        PersistentData.setAchievements(p, achievements);
    }
}

package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.entity.Player;

public class Zorgin extends Observable {
    @Override
    public void kill_zorg(Player p) {
        if (!PersistentData.getAchievements(p.getUniqueId()).is("Zorgin")) {
            Achievements achievements = PersistentData.getAchievements(p.getUniqueId());
            achievements.set("Zorgin", true);
            PersistentData.setAchievements(p, achievements);
            ChatFunctions.achievement(p, "Zorgin");
        }
    }
}

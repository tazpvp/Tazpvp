package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.guis.Shop.Maxim;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.entity.Player;

public class Merchant extends Observable {
    @Override
    public void gui(Player p, String name) {
        if (!PersistentData.getAchievements(p.getUniqueId()).is("Merchant")) {
            if (name.equalsIgnoreCase("Caesar")) {
                Achievements ach = PersistentData.getAchievements(p.getUniqueId());
                ach.set("Merchant", true);
                PersistentData.setAchievements(p, ach);
                ChatFunctions.achievement(p, "Merchant");
            }
        }
    }
}

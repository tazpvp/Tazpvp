package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.PlayerData;
import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.entity.Player;

public class Merchant extends Observable {
    @Override
    public void gui(Player p, String name) {
        if (name.equalsIgnoreCase("caesar")) { // TODO: Add proper name
            Achievements ach = PlayerData.getAchievements(p.getUniqueId());
            ach.setMerchant(true);
            PlayerData.setAchievements(p, ach);
        }
    }
}

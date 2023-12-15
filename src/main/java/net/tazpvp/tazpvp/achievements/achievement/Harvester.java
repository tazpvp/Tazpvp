package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Harvester extends Observable {

    @Override
    public void open_coffin(Player p) {
        if (!PersistentData.getAchievements(p.getUniqueId()).is("Harvester")) {
            PlayerWrapper pw = PlayerWrapper.getPlayer(p);
            if (pw.getCoffinCount() >= 10) {
                Achievements ach = PersistentData.getAchievements(p.getUniqueId());
                ach.set("Harvester", true);
                PersistentData.setAchievements(p, ach);
                ChatFunctions.achievement(p, "Harvester");
            }
        }
    }
}

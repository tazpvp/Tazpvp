package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Speedrunner extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        if (!PersistentData.getAchievements(killer.getUniqueId()).is("Speedrunner")) {
            PlayerWrapper pw = PlayerWrapper.getPlayer(killer);
            if (System.currentTimeMillis() - pw.getTimeOfLaunch() <= 60*1000) {
                if (pw.getKillCount() >= 10) {
                    Achievements ach = PersistentData.getAchievements(killer.getUniqueId());
                    ach.set("Speedrunner", true);
                    PersistentData.setAchievements(killer, ach);
                } else {
                    pw.setKillCount(pw.getKillCount() + 1);
                }
            }
        }
    }
}

package net.tazpvp.tazpvp.player.achievements.achievement;

import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Harvester extends Observable {

    @Override
    public void open_coffin(Player p) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        final UserAchievementEntity userAchievementEntity = pw.getUserAchievementEntity();
        final AchievementEntity achievementEntity = userAchievementEntity.getHarvesterAchievementEntity();

        if (!achievementEntity.isCompleted()) {
            if (pw.getCoffinCount() >= 10) {
                achievementEntity.setCompleted(true);
                userAchievementEntity.setHarvesterAchievementEntity(achievementEntity);
                pw.setUserAchievementEntity(userAchievementEntity);
                ChatFunctions.achievement(p, "Harvester");
            }
        }
    }
}

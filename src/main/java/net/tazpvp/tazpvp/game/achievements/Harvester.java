package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
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
                ChatHelper.achievement(p, "Harvester");
            }
        }
    }
}

package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.entity.Player;

public class Zorgin extends Observable {
    @Override
    public void kill_zorg(Player p) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        final UserAchievementEntity userAchievementEntity = pw.getUserAchievementEntity();
        final AchievementEntity achievementEntity = userAchievementEntity.getZorginAchievementEntity();

        if (!achievementEntity.isCompleted()) {
            achievementEntity.setCompleted(true);
            userAchievementEntity.setZorginAchievementEntity(achievementEntity);
            pw.setUserAchievementEntity(userAchievementEntity);
            ChatHelper.achievement(p, "Zorgin");
        }
    }
}

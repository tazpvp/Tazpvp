package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.helpers.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Speedrunner extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(killer);
        final UserAchievementEntity userAchievementEntity = pw.getUserAchievementEntity();
        final AchievementEntity achievementEntity = userAchievementEntity.getSpeedrunnerAchievementEntity();

        if (!achievementEntity.isCompleted()) {
            if (System.currentTimeMillis() - pw.getTimeOfLaunch() <= 30 * 1000) {
                if (pw.getKillCount() >= 10) {
                    achievementEntity.setCompleted(true);
                    userAchievementEntity.setSpeedrunnerAchievementEntity(achievementEntity);
                    pw.setUserAchievementEntity(userAchievementEntity);
                    ChatFunctions.achievement(killer, "Speedrunner");
                } else {
                    pw.setKillCount(pw.getKillCount() + 1);
                }
            }
        }
    }
}

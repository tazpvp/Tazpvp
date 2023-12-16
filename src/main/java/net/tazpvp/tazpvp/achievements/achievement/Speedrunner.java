package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Speedrunner extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        final PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(killer);
        final AchievementEntity achievementEntity = playerWrapper.getAchievementEntity();

        if (!achievementEntity.isSpeedrunner()) {
            if (System.currentTimeMillis() - playerWrapper.getTimeOfLaunch() <= 30 * 1000) {
                if (playerWrapper.getKillCount() >= 10) {
                    achievementEntity.setSpeedrunner(true);
                    playerWrapper.setAchievementEntity(achievementEntity);
                    ChatFunctions.achievement(killer, "Speedrunner");
                } else {
                    playerWrapper.setKillCount(playerWrapper.getKillCount() + 1);
                }
            }
        }
    }
}

package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Zorgin extends Observable {
    @Override
    public void kill_zorg(Player p) {
        final PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);
        final AchievementEntity achievementEntity = playerWrapper.getAchievementEntity();

        if (!achievementEntity.isZorgin()) {
            achievementEntity.setZorgin(true);
            playerWrapper.setAchievementEntity(achievementEntity);
            ChatFunctions.achievement(p, "Zorgin");
        }
    }
}

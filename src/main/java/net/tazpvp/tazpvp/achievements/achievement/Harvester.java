package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Harvester extends Observable {

    @Override
    public void open_coffin(Player p) {
        final PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);
        final AchievementEntity achievementEntity = playerWrapper.getAchievementEntity();

        if (!achievementEntity.isHarvester()) {
            if (playerWrapper.getCoffinCount() >= 10) {
                achievementEntity.setHarvester(true);
                playerWrapper.setAchievementEntity(achievementEntity);
                ChatFunctions.achievement(p, "Harvester");
            }
        }
    }
}

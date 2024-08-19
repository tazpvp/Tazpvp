package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.services.AchievementService;
import net.tazpvp.tazpvp.data.services.UserAchievementService;
import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.entity.Player;

public class Harvester extends Observable {
    private final UserAchievementService userAchievementService = Tazpvp.getInstance().getUserAchievementService();
    private final AchievementService achievementService = Tazpvp.getInstance().getAchievementService();
    @Override
    public void open_coffin(Player p) {
        final UserAchievementEntity userAchievementEntity =  userAchievementService.getOrDefault(p.getUniqueId());
        final AchievementEntity achievementEntity = userAchievementEntity.getHarvester();

        if (!achievementEntity.isCompleted()) {

        }
    }
}

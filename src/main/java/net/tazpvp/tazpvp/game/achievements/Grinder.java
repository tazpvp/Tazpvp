package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.data.LooseData;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.helpers.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Grinder extends Observable {
    @Override
    public void mine(Player p, Material material) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        final UserAchievementEntity userAchievementEntity = pw.getUserAchievementEntity();
        final AchievementEntity achievementEntity = userAchievementEntity.getGrinderAchievementEntity();

        if (!achievementEntity.isCompleted()) {
            if (LooseData.getMineCount(p.getUniqueId()) >= 100) {
                achievementEntity.setCompleted(true);
                userAchievementEntity.setGrinderAchievementEntity(achievementEntity);
                pw.setUserAchievementEntity(userAchievementEntity);
                ChatFunctions.achievement(p, "Grinder");
            }
        }
    }
}

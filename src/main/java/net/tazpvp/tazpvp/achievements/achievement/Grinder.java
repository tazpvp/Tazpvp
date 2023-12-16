package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.LooseData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Grinder extends Observable {
    @Override
    public void mine(Player p, Material material) {
        final PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);
        final AchievementEntity achievementEntity = playerWrapper.getAchievementEntity();

        if (!achievementEntity.isGrinder()) {
            if (LooseData.getMineCount(p.getUniqueId()) >= 100) {
                achievementEntity.setGrinder(true);
                playerWrapper.setAchievementEntity(achievementEntity);
                ChatFunctions.achievement(p, "Grinder");
            }
        }
    }
}

package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.guis.Shop.Maxim;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Merchant extends Observable {
    @Override
    public void gui(Player p, String name) {
        final PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);
        final AchievementEntity achievementEntity = playerWrapper.getAchievementEntity();

        if (!achievementEntity.isMerchant()) {
            if (name.equalsIgnoreCase("caesar")) {
                achievementEntity.setMerchant(true);
                playerWrapper.setAchievementEntity(achievementEntity);
                ChatFunctions.achievement(p, "Merchant");
            }
        }
    }
}

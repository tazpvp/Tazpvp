package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class Artisan extends Observable {
    private List<Material> woods = List.of(
            Material.OAK_PLANKS,
            Material.SPRUCE_PLANKS,
            Material.BIRCH_PLANKS,
            Material.JUNGLE_PLANKS,
            Material.ACACIA_PLANKS,
            Material.DARK_OAK_PLANKS,
            Material.CRIMSON_PLANKS,
            Material.WARPED_PLANKS
    );
    @Override
    public void place(Player p, Block b) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        final UserAchievementEntity userAchievementEntity = pw.getUserAchievementEntity();
        final AchievementEntity achievementEntity = userAchievementEntity.getArtisanAchievementEntity();

        if (!achievementEntity.isCompleted()) {
            for (Material wood : woods) {
                if (!pw.getBlocksPlaced().contains(wood)) {
                    return;
                }
            }

            achievementEntity.setCompleted(true);
            userAchievementEntity.setArtisanAchievementEntity(achievementEntity);
            pw.setUserAchievementEntity(userAchievementEntity);
            ChatFunctions.achievement(p, "Artisan");
        }
    }
}

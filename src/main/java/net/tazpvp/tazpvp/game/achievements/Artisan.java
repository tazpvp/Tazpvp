package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.services.AchievementService;
import net.tazpvp.tazpvp.data.services.UserAchievementService;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class Artisan extends Observable {
    private final UserAchievementService userAchievementService = Tazpvp.getInstance().getUserAchievementService();
    private final AchievementService achievementService = Tazpvp.getInstance().getAchievementService();
    private final List<Material> woods = List.of(
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
        final UserAchievementEntity userAchievementEntity = userAchievementService.getOrDefault(p.getUniqueId());
        final AchievementEntity achievementEntity = userAchievementEntity.getArtisan();

        if (!achievementEntity.isCompleted()) {
            for (Material wood : woods) {
                if (!pw.getBlocksPlaced().contains(wood)) {
                    return;
                }
            }

            achievementEntity.setCompleted(true);
            achievementService.saveAchievementEntity(achievementEntity);
            ChatHelper.achievement(p, "Artisan");
        }
    }
}

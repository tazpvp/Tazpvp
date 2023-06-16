package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class Artisan extends Observable {
    @Override
    public void place(Player p, Block b) {
        if (!PersistentData.getAchievements(p.getUniqueId()).is("Artisan")) {
            PlayerWrapper pw = PlayerWrapper.getPlayer(p);
            List<Material> woods = List.of(
                    Material.OAK_PLANKS,
                    Material.SPRUCE_PLANKS,
                    Material.BIRCH_PLANKS,
                    Material.JUNGLE_PLANKS,
                    Material.ACACIA_PLANKS,
                    Material.DARK_OAK_PLANKS,
                    Material.CRIMSON_PLANKS,
                    Material.WARPED_PLANKS
            );
            for (Material wood : woods) {
                if (!pw.getBlocksPlaced().contains(wood)) {
                    return;
                }
            }
            Achievements ach = PersistentData.getAchievements(p.getUniqueId());
            ach.set("Artisan", true);
            PersistentData.setAchievements(p, ach);
        }
    }
}

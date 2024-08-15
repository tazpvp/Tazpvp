package net.tazpvp.tazpvp.enums;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum TalentEnum {
    REVENGE(Material.NETHERITE_SWORD, "Revenge", "Set the player who killed you on fire.", 8000),
    MOIST(Material.WATER_BUCKET, "Moist", "You can no longer be set on fire.", 9000),
    RESILIENT(Material.SHIELD, "Resilient", "Gain 2 absorption hearts on kill.", 12000),
    EXCAVATOR(Material.GOLDEN_PICKAXE, "Excavator", "Mining gives you experience.", 8000),
    ARCHITECT(Material.CRAFTING_TABLE, "Architect", "A chance to reclaim the block you placed.", 6000),
    HUNTER(Material.BOW, "Hunter", "A chance to reclaim the arrow you shot.", 8000),
    CANNIBAL(Material.ROTTEN_FLESH, "Cannibal", "Replenish your hunger on kill.", 9000),
    AGILE(Material.FEATHER, "Agile", "Gain a speed boost on kill.", 14000),
    HARVESTER(Material.SHEARS, "Harvester", "Better chance that players drop heads.", 11000),
    NECROMANCER(Material.NETHERITE_HOE, "Necromancer", "Double the items that drop from kills.", 15000),
    BLESSED(Material.GOLDEN_APPLE, "Blessed", "A chance of getting a golden apple from a kill.", 20000),
    GLIDE(Material.ELYTRA, "Glide", "The launch pad pushes you further.", 6000),
    PROFICIENT(Material.EXPERIENCE_BOTTLE, "Proficient", "Gain experience from duels.", 9000),
    MEDIC(Material.POTION, "Medic", "Heal nearby guild mates on kill.", 100000);

    final Material mat;
    final String name;
    final String lore;
    final int cost;

    TalentEnum(Material mat, String name, String lore, int cost) {
        this.mat = mat;
        this.name = name;
        this.lore = lore;
        this.cost = cost;
    }
}

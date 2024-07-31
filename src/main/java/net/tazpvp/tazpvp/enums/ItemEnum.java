package net.tazpvp.tazpvp.enums;

import lombok.Getter;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter
public enum ItemEnum {
    PREMIUM_PASS(Material.NETHER_STAR, "Premium Pass", "24 hours of the Premium rank.", 3),
    BOUNTY_HUNTER(Material.COMPASS, "Bounty Hunter", "Find the player with the highest bounty.", 3),
    AZURE_VAPOR(Material.BLUE_ORCHID, "Azure Vapor", "Extinguish flames.", 2),
    INKER(Material.INK_SAC, "Inker", "Blind the enemies you slap.", 2),
    STICKY_WEB(Material.COBWEB, "Sticky Web", "Slow down your enemies.", 2),
    PUSH_BOMB(Material.TNT, "Push Bomb", "Push everyone away from you.", 2),
    BLOCKS(Material.OAK_PLANKS, "Placeable Blocks", "Is this fortnite?", 2),

    GOLDEN_APPLE(Material.GOLDEN_APPLE, "Golden Apple", "Gain super powers?", 3),
    LIGHTER(Material.FLINT_AND_STEEL, "Lighter", "Set things afire.", 2),
    EXP_BOTTLE(Material.EXPERIENCE_BOTTLE, "Exp Bottle", "Mend your armor.", 1),
    HATCHET(Material.GOLDEN_AXE, "Hatchet", "Break wooden blocks.", 1),
    SHEAR(Material.SHEARS, "Shear", "Break wool blocks.", 1),
    ARROW(Material.ARROW, "Arrow", "Projectiles.", 1),
    GOLD_CARROT(Material.GOLDEN_CARROT, "Gold Carrot", "Good nutrition.", 2),
    SPECTRAL_ARROW(Material.SPECTRAL_ARROW, "Spectral Arrow", "Highlight targets.", 1),

    SHARPNESS(Enchantment.SHARPNESS, "Sharpness", "Deal more sword damage.", 3),
    PROTECTION(Enchantment.PROTECTION, "Protection", "Take less damage.", 2),
    POWER(Enchantment.POWER, "Power", "Deal more damage with your bow.", 3),
    FIRE_ASPECT(Enchantment.FIRE_ASPECT, "Fire Aspect", "Hit and set things on fire.", 2),
    UNBREAKING(Enchantment.UNBREAKING, "Unbreaking", "Fortify your tools.", 1),
    PROJECTILE_PROTECTION(Enchantment.PROJECTILE_PROTECTION, "Projectile Protection", "Take less projectile damage.", 1),
    FIRE_PROTECTION(Enchantment.FIRE_PROTECTION, "Fire Protection", "Take less damage to fire.", 2),
    SWEEPING_EDGE(Enchantment.SWEEPING_EDGE, "Sweeping Edge", "Increase attack range.", 2),
    MULTISHOT(Enchantment.MULTISHOT, "Multishot", "Shoot multiple crossbow arrows at a time.", 2),
    QUICK_CHARGE(Enchantment.QUICK_CHARGE, "Quick Charge", "Recharge crossbows faster.", 2),
    PUNCH(Enchantment.PUNCH, "Punch", "Shoot players back further.", 3),
    KNOCKBACK(Enchantment.KNOCKBACK, "Knockback", "Hit players back further.", 2),
    FLAME(Enchantment.FLAME, "Flame", "Shoot and set things on fire.", 1),
    EFFICIENCY(Enchantment.EFFICIENCY, "Efficiency", "Break blocks faster.", 2),
    DEPTH_STRIDER(Enchantment.DEPTH_STRIDER, "Depth Strider", "Walk faster in water.", 1);

    final Enchantment enchant;
    final Material material;
    final String name;
    final String lore;
    final int tier;

    private static final Random random = new Random();

    ItemEnum(Material material, String name, String lore, int tier) {
        this.enchant = Enchantment.MENDING;
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.tier= tier;
    }

    ItemEnum(Enchantment enchant, String name, String lore, int tier) {
        this.enchant = enchant;
        this.material = Material.ENCHANTED_BOOK;
        this.name = name;
        this.lore = lore;
        this.tier= tier;
    }

    public ItemStack getItem() {
        return ItemBuilder.of(material)
                .name(ChatHelper.gradient("", name,true))
                .lore(lore)
                .build();
    }

    public ItemStack getItem(Material mat) {
        return ItemBuilder.of(mat)
                .name(ChatHelper.gradient("", name,true))
                .lore(lore)
                .build();
    }

    public ItemStack getShopItem(int cost, int amount, boolean glow) {
        return ItemBuilder.of(material, amount)
                .name(ChatHelper.gradient("", name,true))
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: " + cost + " Coins")
                .glow(glow)
                .build();
    }

    public ItemStack getShopItem(Material mat, int cost, int amount, boolean glow) {
        return ItemBuilder.of(mat, amount)
                .name(ChatHelper.gradient("", name,true))
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: " + cost + " Coins")
                .glow(glow)
                .build();
    }

    public ItemStack getShopItem(Material mat, int cost, int amount, boolean glow, String extraLore) {
        return ItemBuilder.of(mat, amount)
                .name(ChatHelper.gradient("", name,true))
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: " + cost + " Coins", extraLore)
                .glow(glow)
                .build();
    }

    public ItemStack getShopEnchant(int cost, int amount) {
        return ItemBuilder.of(material, amount)
            .name(ChatHelper.gradient("#db3bff", name,true))
            .lore(
                    CC.GOLD + lore,
                    " ",
                    CC.GRAY + "Cost: " + cost + " Coins",
                    CC.RED + "Drag the enchant onto",
                    CC.RED + "an item to combine")
            .build();
    }

    public static ItemStack getRandomDrop() {
        List<ItemStack> drops = new ArrayList<>(getAllDrops());
        return (drops.get(random.nextInt(drops.size())));
    }

    public static ItemStack getRandomDrop(int selectedTier) {
        List<ItemStack> drops = new ArrayList<>(getAllDrops(selectedTier));
        return (drops.get(random.nextInt(drops.size())));
    }

    public static List<ItemStack> getAllDrops() {
        List<ItemStack> drops = new ArrayList<>();
        for (ItemEnum item : ItemEnum.values()) {
            drops.add(item.getItem());
        }
        return drops;
    }

    public static List<ItemStack> getAllDrops(int selectedTier) {
        List<ItemStack> drops = new ArrayList<>();
        for (ItemEnum item : ItemEnum.values()) {
            if (item.getTier() == selectedTier) {
                drops.add(item.getItem());
            }
        }
        return drops;
    }
}

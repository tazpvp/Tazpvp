package net.tazpvp.tazpvp.enums;

import lombok.Getter;
import net.tazpvp.tazpvp.helpers.ChatFunctions;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public enum ItemEnum {
    PREMIUM_PASS(Material.NETHER_STAR, "Premium Pass", "24 hours of the Premium rank.", false),
    BOUNTY_HUNTER(Material.COMPASS, "Bounty Hunter", "Find the player with the highest bounty.", false),
    AZURE_VAPOR(Material.BLUE_ORCHID, "Azure Vapor", "Extinguish flames.", false),
    INKER(Material.INK_SAC, "Inker", "Blind the enemies you slap.", false),
    STICKY_WEB(Material.COBWEB, "Sticky Web", "Slow down your enemies.", false),
    PUSH_BOMB(Material.TNT, "Push Bomb", "Push everyone away from you.", false),
    BLOCKS(Material.OAK_PLANKS, "Placeable Blocks", "Is this fortnite?", true),

    GOLDEN_APPLE(Material.GOLDEN_APPLE, "Golden Apple", "Gain super powers?", true),
    LIGHTER(Material.FLINT_AND_STEEL, "Lighter", "Set things afire.", false),
    EXP_BOTTLE(Material.EXPERIENCE_BOTTLE, "Exp Bottle", "Mend your armor.", true),
    HATCHET(Material.GOLDEN_AXE, "Hatchet", "Break wooden blocks.", false),
    SHEAR(Material.SHEARS, "Shear", "Break wool blocks.", false),
    ARROW(Material.ARROW, "Arrow", "Projectiles.", true),
    GOLD_CARROT(Material.GOLDEN_CARROT, "Gold Carrot", "Good nutrition.", true),
    SPECTRAL_ARROW(Material.SPECTRAL_ARROW, "Spectral Arrow", "Highlight targets.", true),

    SHARPNESS(Enchantment.SHARPNESS, "Sharpness", "Deal more sword damage.", true),
    PROTECTION(Enchantment.PROTECTION, "Protection", "Take less damage.", true),
    POWER(Enchantment.POWER, "Power", "Deal more damage with your bow.", true),
    MENDING(Enchantment.MENDING, "Mending", "Heal armor with xp bottles.", true),
    FIRE_ASPECT(Enchantment.FIRE_ASPECT, "Fire Aspect", "Hit and set things on fire.", true),
    UNBREAKING(Enchantment.UNBREAKING, "Unbreaking", "Fortify your tools.", true),
    PROJECTILE_PROTECTION(Enchantment.PROJECTILE_PROTECTION, "Projectile Protection", "Take less projectile damage.", true),
    FIRE_PROTECTION(Enchantment.FIRE_PROTECTION, "Fire Protection", "Take less damage to fire.", true),
    SWEEPING_EDGE(Enchantment.SWEEPING_EDGE, "Sweeping Edge", "Increase attack range.", true),
    MULTISHOT(Enchantment.MULTISHOT, "Multishot", "Shoot multiple crossbow arrows at a time.", true),
    QUICK_CHARGE(Enchantment.QUICK_CHARGE, "Quick Charge", "Recharge crossbows faster.", true),
    PUNCH(Enchantment.PUNCH, "Punch", "Shoot players back further.", true),
    KNOCKBACK(Enchantment.KNOCKBACK, "Knockback", "Hit players back further.", true),
    FLAME(Enchantment.FLAME, "Flame", "Shoot and set things on fire.", true),
    EFFICIENCY(Enchantment.EFFICIENCY, "Efficiency", "Break blocks faster.", true),
    INFINITY(Enchantment.INFINITY, "Infinity", "Infinite arrows.", true),
    DEPTH_STRIDER(Enchantment.DEPTH_STRIDER, "Depth Strider", "Walk faster in water.", true);

    final Enchantment enchant;
    final Material material;
    final String name;
    final String lore;
    final boolean drop;

    final Random random = new Random();

    ItemEnum(Material material, String name, String lore, boolean drop) {
        this.enchant = Enchantment.MENDING;
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.drop = drop;
    }

    ItemEnum(Enchantment enchant, String name, String lore, boolean drop) {
        this.enchant = enchant;
        this.material = Material.ENCHANTED_BOOK;
        this.name = name;
        this.lore = lore;
        this.drop = drop;
    }

    public ItemStack getItem() {
        return ItemBuilder.of(material)
                .name(ChatFunctions.gradient("", name,true))
                .lore(lore)
                .build();
    }

    public ItemStack getShopItem(int cost, int amount, boolean glow) {
        return ItemBuilder.of(material, amount)
                .name(ChatFunctions.gradient("", name,true))
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: " + cost + " Coins")
                .glow(glow)
                .build();
    }

    public ItemStack getShopEnchant(int cost, int amount) {
        return ItemBuilder.of(material, amount)
                .name(ChatFunctions.gradient("#db3bff", name,true))
                .lore(
                        CC.GOLD + lore,
                        " ",
                        CC.GRAY + "Cost: " + cost + " Coins",
                        CC.RED + "Drag the enchant onto",
                        CC.RED + "an item to combine")
                .build();
    }

    public ItemStack getRandomDrop() {
        List<ItemStack> drops = new ArrayList<>();
        for (ItemEnum item : ItemEnum.values()) {
            if (item.isDrop()) {
                drops.add(item.getItem());
            }
        }
        return (drops.get(random.nextInt(drops.size())));
    }
}

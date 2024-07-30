package net.tazpvp.tazpvp.utils.enums;

import lombok.Getter;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public enum Items {
    GAPPLE(Material.GOLDEN_APPLE, "", "", true, false),
    AZURE_VAPOR(Material.BLUE_ORCHID, "Azure Vapor", "Extinguish flames.", false, true),
    INKER(Material.INK_SAC, "Inker", "Blind the enemies you slap.", false, true),
    STICKY_WEB(Material.COBWEB, "Sticky Web", "Slow down your enemies.", false, true),
    LIGHTER(Material.FLINT_AND_STEEL, "Lighter", "Set things afire.", false, true),
    EXP_BOTTLE(Material.EXPERIENCE_BOTTLE, "Exp Bottle", "Mend your armor.", true, true),
    HATCHET(Material.GOLDEN_AXE, "Hatchet", "Break wooden blocks.", false, true),
    SHEAR(Material.SHEARS, "Shear", "Break wool blocks.", false, true),
    ARROW(Material.ARROW, "Arrow", "Projectiles.", true, true),
    GOLD_CARROT(Material.GOLDEN_CARROT, "Gold Carrot", "Good nutrition.", true, true),
    PUSH_BOMB(Material.TNT, "Push Bomb", "Push everyone away from you.", false, true),
    SPECTRAL_ARROW(Material.SPECTRAL_ARROW, "Spectral Arrow", "Highlight targets.", true, true),

    SHARPNESS(Material.ENCHANTED_BOOK, "Sharpness", "Deal more sword damage.", true, true),
    PROTECTION(Material.ENCHANTED_BOOK, "Protection", "Take less damage.", true, true),
    POWER(Material.ENCHANTED_BOOK, "Power", "Deal more damage with your bow.", true, true),
    MENDING(Material.ENCHANTED_BOOK, "Mending", "Heal armor with xp bottles.", true, true),
    FIRE_ASPECT(Material.ENCHANTED_BOOK, "Fire Aspect", "Hit and set things on fire.", true, true);

    final Material material;
    final String name;
    final String lore;
    final boolean drop;
    final boolean shop;

    final Random random = new Random();

    Items(Material material, String name, String lore, boolean drop, boolean shop) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.drop = drop;
        this.shop = shop;
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
        for (Items item : Items.values()) {
            if (item.isDrop()) {
                drops.add(item.getItem());
            }
        }
        return (drops.get(random.nextInt(drops.size())));
    }

    public List<ItemStack> getShopItems() {
        List<ItemStack> shopItems = new ArrayList<>();
        for (Items item : Items.values()) {
            if (item.isShop()) {
                shopItems.add(item.getItem());
            }
        }
        return shopItems;
    }
}

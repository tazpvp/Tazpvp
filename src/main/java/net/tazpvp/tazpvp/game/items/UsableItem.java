package net.tazpvp.tazpvp.game.items;

import lombok.Getter;
import lombok.Setter;
import net.tazpvp.tazpvp.game.items.usables.Inker;
import net.tazpvp.tazpvp.game.items.usables.AzureVapor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class UsableItem {

    public static final List<UsableItem> customItemList = new ArrayList<>();

    public final String name;
    public final String[] description;
    public final Material material;

    public UsableItem(String name, String[] description, Material material) {
        this.name = name;
        this.description = description;
        this.material = material;
    }

    public abstract void onRightClick(Player p, ItemStack item);
    public abstract void onLeftClick(Player p, ItemStack item);
    public abstract void onLeftClick(Player p, ItemStack item, Player target);
    public abstract void onLeftClick(Player p, ItemStack item, Block b);

    public static void registerCustomItems() {
        customItemList.add(new AzureVapor());
        customItemList.add(new Inker());
    }

    public static UsableItem getCustomItem(ItemStack item) {
        for (UsableItem customItem : UsableItem.customItemList) {
            if (customItem.getMaterial() == item.getType()) {
                return customItem;
            }
        }
        return null;
    }
}

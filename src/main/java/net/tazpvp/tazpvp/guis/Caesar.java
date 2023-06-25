package net.tazpvp.tazpvp.guis;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.BlockFunctions;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import net.tazpvp.tazpvp.utils.objects.Pickaxe;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class Caesar extends GUI {

    public Caesar(Player p) {
        super("Caesar", 3);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        fill(0, 27, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        ItemStack tool = BlockFunctions.getPickaxe(p);
        int shardCount = PlayerFunctions.countShards(p);

        int upgradeButtonSlot = 10;

        if (tool.getType() == Material.GOLDEN_PICKAXE) {
            sendNPCMessage(p, "&cCaesar", "You already have the best upgrade.");
            return;
        }

        Pickaxe currentPickaxe = BlockFunctions.pickaxes.stream()
                .filter(pickaxe -> pickaxe.getMat() == tool.getType())
                .findFirst()
                .orElse(null);

        if (currentPickaxe == null) {
            return;
        }

        int cost = currentPickaxe.getCost();

        ItemBuilder upgradeButtonBuilder = ItemBuilder.of(tool.getType(), 1)
                .name(CC.GREEN + "" + CC.BOLD + "Your Pickaxe")
                .flag(ItemFlag.HIDE_ATTRIBUTES);

        String[] lore = {CC.GRAY + "Click to upgrade its material.", "Cost: " + cost + " Shards"};
        upgradeButtonBuilder.lore(lore);

        ItemStack upgradeButton = upgradeButtonBuilder.build();

        addButton(Button.create(upgradeButton, (e) -> {
            if (shardCount < cost) {
                sendNPCMessage(p, "Caesar", "You do not have enough shards. You need " + (cost - shardCount) + " more shards.");
                return;
            }

            tool.setType(currentPickaxe.getUpgrade());
            PlayerFunctions.takeShards(p, cost);

            sendNPCMessage(p, "Caesar", "Thanks, here is your new pickaxe.");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);

            addItems(p);

            Tazpvp.getObservers().forEach(observer -> observer.talent(p));

            String[] lore2 = {CC.GRAY + "Click to upgrade its material.", "Cost: " + cost + " Shards"};
            upgradeButtonBuilder.lore(String.valueOf(lore2));
            upgradeButton.setItemMeta(upgradeButtonBuilder.build().getItemMeta());

            update();
        }), upgradeButtonSlot);

        update();
    }

    private void sendNPCMessage(Player player, String npcName, String message) {
        player.sendMessage(CC.DARK_GRAY + "[" + npcName + "] " + CC.GRAY + message);
    }
}

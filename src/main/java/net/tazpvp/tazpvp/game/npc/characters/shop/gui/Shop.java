package net.tazpvp.tazpvp.game.npc.characters.shop.gui;

import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.StatEnum;
import net.tazpvp.tazpvp.game.npc.characters.shop.gui.subgui.ItemShop;
import net.tazpvp.tazpvp.game.npc.characters.shop.gui.subgui.TalentShop;
import net.tazpvp.tazpvp.game.npc.characters.shop.gui.subgui.cosmetic.PremiumMenu;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class Shop extends GUI {


    public Shop(Player p) {
        super("Shop", 5);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        fill(0, 3*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        addButton(Button.create(ItemBuilder.of(Material.LECTERN, 1).name(CC.GREEN + "" + CC.BOLD + "Item Shop").lore(CC.GRAY + "Buy upgrades and battle gear").build(), (e) -> {
            new ItemShop(p);
        }), 10);

        addButton(Button.create(ItemBuilder.of(Material.LECTERN, 1).name(CC.GREEN + "" + CC.BOLD + "Talents").lore(CC.GRAY + "PvP and PvE perks" + CC.GRAY + "for the arena").build(), (e) -> {
            new TalentShop(p);
        }), 12);

        addButton(Button.create(ItemBuilder.of(Material.FIRE_CHARGE, 1).name(CC.GREEN + "" + CC.BOLD + "Premium").lore(CC.GRAY + "All of the premium", CC.GRAY + "and cosmetic features").build(), (e) -> {
            new PremiumMenu(p);
        }), 16);

        addButton(Button.create(ItemBuilder.of(Material.FIRE_CHARGE, 1).name(CC.GREEN + "" + CC.BOLD + "Trade In").lore(CC.GRAY + "Trade all of your player", CC.GRAY + "heads for coins.").build(), (e) -> {
            sellHead(p);
        }), 30);

        update();
    }

    public static void sellHead(Player p) {
        int reward = 0;
        for (ItemStack item : p.getInventory()) {
            if (item.getType().equals(Material.PLAYER_HEAD)) {
                reward = reward + 2000;
                item.setAmount(0);
            }
        }

        p.sendMessage(CC.GREEN + "[Shop]" + CC.WHITE + " Pleasure doing business." + CC.GOLD + " + $" + reward);
        p.playSound(p.getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 1);
        StatEnum.COINS.add(p.getUniqueId(), reward);
    }
}

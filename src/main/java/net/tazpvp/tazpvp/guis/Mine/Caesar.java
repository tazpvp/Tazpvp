package net.tazpvp.tazpvp.guis.Mine;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.DataTypes;
import net.tazpvp.tazpvp.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.BlockFunctions;
import net.tazpvp.tazpvp.utils.objects.Ore;
import net.tazpvp.tazpvp.utils.objects.Pickaxe;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;


public class Caesar extends GUI {

    public Caesar(Player p) {
        super("Caesar", 3);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        fill(0, 27, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        ItemStack tool = BlockFunctions.getPickaxe(p);
        int balance = PersistentData.getInt(p.getUniqueId(), DataTypes.COINS);

        Pickaxe currentPickaxe = BlockFunctions.pickaxes.stream()
                .filter(pickaxe -> pickaxe.getItem().getType() == tool.getType())
                .findFirst()
                .orElse(null);

        if (currentPickaxe == null) {
            return;
        }

        int cost = currentPickaxe.getCost();

        ItemBuilder upgradeButtonBuilder = ItemBuilder.of(tool.getType(), 1)
                .name(CC.YELLOW + "" + CC.BOLD + "Upgrade Pickaxe")
                .flag(ItemFlag.HIDE_ATTRIBUTES);

        String[] lore = {CC.GRAY + "Click to upgrade pickaxe material.", CC.GRAY + "Cost: " + CC.GOLD + "$" + cost};
        upgradeButtonBuilder.lore(lore);

        ItemStack upgradeButton = upgradeButtonBuilder.build();

        addButton(Button.create(upgradeButton, (e) -> {

            if (tool.getType() == Material.NETHERITE_PICKAXE) {
                sendNPCMessage(p, "You already have the best upgrade.");
                return;
            }

            if (balance < cost) {
                sendNPCMessage(p, "You do not have enough coins. You need " + (cost - balance) + " more coins.");
                return;
            }

            tool.setType(currentPickaxe.getUpgrade());
            PersistentData.remove(p.getUniqueId(), DataTypes.COINS, cost);

            sendNPCMessage(p, "Thanks, here is your new pickaxe.");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);

            addItems(p);

            Tazpvp.getObservers().forEach(observer -> observer.talent(p));

            String[] lore2 = {CC.GRAY + "Click to upgrade its material.", CC.GRAY + "Cost: " + CC.GOLD + "$" + cost};
            upgradeButtonBuilder.lore(lore2);
            upgradeButton.setItemMeta(upgradeButtonBuilder.build().getItemMeta());

            update();
        }), 11);

        addButton(Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Enchant Pickaxe").lore(CC.GRAY + "Check out the custom pickaxe enchantments.").build(), (e) -> {
            p.closeInventory();
            new Enchantments(p, tool);
        }), 13);

        addButton(Button.create(ItemBuilder.of(Material.NAME_TAG, 1).name(CC.GREEN + "" + CC.BOLD + "Sell Your Ores").lore(CC.GRAY + "Sell all of your ores.").build(), (e) -> {
            int reward = 0;
            for (ItemStack i : p.getInventory()) {
                if (i != null) {
                    Ore ore = BlockFunctions.getOreFrom(i.getType());
                    int amount = i.getAmount();
                    if (ore != null) {
                        reward += (BlockFunctions.isSmelted(i.getType())) ? ((ore.getCost() * 2) * amount) : (ore.getCost() * amount);

                        i.setAmount(0);
                    }
                }
            }
            if (reward == 0) {
                sendNPCMessage(p, "Are you trying to scam me!? You don't have any ores!" + reward);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            } else {
                PersistentData.add(p.getUniqueId(), DataTypes.COINS, reward);
                sendNPCMessage(p, "Great doing business!" + CC.GREEN + " + $" + reward);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                Tazpvp.getObservers().forEach(observer -> observer.gui(p, "Caesar"));
            }
        }), 15);

        update();
    }

    private void sendNPCMessage(Player player, String message) {
        player.sendMessage(CC.YELLOW + "[" + "Caesar" + "] " + CC.GOLD + message);
    }
}

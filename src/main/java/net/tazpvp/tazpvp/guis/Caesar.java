package net.tazpvp.tazpvp.guis;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.guis.guild.GuildBrowser;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.BlockFunctions;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import net.tazpvp.tazpvp.utils.objects.Pickaxe;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class Caesar extends GUI {

    private static final List<Player> doubleClick = new ArrayList<>();
    public Caesar(Player p) {
        super("Caesar", 3);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        fill(0, 3*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        ItemStack tool = BlockFunctions.getPickaxe(p);
        int shardCount = PlayerFunctions.countShards(p);

        addButton(Button.create(ItemBuilder.of(tool.getType(), 1).name(CC.GREEN + "" + CC.BOLD + "Your Pickaxe").lore(CC.GRAY + "Click to upgrade its material.").flag(ItemFlag.HIDE_ATTRIBUTES).build(), (e) -> {
            if (tool.getType().equals(Material.GOLDEN_PICKAXE)) {
                p.sendMessage("You already have the best upgrade.");
                return;
            }

            for (Pickaxe pickaxe : BlockFunctions.pickaxes) {
                if (pickaxe.getMat().equals(tool.getType())) {
                    int cost = pickaxe.getCost();
                    if (shardCount >= cost) {
                        if (doubleClick.contains(p)) {

                            tool.setType(pickaxe.getUpgrade());

                            PlayerFunctions.takeShards(p, cost);
                            doubleClick.remove(p);

                            p.closeInventory();
                            p.sendMessage("Thanks, here is your new pickaxe.");
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);

                            new Caesar(p);

                            Tazpvp.getObservers().forEach(observer -> observer.talent(p));

                        } else {

                            p.sendMessage("Are you sure you want to upgrade your pickaxe for " + cost + " Shards?");

                            doubleClick.add(p);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    doubleClick.remove(p);
                                }
                            }.runTaskLater(Tazpvp.getInstance(), 20*5);
                        }
                    } else {
                        p.sendMessage("You do not have enough shards. You need " + (cost - shardCount) + " more shards.");
                    }
                }
            }
        }), 10);

        update();
    }
}

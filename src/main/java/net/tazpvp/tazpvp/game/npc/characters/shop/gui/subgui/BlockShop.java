package net.tazpvp.tazpvp.game.npc.characters.shop.gui.subgui;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.entity.UserRankEntity;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.data.services.UserRankService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.ItemEnum;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.List;

public class BlockShop extends GUI {

    private int slotNum;
    private int count;
    private final Player p;
    private final String prefix = CC.RED + "[Maxim] " + CC.WHITE;
    private final PlayerStatEntity playerStatEntity;

    private final List<Material> wool = List.of(
            Material.ORANGE_WOOL,
            Material.PURPLE_WOOL,
            Material.YELLOW_WOOL,
            Material.LIME_WOOL,
            Material.CYAN_WOOL,
            Material.RED_WOOL,
            Material.BLACK_WOOL
    );

    private final List<Material> wood = List.of(
            Material.OAK_PLANKS,
            Material.CHERRY_PLANKS,
            Material.BIRCH_PLANKS,
            Material.MANGROVE_PLANKS,
            Material.DARK_OAK_PLANKS,
            Material.JUNGLE_PLANKS,
            Material.SPRUCE_PLANKS
    );

    public BlockShop(Player p, PlayerStatService playerStatService) {
        super("Block Shop", 4);
        this.p = p;
        this.playerStatEntity = playerStatService.getOrDefault(p.getUniqueId());
        addItems();
        open(p);
    }

    private void addItems() {
        slotNum = 10;
        count = 1;
        fill(0, 6*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        for (Material mat : wood) {
            addDefault(mat);
        }
        for (Material mat : wool) {
            addPremium(mat, CC.GREEN + "Premium pass required.");
        }

        update();
    }

    private void addDefault(Material material) {
        ItemStack item = ItemEnum.BLOCKS.getShopItem(material, 64, 64, false);

        addButton(Button.create(item, (_) -> checkMoney(material, 64, false)), slotNum);
        calcSlot();
    }

    private void addPremium(Material material, String lore) {
        ItemStack item = ItemEnum.BLOCKS.getShopItem(material, 120, 64, false, lore);

        addButton(Button.create(item, (_) -> checkMoney(material, 120, true)), slotNum);
        calcSlot();
    }

    private void checkMoney(Material mat, int cost, boolean premium) {
        if (playerStatEntity != null) {
            if (premium) {
                UserRankService userRankService = Tazpvp.getInstance().getUserRankService();
                UserRankEntity userRankEntity = userRankService.getUserRankEntity(p.getUniqueId());
                List<String> userPermissions = userRankService.getPermissions(userRankEntity);

                if (!userPermissions.contains("tazpvp.premium")) {
                    p.sendMessage(CC.RED + "You require the premium pass to use this feature.");
                    return;
                }
            }
            if (playerStatEntity.getCoins() >= cost) {
                playerStatEntity.setCoins(playerStatEntity.getCoins() - cost);
                p.getInventory().addItem(ItemEnum.BLOCKS.getItem(mat, 64));
                p.sendMessage(prefix + "You purchased: " + ItemEnum.BLOCKS.getName());
                p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1, 1);
            } else {
                p.sendMessage(prefix + "You don't have enough money");
            }
        }
    }

    public void calcSlot() {
        if (count % 7 == 0) {
            slotNum += 2;
            count = 0;
        }
        slotNum ++;
        count++;
    }
}

package net.tazpvp.tazpvp.game.npc.characters.shop.gui.subgui;

import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.data.implementations.TalentServiceImpl;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.data.services.TalentService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.ItemEnum;
import net.tazpvp.tazpvp.enums.ScoreboardEnum;
import net.tazpvp.tazpvp.enums.StatEnum;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.helpers.PlayerHelper;
import net.tazpvp.tazpvp.helpers.ScoreboardHelper;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.UUID;

public class PrestigeShop extends GUI {
    private int slotNum;
    private int num;
    private Player p;
    private final UUID id;
    private final String prefix = CC.RED + "[Maxim] " + CC.WHITE;

    public PrestigeShop(Player p) {
        super("Maxim", 5);
        this.p = p;
        this.id = p.getUniqueId();
        addItems();
        open(p);
    }

    private void addItems() {
        slotNum = 19;
        num = 1;

        fill(0, 5*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        addButton(Button.create(ItemBuilder.of(Material.ENCHANTING_TABLE, 1)
                .name(ChatHelper.gradient("#eb0fff", "Rebirth", true))
                .lore(CC.GRAY + "Reset your stats ", CC.GRAY + "and gain buffs.",
                        "", CC.GRAY + "- 25% more EXP gain.",
                        CC.GRAY + "- 25% more coins.",
                        CC.GRAY + "- Access to rebirth features.",
                        "",
                        CC.DARK_PURPLE + "Requirement: " + CC.LIGHT_PURPLE + "100 Levels",
                        CC.YELLOW + "(Click to Rebirth)"
                )
                .glow(true)
                .build(), (e) -> {
            if (StatEnum.LEVEL.getInt(id) < 100) {
                p.sendMessage(prefix + "You are not a high enough level.");
                return;
            }

            StatEnum.PRESTIGE.add(id, 1);

            PlayerWrapper pw = PlayerWrapper.getPlayer(p);

            TalentEntity talentEntity = pw.getTalentEntity();
            TalentService talentService = new TalentServiceImpl();
            talentService.removeTalentEntity(talentEntity);
            pw.setTalentEntity(talentService.getOrDefault(p.getUniqueId()));

            StatEnum.COINS.set(id, 0);
            StatEnum.LEVEL.set(id, 0);
            StatEnum.XP.set(id, 0);

            p.getEnderChest().clear();
            p.getInventory().clear();
            PlayerHelper.kitPlayer(p);

            p.closeInventory();
            p.sendTitle(CC.LIGHT_PURPLE + "" + CC.BOLD + "REBIRTH", CC.DARK_PURPLE + "You are reborn anew", 20, 40, 20);
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
        }), 13);

        addBuyButton(100000, true, ItemEnum.PREMIUM_PASS);
        addBuyButton(1600, true, ItemEnum.BOUNTY_HUNTER);

        update();
    }

    private void addBuyButton(int cost, boolean glow, ItemEnum customItem) {
        ItemStack item = customItem.getShopItem(cost, 1, glow);
        addButton(Button.create(item, (e) -> {
            checkMoney(cost, customItem);
        }), slotNum);
        calcSlot();
    }

    private void checkMoney(int cost, ItemEnum item) {
        if (StatEnum.COINS.getInt(id) >= cost) {
            StatEnum.COINS.remove(id, cost);
            p.getInventory().addItem(item.getItem(1));
            p.sendMessage(prefix + "You purchased: " + item.getName());
            p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1, 1);
        } else {
            p.sendMessage(prefix + "You don't have enough money");
        }
    }

    public void calcSlot() {
        if (num % 7 == 0) {
            slotNum += 2;
            num = 0;
        }
        slotNum ++;
        num ++;
    }
}

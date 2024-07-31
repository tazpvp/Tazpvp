package net.tazpvp.tazpvp.npc.characters.shop.gui.subgui;

import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.data.implementations.TalentServiceImpl;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.data.services.TalentService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.ChatFunctions;
import net.tazpvp.tazpvp.helpers.PlayerFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.EnchantmentBookBuilder;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import javax.annotation.Nullable;

public class PrestigeShop extends GUI {
    private int slotNum;
    private int num;
    private Player p;
    private final String prefix = CC.RED + "[Maxim] " + CC.WHITE;
    private final PlayerStatService playerStatService;
    private final PlayerStatEntity playerStatEntity;

    public PrestigeShop(Player p, PlayerStatService playerStatService) {
        super("Maxim", 5);
        this.p = p;
        this.playerStatService = playerStatService;
        this.playerStatEntity = playerStatService.getOrDefault(p.getUniqueId());
        addItems();
        open(p);
    }

    private void addItems() {
        slotNum = 19;
        num = 1;

        fill(0, 5*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        addButton(Button.create(ItemBuilder.of(Material.ENCHANTING_TABLE, 1)
                .name(ChatFunctions.gradient("#eb0fff", "Rebirth", true))
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
            if (playerStatEntity.getLevel() < 100) {
                p.sendMessage(prefix + "You are not a high enough level.");
                return;
            }

            playerStatEntity.setPrestige(playerStatEntity.getPrestige() + 1);

            PlayerWrapper pw = PlayerWrapper.getPlayer(p);

            TalentEntity talentEntity = pw.getTalentEntity();
            TalentService talentService = new TalentServiceImpl();
            talentService.removeTalentEntity(talentEntity);
            pw.setTalentEntity(talentService.getOrDefault(p.getUniqueId()));

            playerStatEntity.setCoins(0);
            playerStatEntity.setLevel(0);
            playerStatEntity.setXp(0);

            p.getEnderChest().clear();
            p.getInventory().clear();
            PlayerFunctions.kitPlayer(p);

            p.closeInventory();
            p.sendTitle(CC.LIGHT_PURPLE + "" + CC.BOLD + "REBIRTH", CC.DARK_PURPLE + "You are reborn anew", 20, 40, 20);
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
        }), 13);

        setButton("Premium Pass", "24 hours of the Premium rank.", Material.NETHER_STAR, 100000, true, true);
        setButton("Bounty Hunter", "Find the player with the highest bounty.", Material.COMPASS, 1600, true, true);


        update();
    }

    private void setButton(String name, String lore, Material material, int cost, boolean glow, boolean custom) {
        String name2 = ChatFunctions.gradient("#db3bff", name, true);
        ItemStack item;
        if (custom) {
            item = ItemBuilder.of(material, 1).name(name2).lore(CC.GRAY + lore).build();
        } else {
            item = ItemBuilder.of(material, 1).build();
        }

        addButton(Button.create(ItemBuilder.of(material, 1)
                .name(CC.YELLOW + "" + CC.BOLD + name)
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: " + cost + " Coins")
                .glow(glow)
                .build(), (e) -> {
            if (playerStatEntity.getPrestige() < 1) {
                p.sendMessage(prefix + "You need to rebirth before buying this.");
                return;
            }
            checkMoney(name2, cost, item, null);
        }), slotNum);
        calcSlot();
    }

    private void setButton(String name, String lore, int cost, Enchantment enchant) {
        String name2 = ChatFunctions.gradient("#db3bff", name, true);

        addButton(Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1)
                .name(CC.YELLOW + "" + CC.BOLD + name)
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: " + cost + " Coins", CC.RED + "Drag the enchant onto", CC.RED + "an item to combine")
                .build(), (e) -> {
            if (playerStatEntity.getPrestige() < 1) {
                p.sendMessage(prefix + "You need to rebirth before buying this.");
                return;
            }
            checkMoney(name2, cost, ItemBuilder.of(Material.ENCHANTED_BOOK, 1).build(), enchant);
        }), slotNum);
        calcSlot();
    }

    private void checkMoney(String name, int cost, ItemStack item, @Nullable Enchantment enchantment) {
        if (playerStatEntity.getCoins() >= cost) {
            playerStatEntity.setCoins(playerStatEntity.getCoins() - cost);
            if (enchantment == null) {
                p.getInventory().addItem(item);
            } else {
                p.getInventory().addItem(new EnchantmentBookBuilder().enchantment(enchantment, 1).build());
            }
            p.sendMessage(prefix + "You purchased: " + name);
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

package net.tazpvp.tazpvp.guis.Shop;

import net.tazpvp.tazpvp.data.DataTypes;
import net.tazpvp.tazpvp.data.PersistentData;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.implementations.TalentServiceImpl;
import net.tazpvp.tazpvp.data.services.TalentService;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.EnderChest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.EnchantmentBookBuilder;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import javax.annotation.Nullable;

public class Prestige extends GUI {
    private int slotNum;
    private int num;
    private Player p;
    private final String prefix = CC.RED + "[Maxim] " + CC.WHITE;

    public Prestige(Player p) {
        super("Maxim", 5);
        this.p = p;
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
            if (PersistentData.getInt(p.getUniqueId(), DataTypes.LEVEL) < 100) {
                p.sendMessage(prefix + "You are not a high enough level.");
                return;
            }

            int rebirthLevel = PersistentData.getInt(p.getUniqueId(), DataTypes.REBIRTH);
            PersistentData.set(p.getUniqueId(), DataTypes.REBIRTH, rebirthLevel + 1);

            PlayerWrapper pw = PlayerWrapper.getPlayer(p);

            TalentEntity talentEntity = pw.getTalentEntity();
            TalentService talentService = new TalentServiceImpl();
            talentService.removeTalentEntity(talentEntity);
            pw.setTalentEntity(talentService.getOrDefault(p.getUniqueId()));

            PersistentData.set(p.getUniqueId(), DataTypes.COINS, 0);
            PersistentData.set(p.getUniqueId(), DataTypes.LEVEL, 0);
            PersistentData.set(p.getUniqueId(), DataTypes.XP, 0);

            p.getEnderChest().clear();
            p.getInventory().clear();
            PlayerFunctions.kitPlayer(p);

            p.closeInventory();
            p.sendTitle(CC.LIGHT_PURPLE + "" + CC.BOLD + "REBIRTH", CC.DARK_PURPLE + "You are reborn anew", 20, 40, 20);
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
        }), 13);

        setButton(1, "Premium Pass", "24 hours of the Premium rank.", Material.NETHER_STAR, 100000, true, true);
        setButton(1, "Bounty Hunter", "Find the player with the highest bounty.", Material.COMPASS, 1600, true, true);
        setButton(1, "Unbreaking", "Fortify your tools.", Material.ENCHANTED_BOOK, 160, Enchantment.DURABILITY);
        setButton(1, "Projectile Protection", "Take less projectile damage.", Material.ENCHANTED_BOOK, 225, Enchantment.PROTECTION_PROJECTILE);
        setButton(1, "Fire Protection", "Take less damage to fire.", Material.ENCHANTED_BOOK, 225, Enchantment.PROTECTION_FIRE);
        setButton(1, "Sweeping Edge", "Increase attack range.", Material.ENCHANTED_BOOK, 220, Enchantment.SWEEPING_EDGE);
        setButton(1, "Multishot", "Shoot multiple crossbow arrows at time.", Material.ENCHANTED_BOOK, 650, Enchantment.MULTISHOT);

        setButton(1, "Quick Charge", "Recharge crossbows faster.", Material.ENCHANTED_BOOK, 550, Enchantment.QUICK_CHARGE);
        setButton(1, "Punch", "Shoot players back further.", Material.ENCHANTED_BOOK, 350, Enchantment.ARROW_KNOCKBACK);
        setButton(1, "Knockback", "Hit players back further.", Material.ENCHANTED_BOOK, 275, Enchantment.KNOCKBACK);
        setButton(1, "Flame", "Shoot and set things on fire.", Material.ENCHANTED_BOOK, 450, Enchantment.ARROW_FIRE);
        setButton(1, "Efficiency", "Break blocks faster.", Material.ENCHANTED_BOOK, 350, Enchantment.DIG_SPEED);
        setButton(1, "Infinity", "Infinite arrows.", Material.ENCHANTED_BOOK, 900, Enchantment.ARROW_INFINITE);
        setButton(1, "Depth Strider", "Walk faster in water.", Material.ENCHANTED_BOOK, 250, Enchantment.DEPTH_STRIDER);

        update();
    }

    private void setButton(int amount, String name, String lore, Material material, int cost, boolean glow, boolean custom) {
        String name2 = ChatFunctions.gradient("#db3bff", name, true);
        ItemStack item;
        if (custom) {
            item = ItemBuilder.of(material, amount).name(name2).lore(CC.GRAY + lore).build();
        } else {
            item = ItemBuilder.of(material, amount).build();
        }

        addButton(Button.create(ItemBuilder.of(material, amount)
                .name(CC.YELLOW + "" + CC.BOLD + name)
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: " + cost + " Coins")
                .glow(glow)
                .build(), (e) -> {
            if (PersistentData.getInt(p.getUniqueId(), DataTypes.REBIRTH) < 1) {
                p.sendMessage(prefix + "You need to rebirth before buying this.");
                return;
            }
            checkMoney(name2, cost, item, null);
        }), slotNum);
        calcSlot();
    }

    private void setButton(int amount, String name, String lore, Material material, int cost, Enchantment enchant) {
        String name2 = ChatFunctions.gradient("#db3bff", name, true);

        addButton(Button.create(ItemBuilder.of(material, amount)
                .name(CC.YELLOW + "" + CC.BOLD + name)
                .lore(CC.GOLD + lore, " ", CC.GRAY + "Cost: " + cost + " Coins", CC.RED + "Drag the enchant onto", CC.RED + "an item to combine")
                .build(), (e) -> {
            if (PersistentData.getInt(p.getUniqueId(), DataTypes.REBIRTH) < 1) {
                p.sendMessage(prefix + "You need to rebirth before buying this.");
                return;
            }
            checkMoney(name2, cost, ItemBuilder.of(material, amount).build(), enchant);
        }), slotNum);
        calcSlot();
    }

    private void checkMoney(String name, int cost, ItemStack item, @Nullable Enchantment enchantment) {
        if (PersistentData.getInt(p, DataTypes.COINS) >= cost) {
            PersistentData.remove(p, DataTypes.COINS, cost);
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

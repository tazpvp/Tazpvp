package net.tazpvp.tazpvp.guis.Shop;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.EnchantmentBookBuilder;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

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
                        CC.DARK_PURPLE + "Requirement: " + CC.LIGHT_PURPLE + "100 Levels"
                )
                .glow(true)
                .build(), (e) -> {
            if (PersistentData.getInt(p.getUniqueId(), DataTypes.LEVEL) < 100) {
                p.sendMessage(prefix + "You are not a high enough level.");
                return;
            }
            p.sendMessage(prefix + "Currently out of order.");
//            int rebirthLevel = PersistentData.getInt(p.getUniqueId(), DataTypes.PRESTIGE);
//            PersistentData.set(p.getUniqueId(), DataTypes.PRESTIGE, rebirthLevel + 1);
//            PersistentData.set(p.getUniqueId(), DataTypes.COINS, 0);
        }), 13);

        setButton(1, "Premium Pass", "24 hours of the Premium rank.", Material.NETHER_STAR, 100000, true, true);
        setButton(1, "Sharpness", "Deal more sword damage.", Material.ENCHANTED_BOOK, 230, Enchantment.DAMAGE_ALL);
        setButton(1, "Unbreaking", "Fortify your tools.", Material.ENCHANTED_BOOK, 160, Enchantment.DURABILITY);
        setButton(1, "Protection", "Take less damage.", Material.ENCHANTED_BOOK, 375, Enchantment.PROTECTION_ENVIRONMENTAL);
        setButton(1, "Projectile Protection", "Take less projectile damage.", Material.ENCHANTED_BOOK, 225, Enchantment.PROTECTION_PROJECTILE);
        setButton(1, "Fire Protection", "Take less damage to fire.", Material.ENCHANTED_BOOK, 225, Enchantment.PROTECTION_FIRE);
        setButton(1, "Sweeping Edge", "Increase attack range.", Material.ENCHANTED_BOOK, 220, Enchantment.SWEEPING_EDGE);

        setButton(1, "Punch", "Shoot players back further.", Material.ENCHANTED_BOOK, 350, Enchantment.ARROW_KNOCKBACK);
        setButton(1, "Knockback", "Hit players back further.", Material.ENCHANTED_BOOK, 275, Enchantment.KNOCKBACK);
        setButton(1, "Flame", "Shoot and set things on fire.", Material.ENCHANTED_BOOK, 450, Enchantment.ARROW_FIRE);
        setButton(1, "Fire Aspect", "Hit and set things on fire.", Material.ENCHANTED_BOOK, 450, Enchantment.FIRE_ASPECT);
        setButton(1, "Mending", "Heal armor with xp bottles.", Material.ENCHANTED_BOOK, 100, Enchantment.MENDING);
        setButton(1, "Power", "Deal more damage with your bow.", Material.ENCHANTED_BOOK, 600, Enchantment.ARROW_DAMAGE);

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
            if (PersistentData.getInt(p.getUniqueId(), DataTypes.PRESTIGE) < 1) {
                p.sendMessage(prefix + "You need to prestige before buying this.");
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
            if (PersistentData.getInt(p.getUniqueId(), DataTypes.PRESTIGE) < 1) {
                p.sendMessage(prefix + "You need to prestige before buying this.");
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

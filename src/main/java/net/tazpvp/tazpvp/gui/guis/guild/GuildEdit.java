package net.tazpvp.tazpvp.gui.guis.guild;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.guild.Guild;
import net.tazpvp.tazpvp.utils.Profanity;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.List;

public class GuildEdit extends GUI {
    public GuildEdit(Player p, Guild g) {
        super(CC.GOLD + "Edit Guild", 3);
        addItems(p, g);
        open(p);
    }

    private void addItems(Player p, Guild g) {
        fill(0, 3 * 9, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).name(" ").build());

        // Guild Icon
        // Guild Description
        // Guild Tag

        // Show Guild in Browser


        Button guildIcon = Button.create(ItemBuilder.of(g.getIcon()).build(), e -> {
            new Icon(p, g);
        });

        Button guildDescription = Button.create(ItemBuilder.of(Material.OAK_SIGN).name("Change Discription").lore("cost 6k coins yo yo").build(), e -> {
            if (PersistentData.getInt(p, DataTypes.COINS) > 6000) {
                setDescription(p, g);
            } else {
                p.sendMessage("broke ass");
            }
        });

        Button guildTag = Button.create(ItemBuilder.of(Material.NAME_TAG).name("Change Tag").lore("need a rank ;)").build(), e -> {
            if (PersistentData.getInt(p, DataTypes.COINS) > 6000) {
                setTag(p, g);
            } else {
                p.sendMessage("no rank broke ass");
            }
        });

        String showinbrowserTexxt = (g.isShow_in_browser() ? "Enabled" : "Disabled");
        Button setShowInBrowser = Button.create(ItemBuilder.of(Material.REDSTONE_TORCH).name("Show in browser").lore(showinbrowserTexxt).build(), e -> {
           g.setShow_in_browser(p.getUniqueId(), !g.isShow_in_browser());
           p.closeInventory();
           p.sendMessage((g.isShow_in_browser() ? "Enabled" : "Disabled"));
           new GuildEdit(p, g);
        });

        addButton(guildIcon, 11);
        addButton(guildDescription, 12);
        addButton(guildTag, 13);
        addButton(setShowInBrowser, 15);

        update();
    }

    public static class Icon extends GUI {
        private List<Material> icons = List.of(
                Material.OAK_SIGN, Material.RAW_GOLD, Material.CHORUS_FLOWER, Material.ENCHANTING_TABLE, Material.BEACON, Material.END_PORTAL_FRAME, Material.TURTLE_EGG, Material.WITHER_ROSE, Material.DIRT, Material.TARGET, Material.DIAMOND, Material.EMERALD, Material.LAPIS_LAZULI, Material.AMETHYST_SHARD, Material.GOLDEN_APPLE, Material.NETHERITE_CHESTPLATE, Material.CARROT_ON_A_STICK, Material.LEATHER, Material.LAVA_BUCKET, Material.ENDER_PEARL, Material.ENDER_EYE, Material.SLIME_BALL, Material.GLOW_INK_SAC, Material.PUFFERFISH, Material.MAGMA_CREAM, Material.BLAZE_POWDER, Material.FIRE_CHARGE, Material.DIAMOND_HORSE_ARMOR
        );
        public Icon(Player p, Guild g) {
            super("Guild Icon Picker", 6);
            addItems(p, g);
            p.closeInventory();
            open(p);
        }

        private void addItems(Player p, Guild g) {
            fill(0, 6 * 9, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).name(" ").build());

            int index = 10;
            for (Material m : icons) {
                Button iconBTN = Button.create(ItemBuilder.of(m).name(ChatColor.YELLOW + m.name()).lore(ChatColor.GREEN + "Click to set icon", ChatColor.DARK_AQUA + "Cost: " + ChatColor.AQUA + "50 credits").build(), (e) -> {
                    if (true) { // check if ranked
                        g.setIcon(p.getUniqueId(), m);
                        p.closeInventory();
                        g.sendAll(ChatColor.YELLOW + p.getName() + ChatColor.GREEN + " has set the guild icon to " + ChatColor.YELLOW + m.name());
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                new GuildEdit(p, g);
                            }
                        }.runTaskLater(Tazpvp.getInstance(), 1);

                    } else {
                        p.sendMessage(ChatColor.RED + "No rank broke ass");
                        p.closeInventory();
                    }
                });

                addButton(iconBTN, index);

                if (index == 16 || index == 25 || index == 34 || index == 43) {
                    index += 2;
                }
                index++;
            }
            update();
        }
    }

    private void setDescription(Player p, Guild g) {
        new AnvilGUI.Builder()
                .onComplete((player, text) -> {
                    if (text.startsWith(">")) {
                        text = text.replaceFirst(">", "");
                    }


                    String description = Profanity.censor(text);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

                    PersistentData.remove(p, DataTypes.COINS, 6000);

                    g.setDescription(p.getUniqueId(), description);

                    return AnvilGUI.Response.close();
                })
                .onClose(player -> {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                })
                .text(">")
                .itemLeft(ItemBuilder.of(Material.NAME_TAG).name(ChatColor.GREEN + "Guild Description").build())
                .title(ChatColor.YELLOW + "Guild Description:")
                .plugin(Tazpvp.getInstance())
                .open(p);
    }

    private void setTag(Player p, Guild g) {
        new AnvilGUI.Builder()
                .onComplete((player, text) -> {
                    if (text.startsWith(">")) {
                        text = text.replaceFirst(">", "");
                    }

                    if (!(text.length() < 8)) {
                        p.sendMessage("too long fatass");
                        return AnvilGUI.Response.close();
                    }

                    String tag = Profanity.censor(text);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

                    g.setTag(p.getUniqueId(), tag);

                    return AnvilGUI.Response.close();
                })
                .onClose(player -> {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                })
                .text(">")
                .itemLeft(ItemBuilder.of(Material.NAME_TAG).name(ChatColor.GREEN + "Guild Tag < 8").build())
                .title(ChatColor.YELLOW + "Guild Tag < 8:")
                .plugin(Tazpvp.getInstance())
                .open(p);
    }
}

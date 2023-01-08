package net.tazpvp.tazpvp.gui.guis.guild;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.guild.Guild;
import net.tazpvp.tazpvp.guild.GuildUtils;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.GuildData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.LinkedHashMap;
import java.util.UUID;

public class GuildBrowser extends GUI {
    private int pagesNeeded;
    private int numNum;
    public GuildBrowser(Player p) {
        super(CC.DARK_GREEN + "Guild Browser", 5);
        this.pagesNeeded = (int) Math.ceil(GuildUtils.getSortedGuilds().size() / (double) (4 * 7));
        pageChange(p, 0);
        open(p);
    }

    public void pageChange(Player p, int page) {
        defaults(p);
        LinkedHashMap<UUID, Integer> guilds = GuildUtils.getSortedGuilds();

        int displacement = 10;
        for(int i = (page * numNum); i < Math.min(numNum + (page*numNum), guilds.size()); i++) {
            Guild g = GuildData.getGuild(UUID.fromString(String.valueOf(guilds.keySet().toArray()[i])));
            String tag = (g.getTag() == null) ? "" : CC.YELLOW + " [" + g.getTag() + "]";
            CC color = CC.WHITE;
            if (i == 0) color = CC.GOLD;
            else if (i == 1) color = CC.GRAY;
            else if (i == 2) color = CC.DARK_RED;

            Button guildView = Button.create(ItemBuilder.of(g.getIcon()).name(color + g.getName() + tag).lore(
                    "",
                    CC.WHITE + g.getDescription(),
                    ChatColor.GRAY + "-" + Bukkit.getOfflinePlayer(g.getGuild_leader()).getName(),
                    "",
                    CC.GRAY + "Members: " + CC.WHITE + g.getAllMembers().length,
                    CC.GRAY + "Kills: " + CC.WHITE + (int) g.getKills()
            ).build(), (e) -> {});

            addButton(guildView, displacement);

            if((i+1) % 7 == 0) {
                displacement += 2;
            }
            displacement++;
        }

        if (page != 0) {
            Button lastPage = Button.create(ItemBuilder.of(Material.ARROW).name(CC.GREEN + "Last Page").build(), (e) -> {
                if((page - 1) * numNum >= 0) pageChange(p, page - 1);
            });
            addButton(lastPage, 18);
        }

        if (page != pagesNeeded - 1) {
            Button nextPage = Button.create(ItemBuilder.of(Material.ARROW).name(CC.GREEN + "Next Page").build(), (e) -> {
                if((page + 1) * numNum < guilds.size()) pageChange(p, page + 1);
            });

            addButton(nextPage, 26);
        }

        update();
    }

    private void defaults(Player p) {
        clear();
        fill(0, 5 * 9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).name(" ").build());

        Button playerGuild;
        if (GuildUtils.isInGuild(p)) {
            Guild g = GuildUtils.getGuildPlayerIn(p);
            playerGuild = Button.create(ItemBuilder.of(g.getIcon())
                    .name(CC.GREEN + "" + CC.BOLD + g.getName())
                    .lore(CC.DARK_GREEN + "View your guild.")
            .build(), (e) -> {
                p.closeInventory();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new GuildInfo(p, g);
                    }
                }.runTaskLater(Tazpvp.getInstance(), 2L);
            });
        } else {
            playerGuild = Button.create(ItemBuilder.of(Material.MINECART)
                    .name(CC.GREEN + "" + CC.BOLD + "Purchase Guild")
                    .lore(CC.DARK_GREEN + "Click to buy a guild.", " ", CC.GRAY + "Cost: $6,000")
            .build(), (e) -> {
                p.closeInventory();
                if (PersistentData.getInt(p, DataTypes.COINS) >= 6000) {
                    nameGuild(p);
                } else {
                    p.sendMessage("You don't have enough money");
                }
            });
        }
        addButton(playerGuild, 4);

        update();
    }

    private static void nameGuild(Player p) {
        new AnvilGUI.Builder()
            .onComplete((player, text) -> {
                if (text.startsWith(">")) {
                    text = text.replaceFirst(">", "");
                }
                PersistentData.remove(p, DataTypes.COINS, 6000);
                p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1, 1);

                createGuild(text, p.getUniqueId());
                return AnvilGUI.Response.close();
            })
            .onClose(player -> {
                p.sendMessage("Guild creation cancelled.");
            })
            .text(">")
            .itemLeft(ItemBuilder.of(Material.NAME_TAG).build())
            .title("Name your guild")
            .plugin(Tazpvp.getInstance())
            .open(p);

    }

    public static void createGuild(String text, UUID id) {
        new Guild(text, id);
    }

}

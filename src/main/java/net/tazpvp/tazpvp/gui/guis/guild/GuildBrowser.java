package net.tazpvp.tazpvp.gui.guis.guild;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.guild.Guild;
import net.tazpvp.tazpvp.guild.GuildUtils;
import net.tazpvp.tazpvp.utils.data.GuildData;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
        super(CC.GOLD + "Guild Browser", 5);
        this.pagesNeeded = (int) Math.ceil(GuildUtils.getSortedGuilds().size() / (double) (4 * 7));
        pageChange(p, 0);
        open(p);
    }

    public void pageChange(Player p, int page) {
        defaults(p);
        LinkedHashMap<UUID, Integer> guilds = GuildUtils.getSortedGuilds();

        if (guilds.size() < 1) {
            addButton(Button.createBasic(ItemBuilder.of(Material.OAK_SIGN).name("No Guilds!").build()), 36 );
            update();
            return;
        }

        int displacement = 10;
        for(int i = (page * numNum); i < Math.min(numNum + (page*numNum), guilds.size()); i++) {
            Guild g = GuildData.getGuild(UUID.fromString(String.valueOf(guilds.keySet().toArray()[i])));
            String tag = (g.getTag() == null) ? "" : ChatColor.YELLOW + " [" + g.getTag() + "]";
            ChatColor color = ChatColor.WHITE;
            if (i == 0) color = ChatColor.GOLD;
            else if (i == 1) color = ChatColor.GRAY;
            else if (i == 2) color = ChatColor.DARK_RED;

            Button guildView = Button.create(ItemBuilder.of(g.getIcon()).name(color + g.getName() + tag).lore(
                    "",
                    ChatColor.WHITE + g.getDescription(),
                    ChatColor.GRAY + "-" + Bukkit.getOfflinePlayer(g.getGuild_leader()).getName(),
                    "",
                    ChatColor.GRAY + "Members: " + ChatColor.WHITE + g.getAllMembers().length,
                    ChatColor.GRAY + "Kills: " + ChatColor.WHITE + (int) g.getKills()
            ).build(), (e) -> {});

            addButton(guildView, displacement);

            if((i+1) % 7 == 0) {
                displacement += 2;
            }
            displacement++;
        }

        if (page != 0) {
            Button lastPage = Button.create(ItemBuilder.of(Material.ARROW).name(ChatColor.GREEN + "Last Page").build(), (e) -> {
                if((page - 1) * numNum >= 0) pageChange(p, page - 1);
            });
            addButton(lastPage, 18);
        }

        if (page != pagesNeeded - 1) {
            Button nextPage = Button.create(ItemBuilder.of(Material.ARROW).name(ChatColor.GREEN + "Next Page").build(), (e) -> {
                if((page + 1) * numNum < guilds.size()) pageChange(p, page + 1);
            });

            addButton(nextPage, 26);
        }

        update();
    }

    private void defaults(Player p) {
        clear();
        fill(0, 6 * 9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).name(" ").build());

        Button playerGuild;
        if (GuildUtils.isInGuild(p)) {
            Guild g = GuildUtils.getGuildPlayerIn(p);
            playerGuild = Button.create(ItemBuilder.of(g.getIcon()).name(ChatColor.YELLOW + g.getName()).lore(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Click to view your guild.").build(), (e) -> {
                p.closeInventory();
                new BukkitRunnable() {
                    @Override
                    public void run() {
//                        View player's guild
                    }
                }.runTaskLater(Tazpvp.getInstance(), 2L);
            });
        } else {
            playerGuild = Button.create(ItemBuilder.of(Material.MINECART).name(ChatColor.RED + "You are not in a guild.").lore(
                    ChatColor.YELLOW + "Click to buy a guild.",
                    ChatColor.GOLD + "$6,000 Coins",
                    ChatColor.AQUA + "10 Shards"
            ).build(), (e) -> {
                p.closeInventory();
//                create guild
            });
        }
        addButton(playerGuild, 4);

        update();
    }

}

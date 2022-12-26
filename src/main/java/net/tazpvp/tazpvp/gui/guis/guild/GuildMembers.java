package net.tazpvp.tazpvp.gui.guis.guild;

import net.tazpvp.tazpvp.guild.Guild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;
import world.ntdi.nrcore.utils.item.builders.SkullBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuildMembers extends GUI {
    public GuildMembers(Player p, Guild g) {
        super("Members", 4);
        addItems(p, g);
        open(p);
    }

    private void addItems(Player p, Guild g) {
        fill(0, 4*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).name(" ").build());

        Button owner = Button.createBasic(SkullBuilder.of()
                .setHeadTexture(g.getGuild_leader())
                .name(Bukkit.getOfflinePlayer(g.getGuild_leader()).getName())
                .build());

        addButton(owner, 4);

        List<OfflinePlayer> members = new ArrayList<>();
        for (UUID uuid : g.getAllMembers()) {
            if (uuid.equals(p.getUniqueId())) continue;
            members.add(Bukkit.getOfflinePlayer(uuid));
        }
        allOthers(members, g, p);

        update();
    }

    private void allOthers(List<OfflinePlayer> members, Guild g, Player viewer) {
        int index = 10;
        for (OfflinePlayer p : members) {
            ChatColor nameColor = g.getGuild_generals().contains(p.getUniqueId()) ? ChatColor.GREEN : ChatColor.GRAY;
            ChatColor rankColor = g.getGuild_generals().contains(p.getUniqueId()) ? ChatColor.DARK_GREEN : ChatColor.GRAY;
//            String contributions = ChatColor.GRAY + "Kills: " + ChatColor.WHITE + g.getKillsPlayer(p.getUniqueId());
//            String contributions2 = ChatColor.GRAY + "Deaths: " + ChatColor.WHITE + g.getDeathsPlayer(p.getUniqueId());
            String extraLore = g.hasElevatedPerms(viewer.getUniqueId()) ? ChatColor.RED + "Click me to edit!" : "";
            ItemStack plrItem;
            if (g.hasElevatedPerms(viewer.getUniqueId())) {
                plrItem = SkullBuilder.of().setHeadTexture(p).name(nameColor + p.getName()).lore(rankColor + g.getGroup(p.getUniqueId()), /*contributions, contributions2,*/ "", extraLore);
            } else {
                plrItem = new ItemBuilder(ItemUtils.skull(p)).setName(nameColor + p.getName()).setLore(rankColor + g.getGroup(p.getUniqueId()), /*contributions, contributions2*/);
            }
            Button plr = Button.create(plrItem, (e) -> {
                if (!viewer.getUniqueId().equals(p.getUniqueId())) {
                    if (g.isOwner(viewer.getUniqueId())) ownerGui(viewer, p, g);
                    else if (g.isStaff(viewer.getUniqueId())) staffGui(viewer, p, g);
                }
            });

            gui.addButton(index, plr);

            if (index == 16 || index == 25) {
                index += 2;
            }
            index++;
        }

        if (members.isEmpty()) {
            ItemButton emptylmfao = ItemButton.create(new ItemBuilder(Material.OAK_SIGN).setName(ChatColor.WHITE + "Such Lonely (╯°□°）╯︵ ┻━┻").setLore(ChatColor.GRAY + "Invite some members with /guild invite or the paper button"), e->{});
            gui.addButton(22, emptylmfao);
        }

        if (g.hasPerms(viewer)) {
            ItemButton inviteBTN = ItemButton.create(new ItemBuilder(Material.PAPER).setName(ChatColor.GREEN + "Invite Player").setLore(ChatColor.GRAY + "Invite a player to your guild."), (e) -> {
                new InviteGuildGUI(viewer, g);
            });
            gui.addButton(27, inviteBTN);
        }

        gui.update();
    }
}

package net.tazpvp.tazpvp.gui.guis.guild;

import net.tazpvp.tazpvp.guild.Guild;
import net.tazpvp.tazpvp.utils.enums.CC;
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
        super("Guild Members", 4);
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
            //rank based colors

            String lore = g.hasElevatedPerms(viewer.getUniqueId()) ? ChatColor.RED + "Click me to edit!" : "";
            ItemStack plrItem = SkullBuilder.of().setHeadTexture(p).name(nameColor + p.getName()).lore(nameColor + g.getRank(p.getUniqueId()), "", lore).build();
            Button item = Button.createBasic(plrItem);

            addButton(item, index);

            if (index == 16 || index == 25) {
                index += 2;
            }
            index++;
        }

        if (members.isEmpty()) {
            addButton(
                Button.createBasic(ItemBuilder.of(Material.OAK_SIGN)
                        .name(CC.DARK_GREEN + "Guild Empty")
                        .lore(CC.GRAY + "Invite players with" + CC.GREEN + " /guild invite")
                        .build()),
                22);
        }

        update();
    }
}

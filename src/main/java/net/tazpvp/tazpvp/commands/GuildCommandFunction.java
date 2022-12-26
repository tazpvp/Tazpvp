package net.tazpvp.tazpvp.commands;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.guild.Guild;
import net.tazpvp.tazpvp.guild.GuildUtils;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.GuildData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

import java.awt.*;
import java.util.UUID;

public class GuildCommandFunction extends CommandCore implements CommandFunction {

    public GuildCommandFunction() {
        super("guild", "tazpvp.guild", "g");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args[0].equalsIgnoreCase("invite")) {
                if (GuildUtils.isInGuild(p)) {
                    Player target = Bukkit.getPlayer(args[1]);
                    Guild g = GuildData.getGuild(p.getUniqueId());

                    invite(p, target, g);
                } else {
                    p.sendMessage("You are not in a guild");
                }
            } else if (args[0].equalsIgnoreCase("accept")) {
                acceptInvite(p);
            }
        }
    }

    private static void invite(Player p, Player target, Guild g) {
        if (target == null) return;

        if (g.hasElevatedPerms(p.getUniqueId())) {
            if (g.getAllMembers().length >= 21) {
                p.sendMessage("Your guild is full");
                return;
            } else if (g.isInGuild(target.getUniqueId())) {
                p.sendMessage("This user is already in your guild.");
                return;
            } else if (GuildUtils.isInGuild(target)) {
                p.sendMessage(target.getName() + " is already in a guild.");
                return;
            } else if (g.isInvited(target.getUniqueId())) {
                p.sendMessage(target + " was already invited to your guild.");
                return;
            }

            TextComponent component = new TextComponent(TextComponent.fromLegacyText("[Click Here] to join"));

            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guild accept"));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Accept Invite")));

            target.spigot().sendMessage(component);

            g.invitePlayer(target.getUniqueId(), p.getUniqueId());
            target.setMetadata("guildInvited", new FixedMetadataValue(Tazpvp.getInstance(), g.getID()));

        } else {
            p.sendMessage("You do not have permission to invite");
        }
    }

    private static void acceptInvite(Player p) {
        if (p.hasMetadata("guildInvited")) {
            UUID guildID = UUID.fromString(p.getMetadata("guildInvited").get(0).asString());
            Guild g = GuildData.getGuild(guildID);
            g.acceptInvite(p.getUniqueId());
            p.removeMetadata("guildInvite", Tazpvp.getInstance());
        } else {
            p.sendMessage("You were not invited to this guild.");
        }
    }
}

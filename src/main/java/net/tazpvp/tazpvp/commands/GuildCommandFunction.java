package net.tazpvp.tazpvp.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.guild.Guild;
import net.tazpvp.tazpvp.guild.GuildUtils;
import net.tazpvp.tazpvp.utils.data.GuildData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

import java.util.List;
import java.util.UUID;

public class GuildCommandFunction extends CommandCore implements CommandFunction {

    public GuildCommandFunction() {
        super("guild", "guild", "g");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String notInGuild = "You are not in a guild";
        String noPerms = "You do not have permission to do this.";

        if (sender instanceof Player p) {
            String cmd = args[0];
            if (cmd.equalsIgnoreCase("help")) {
                String[] help = {
                        "",
                        "",
                        "",
                        "",
                        ""
                };
                p.sendMessage(help);
            } else if (cmd.equalsIgnoreCase("invite")) {
                if (GuildUtils.isInGuild(p)) {
                    Player target = Bukkit.getPlayer(args[1]);
                    Guild g = GuildUtils.getGuildPlayerIn(p);

                    invite(p, target, g);
                } else {
                    p.sendMessage(notInGuild);
                }
            } else if (cmd.equalsIgnoreCase("accept")) {
                acceptInvite(p);
            } else if (cmd.equalsIgnoreCase("leave")) {
                if (GuildUtils.isInGuild(p)) {
                    Guild g = GuildUtils.getGuildPlayerIn(p);
                    if (g.getGuild_leader() == p.getUniqueId()) {
                        p.sendMessage("You cannot leave your guild. Try disbanding it.");
                        return;
                    }
                    g.removeMember(p.getUniqueId());
                } else {
                    p.sendMessage(notInGuild);
                }
            } else if (cmd.equalsIgnoreCase("disband")) {
                if (GuildUtils.isInGuild(p)) {
                    Guild g = GuildUtils.getGuildPlayerIn(p);
                    if (g.getGuild_leader() != p.getUniqueId()) return;
                    g.deleteGuild();
                    p.sendMessage("You disbanded your guild.");
                } else {
                    p.sendMessage(notInGuild);
                }
            } else if (cmd.equalsIgnoreCase("delete")) {
                String guildName = args[1];
                for (Guild g : GuildData.getAllGuilds()) {
                    if (g.getName().equals(guildName)) {
                        if (!p.isOp()) return;
                        g.deleteGuild();
                        p.sendMessage("You've deleted the guild: " + guildName);
                    }
                }
            } else if (cmd.equalsIgnoreCase("kick")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (GuildUtils.isInGuild(p)) {
                    Guild g = GuildUtils.getGuildPlayerIn(p);
                    if (!g.hasElevatedPerms(p.getUniqueId())) {
                        p.sendMessage(noPerms);
                        return;
                    }
                    if (target != null) {
                        if (g.getGuild_members().contains(p.getUniqueId())) {
                            g.removeMember(target.getUniqueId());
                            p.sendMessage("You kicked the user: " + target);
                        } else {
                            p.sendMessage("This user is not in your guild.");
                            return;
                        }
                    } else {
                        p.sendMessage("User was not found.");
                    }
                } else {
                    p.sendMessage(notInGuild);
                }
            } else if (cmd.equalsIgnoreCase("promote")) {
                Player target = Bukkit.getPlayer(args[1]);

                if (GuildUtils.isInGuild(p)) {
                    Guild g = GuildUtils.getGuildPlayerIn(p);

                    if (g.getGuild_leader() != p.getUniqueId()) {
                        p.sendMessage(noPerms);
                        return;
                    }
                    if (target != null) {
                        if (g.getGuild_members().contains(p.getUniqueId())) {
                            if (g.getGuild_generals().contains(target.getUniqueId())) {
                                p.sendMessage("This user is already a general.");
                            } else {
                                g.promoteMember(target.getUniqueId());
                            }
                        } else {
                            p.sendMessage("This user is not in your guild.");
                            return;
                        }
                    } else {
                        p.sendMessage("User was not found.");
                    }
                } else {
                    p.sendMessage(notInGuild);
                }
            } else if (cmd.equalsIgnoreCase("demote")) {
                Player target = Bukkit.getPlayer(args[1]);

                if (GuildUtils.isInGuild(p)) {
                    Guild g = GuildUtils.getGuildPlayerIn(p);

                    if (g.getGuild_leader() != p.getUniqueId()) {
                        p.sendMessage(noPerms);
                        return;
                    }
                    if (target != null) {
                        if (g.getGuild_members().contains(p.getUniqueId())) {
                            if (g.getGuild_generals().contains(target.getUniqueId())) {
                                g.demoteMember(target.getUniqueId());
                                p.sendMessage("You just demoted: " + target.getName());
                            } else {
                                p.sendMessage("This user is already a member.");
                            }
                        } else {
                            p.sendMessage("This user is not in your guild.");
                            return;
                        }
                    } else {
                        p.sendMessage("User was not found.");
                    }
                } else {
                    p.sendMessage(notInGuild);
                }
            }
            //TODO: transfer ownership cmd
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
            for (UUID u : g.getInvited()) {
                Bukkit.broadcastMessage(Bukkit.getPlayer(u).getName());
            }

            g.acceptInvite(p.getUniqueId());
            p.removeMetadata("guildInvite", Tazpvp.getInstance());
        } else {
            p.sendMessage("You were not invited to this guild.");
        }
    }

    @Override
    public List<String> tabCompletion(CommandSender commandSender, String[] strings) {
        return List.of("");
    }
}

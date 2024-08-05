package net.tazpvp.tazpvp.commands.gameplay.event;

import lombok.NonNull;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.game.events.Event;
import net.tazpvp.tazpvp.game.events.EventHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.PlayerUtils;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;
import java.util.UUID;

public class EventCommand extends NRCommand {
    public EventCommand() {
        super(new Label("event", "tazpvp.events", "events"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (!PlayerUtils.checkPerms(p, Tazpvp.prefix + "event.host")) return true;
            if (args.length < 2) {
                sendIncorrectUsage(p);
                return true;
            }
            if (Event.eventTypes.contains(args[1])) {
                if (Event.currentEvent == null) {
                    Event.currentEvent = EventHelper.create(args[1]);
                    Event.currentEvent.addParticipant(p.getUniqueId());

                    Bukkit.broadcastMessage("The " + args[1] + " event has begun.");

                    TextComponent component = new TextComponent(CC.GREEN + "" + CC.UNDERLINE + "CLICK HERE" + CC.GREEN + "to participate");
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Click to join the event").create()));
                    component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.RUN_COMMAND, "/events join"));
                    for (Player plr : Bukkit.getOnlinePlayers()) {
                        plr.spigot().sendMessage(component);
                    }
                }
                return true;
            } else {
                p.sendMessage(CC.YELLOW + "'" + args[1] + "'" + CC.RED + " is not a valid event type.");
                p.sendMessage(CC.RED + "Try: FFA, Parkour");
            }
        } else if (args[0].equalsIgnoreCase("begin")) {
            if (Event.currentEvent != null) {
                Event.currentEvent.begin();
                for (UUID id : Event.currentEvent.getParticipantList()) {
                    Event.currentEvent.addAliveList(id);
                }
                p.sendMessage("You began the " + Event.currentEvent.getNAME() + " event.");
            } else {
                p.sendMessage(CC.RED + "There is no active event. Create one with /event create");
            }
        } else if (args[0].equalsIgnoreCase("join")) {

//            if (Event.currentEvent != null) {
//                if (Event.currentEvent.getParticipantList().contains(p.getUniqueId())) {
//                    p.sendMessage(CC.RED + "You already joined the event.");
//                    return true;
//                }
//                if (!Event.currentEvent.getAliveList().isEmpty()) {
//                    p.sendMessage(CC.RED + "The event has already begun, you can no longer join.");
//                    return true;
//                }
//                Event.currentEvent.getParticipantList().add(p.getUniqueId());
//                p.sendMessage("You joined the event: " + Event.currentEvent.getNAME());
//
//                for (UUID plr : Event.currentEvent.getParticipantList()) {
//                    Bukkit.getPlayer(plr).sendMessage(p.getName() + " has joined the event. " + "(" + Event.currentEvent.getParticipantList().size() + ")");
//                }
//                Tazpvp.getObservers().forEach(observer -> observer.event(p));
//            }
        } else {
            String[] commands = {
                    "Event Commands:\n",
                    "/event create <type> (FFA / Parkour)\n",
                    "/event join - Joins the current event.\n",
                    "/event begin - Starts the current event.\n",
                    ""
            };
            p.sendMessage(commands);
        }
        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return List.of("create", "begin", "join");
        } else if (args[0].equalsIgnoreCase("create") && args.length == 2) {
            return List.of("FFA");
        } else {
            return List.of();
        }
    }
}

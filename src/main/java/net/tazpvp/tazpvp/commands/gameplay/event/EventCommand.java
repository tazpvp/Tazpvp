package net.tazpvp.tazpvp.commands.gameplay.event;

import lombok.NonNull;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.events.Event;
import net.tazpvp.tazpvp.events.EventUtils;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.PlayerUtils;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.ArrayList;
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
            for (String event : Tazpvp.events) {
                if (args[1].equalsIgnoreCase(event)) {
                    if (Tazpvp.event == null) {
                        Tazpvp.event = EventUtils.create(args[1], Event.participantList);
                        Event.participantList.add(p.getUniqueId());

                        Bukkit.broadcastMessage("The " + args[1] + "event has begun.");

                        TextComponent component = new TextComponent(CC.GREEN + "" + CC.UNDERLINE + "CLICK HERE" + CC.GREEN + "to participate");
                        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Click to join the event").create()));
                        component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.RUN_COMMAND, "/events join"));
                        for (Player plr : Bukkit.getOnlinePlayers()) {
                            plr.spigot().sendMessage(component);
                        }
                    }
                    return true;
                }
            }
        } else if (args[0].equalsIgnoreCase("begin")) {
            if (Tazpvp.event != null) {
                Tazpvp.event.begin();
                for (UUID id : Event.participantList) {
                    Event.aliveList.add(id);
                }
                p.sendMessage("You began the " + Tazpvp.event.getNAME() + " event.");
            }
        } else if (args[0].equalsIgnoreCase("join")) {
            if (Tazpvp.event != null) {
                if (Event.participantList.contains(p.getUniqueId())) {
                    p.sendMessage("You already joined the event.");
                    return true;
                }
                Tazpvp.getObservers().forEach(observer -> observer.event(p));
                Event.participantList.add(p.getUniqueId());
                p.sendMessage("You joined the event: " + Tazpvp.event.getNAME());

                for (UUID plr : Event.participantList) {
                    Bukkit.getPlayer(plr).sendMessage(p.getName() + " has joined the event." + "(" + Event.participantList.size() + ")");
                }
            }
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

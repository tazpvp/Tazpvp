package net.tazpvp.tazpvp.commands.gameplay.event;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.events.EventUtils;
import org.bukkit.Bukkit;
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
            for (String event : Tazpvp.events) {
                if (args[1].equalsIgnoreCase(event)) {
                    if (Tazpvp.event == null) {
                        Tazpvp.event = EventUtils.create(args[1], new ArrayList<>());
                        p.sendMessage("You created a(n) " + args[1] + " event.");
                    }
                    return true;
                }
            }
        } else if (args[0].equalsIgnoreCase("list")) {
            for (UUID id : Tazpvp.event.getPlayerList()) {
                p.sendMessage(Bukkit.getPlayer(id).getName());
            }
        } else if (args[0].equalsIgnoreCase("begin")) {
            if (Tazpvp.event != null) {
                EventUtils.create(Tazpvp.event.getNAME(), Tazpvp.playerList);
                p.sendMessage("You began the " + Tazpvp.event.getNAME() + " event.");
            }
        } else if (args[0].equalsIgnoreCase("join")) {
            if (Tazpvp.event != null) {
                Tazpvp.getObservers().forEach(observer -> observer.event(p));
                Tazpvp.playerList.add(p.getUniqueId());
                p.sendMessage("You joined the event: " + Tazpvp.event.getNAME());
            }
        } else {
            String[] commands = {
                    "Event Commands:\n",
                    "/event create <type> (FFA / Parkour)\n",
                    "/event list - Lists the players in the event.\n",
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
            return List.of("create", "list", "begin", "join");
        } else if (args[0].equalsIgnoreCase("create") && args.length == 2) {
            return List.of("FFA");
        } else {
            return List.of();
        }
    }
}

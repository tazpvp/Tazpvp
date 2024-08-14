package net.tazpvp.tazpvp.commands.gameplay.event;

import lombok.NonNull;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.commands.gameplay.event.sub.EventJoin;
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

        addSubcommand(new EventJoin());
    }


}

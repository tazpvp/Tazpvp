package net.tazpvp.tazpvp.commands.admin.stats;

import lombok.NonNull;
import net.tazpvp.tazpvp.enums.StatEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class StatCommand extends NRCommand {
    private final List<String> modifiers = List.of(
            "add", "remove", "set"
    );

    private final List<StatEnum> dataTypes = Arrays.stream(StatEnum.values()).toList();

    public StatCommand() {
        super(new Label("stats", "tazpvp.stats", "stat"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return false;
        }

        if (args.length < 4) {
            sendIncorrectUsage(sender, "/stats <add/remove/set> <type> <amount> <player>");
            return false;
        }

        final String modifier = args[0];

        if (!modifiers.contains(modifier)) {
            sendIncorrectUsage(sender);
            return false;
        }

        final StatEnum dataType = StatEnum.valueOf(args[1].toUpperCase());

        final int number = Integer.parseInt(args[2]);

        final Player target = Bukkit.getPlayer(args[3]);

        if (target == null) {
            sendIncorrectUsage(sender);
            return false;
        }

        final UUID targetID = target.getUniqueId();

        switch (modifier) {
            case "add" -> dataType.add(targetID, number);
            case "remove" -> dataType.remove(targetID, number);
            case "set" -> dataType.set(targetID, number);
        }

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return modifiers;
        } else if (args.length == 2) {
            List<String> values = new ArrayList<>();
            for (StatEnum dataTypes : dataTypes) {
                values.add(dataTypes.name().toLowerCase());
            }
            return values;
        } else if (args.length == 3) {
            return List.of("<amount>");
        } else if (args.length == 4) {
            return Completer.onlinePlayers(args[3]);
        }
        return List.of();
    }
}

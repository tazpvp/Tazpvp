package net.tazpvp.tazpvp.commands.admin.stats;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatCommand extends NRCommand {
    private final List<String> modifiers = List.of(
            "add", "remove", "set"
    );

    private final List<DataTypes> dataTypes = Arrays.stream(DataTypes.values()).filter(datatype -> datatype.isQuantitative()).toList();

    public StatCommand() {
        super(new Label("stats", "tazpvp.stats", "stat"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return false;
        }

        // /stats modifier datatype number player
        if (args.length < 4) {
            sendIncorrectUsage(sender, "MISSING ALL ARGS");
            return false;
        }

        final String modifier = args[0];

        if (!modifiers.contains(modifier)) {
            sendIncorrectUsage(sender);
            return false;
        }

        final DataTypes dataType = DataTypes.valueOf(args[1]);

        final int number = Integer.parseInt(args[2]);

        final Player target = Bukkit.getPlayer(args[3]);

        if (target == null) {
            sendIncorrectUsage(sender);
            return false;
        }

        switch (modifier) {
            case "add" -> PersistentData.add(target, dataType, number);
            case "remove" -> PersistentData.remove(target, dataType, number);
            case "set" -> PersistentData.set(target, dataType, number);
        }

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return modifiers;
        } else if (args.length == 2) {
            List<String> values = new ArrayList<>();
            for (DataTypes dataTypes : dataTypes) {

            }
        }
        return List.of();
    }
}

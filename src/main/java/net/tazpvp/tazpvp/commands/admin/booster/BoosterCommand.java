package net.tazpvp.tazpvp.commands.admin.booster;

import lombok.NonNull;
import net.tazpvp.tazpvp.booster.ActiveBoosterManager;
import net.tazpvp.tazpvp.booster.BoosterTypes;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BoosterCommand extends NRCommand {
    public BoosterCommand() {
        super(new Label("booster", "tazpvp.addbooster"));
    }

    @Override
    public boolean execute(@NonNull final CommandSender sender, @NotNull final @NonNull String[] args) {
        if (sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return false;
        }

        if (args.length >= 4) {
            final BoosterTypes boosterType = BoosterTypes.valueOf(args[0]);
            final TimeUnit timeUnit = TimeUnit.valueOf(args[1]);
            final int duration = Integer.parseInt(args[2]);
            final UUID uuid = UUID.fromString(args[3]);

            ActiveBoosterManager.getInstance().addTimeToBooster(boosterType, timeUnit,duration);

            // TODO: Announce buyer
        }

        return true;
    }

    @Override
    public List<String> complete(final CommandSender sender, final String[] args) {
        if (args.length <= 1) {
            return Arrays.stream(BoosterTypes.values()).map(Enum::name).toList();
        }
        if (args.length == 2) {
            return Arrays.stream(TimeUnit.values()).map(Enum::name).toList();
        }
        if (args.length == 3) {
            return List.of("1", "15", "30");
        }
        if (args.length == 4) {
            return List.of("cfe18f47-23bc-49a3-8f0b-b6df49f893eb");
        }

        return List.of("");
    }
}

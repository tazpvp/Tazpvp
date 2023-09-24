package net.tazpvp.tazpvp.commands.gameplay.loadout;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.data.KitData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.kit.SerializableInventory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class LoadoutCommand extends NRCommand {
    public LoadoutCommand() {
        super(new Label("loadout", null));
    }

    @Override
    public boolean execute(@NonNull final CommandSender sender, @NotNull final @NonNull String[] args) {
        if (!(sender instanceof Player player)) {
            sendIncorrectUsage(sender);
            return false;
        }

        final SerializableInventory serializableInventory = SerializableInventory.readHotbar(player.getInventory());

        KitData.setSerial(player.getUniqueId(), SerializableInventory.convertToString(serializableInventory));

        sender.sendMessage(CC.GREEN + "Saved layout for next time.");

        return true;
    }

    @Override
    public List<String> complete(final CommandSender sender, final String[] args) {
        return List.of("");
    }
}

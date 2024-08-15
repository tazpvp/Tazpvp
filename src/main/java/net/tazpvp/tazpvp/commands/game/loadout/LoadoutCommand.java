package net.tazpvp.tazpvp.commands.game.loadout;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.entity.KitEntity;
import net.tazpvp.tazpvp.data.implementations.KitServiceImpl;
import net.tazpvp.tazpvp.data.services.KitService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.SerializableInventory;
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

        final KitService kitService = new KitServiceImpl();

        KitEntity kitEntity = kitService.getOrDefault(player.getUniqueId());

        kitEntity.setSerial(SerializableInventory.convertToString(serializableInventory));

        kitService.saveKitEntity(kitEntity);

        sender.sendMessage(CC.GREEN + "Saved layout for next time.");

        return true;
    }

    @Override
    public List<String> complete(final CommandSender sender, final String[] args) {
        return List.of("");
    }
}

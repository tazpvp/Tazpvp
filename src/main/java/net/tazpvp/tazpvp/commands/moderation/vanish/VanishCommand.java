package net.tazpvp.tazpvp.commands.moderation.vanish;

import lombok.NonNull;
import net.tazpvp.tazpvp.commands.admin.tazload.TazloadCommand;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.CombatTagFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class VanishCommand extends NRCommand {
    public VanishCommand(@NonNull Label label) {
        super(new Label("vanish", "tazpvp.vanish", "v"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        if (PlayerWrapper.getPlayer(p).getDuel() != null) {
            p.sendMessage(CC.RED + "You cannot use this command while dueling.");
            return true;
        }

        if (PlayerWrapper.getPlayer(p).getSpectating() != null) {
            p.sendMessage(CC.RED + "You cannot use this command while spectating.");
            return true;
        }

        if (CombatTagFunctions.isInCombat(p.getUniqueId())) {
            p.sendMessage( CC.RED + "You cannot use this command while in combat.");
            return true;
        }

        if (TazloadCommand.tazloading) {
            p.sendMessage(CC.RED + "This feature is disabled while the server is reloading.");
            return true;
        }


        return true;
    }
}

package net.tazpvp.tazpvp.commands.admin.tazload;

import net.tazpvp.tazpvp.objects.DuelObject;
import org.bukkit.Bukkit;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class TazloadCommand extends NRCommand {

    public static boolean tazloading = false;
    public TazloadCommand() {
        super(new Label("tazload", "tazpvp.tazload"));
        setNativeExecutor((sender, args) -> {

            if (!sender.hasPermission(getLabel().getPermission())) {
                sendNoPermission(sender);
                return true;
            }

            tazloading = true;
            Bukkit.broadcastMessage("Server reloading");

            for (DuelObject duel : DuelObject.getActiveDuels()) {
                if (duel != null) {
                    duel.abort();
                }
            }

            return true;
        });
    }
}

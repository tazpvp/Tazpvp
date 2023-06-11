package net.tazpvp.tazpvp.commands.admin.tazload;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.duels.Duel;
import org.bukkit.Bukkit;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class TazloadCommand extends NRCommand {

    public static boolean tazloading = false;
    public TazloadCommand() {
        super(new Label("tazload", "tazpvp.tazload"));
        setNativeExecutor((sender, args) -> {
            Bukkit.broadcastMessage("Server reloading");
            for (Duel duel : Duel.duels.keySet()) {
                duel.abort();
            }
            return true;
        });
    }
}

package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.utils.data.PunishmentService;
import net.tazpvp.tazpvp.utils.data.PunishmentServiceImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandSend implements Listener {
    @EventHandler
    public void onCommand(ServerCommandEvent playerCommandSendEvent) {
        if (!(playerCommandSendEvent.getSender() instanceof Player player)) {
            return;
        }

        if (new PunishmentServiceImpl().getPunishment(player.getUniqueId()) == PunishmentService.PunishmentType.MUTED) {
            if (playerCommandSendEvent.getCommand().contains("me")) {
                playerCommandSendEvent.setCancelled(true);
            }
        }
    }
}

package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.utils.data.PunishmentData;
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

        if (PunishmentData.isMuted(player.getUniqueId())) {
            if (playerCommandSendEvent.getCommand().contains("me")) {
                playerCommandSendEvent.setCancelled(true);
            }
        }
    }
}

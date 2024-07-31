package net.tazpvp.tazpvp.helpers;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PunishmentEntity;
import net.tazpvp.tazpvp.data.implementations.PunishmentServiceImpl;
import net.tazpvp.tazpvp.data.services.PunishmentService;
import net.tazpvp.tazpvp.utils.TimeUtil;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class BanFunctions {

    public static void checkBan(Player p) {
        final PunishmentService punishmentService = new PunishmentServiceImpl();
        final PunishmentEntity punishmentEntity = punishmentService.getOrDefault(p.getUniqueId());
        final PunishmentService.PunishmentType punishmentType = punishmentService.getPunishment(p.getUniqueId());

        TextComponent component = new TextComponent(CC.GREEN + "Join the discord to appeal [Click here]");
        component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/56rdkbSqa8"));

        if (punishmentType == PunishmentService.PunishmentType.BANNED) {
            if (punishmentService.getTimeRemaining(p.getUniqueId()) > 0) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.setGameMode(GameMode.SPECTATOR);
                    }
                }.runTaskLater(Tazpvp.getInstance(), 20);

                p.sendMessage("");
                p.sendMessage(CC.RED + "You've been banned.");
                p.sendMessage(CC.GRAY + "Reason: " + CC.WHITE + punishmentEntity.getReason());
                p.sendMessage(CC.GRAY + "Time left: " + CC.WHITE + TimeUtil.howLongAgo(punishmentService.getTimeRemaining(UUID.randomUUID())));
                p.sendMessage("");

                p.spigot().sendMessage(component);
            } else {
                punishmentService.unpunish(p.getUniqueId());
            }
        }
    }
}

package net.tazpvp.tazpvp.utils.functions;

import net.tazpvp.tazpvp.utils.TimeToken;
import net.tazpvp.tazpvp.utils.data.PunishmentService;
import net.tazpvp.tazpvp.utils.data.PunishmentServiceImpl;
import org.bukkit.entity.Player;

public class PunishmentFunctions {

    public static void ban(Player target, String time) {
        ban(target, time, "Unfair Advantage");
    }

    public static void ban(Player target, String time, String reason) {
        TimeToken timeToken = new TimeToken(time);

        final PunishmentService punishmentService = new PunishmentServiceImpl();
        punishmentService.punish(target.getUniqueId(), PunishmentService.PunishmentType.BANNED, timeToken.getUnixTimestamp());

        target.kickPlayer(reason);
    }

    public static void mute(Player target, String time) {
        mute(target, time, "Chat Infraction");
    }

    public static void mute(Player target, String time, String reason) {
        TimeToken timeToken = new TimeToken(time);

        final PunishmentService punishmentService = new PunishmentServiceImpl();
        punishmentService.punish(target.getUniqueId(), PunishmentService.PunishmentType.MUTED, timeToken.getUnixTimestamp());
    }

    public static void unmute(Player target) {
        PunishmentService punishmentService = new PunishmentServiceImpl();
        punishmentService.unpunish(target.getUniqueId());
    }

    public static void unban(Player target) {
        PunishmentService punishmentService = new PunishmentServiceImpl();
        punishmentService.unpunish(target.getUniqueId());
    }
}

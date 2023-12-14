package net.tazpvp.tazpvp.utils.functions;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.utils.TimeToken;
import net.tazpvp.tazpvp.utils.data.PunishmentService;
import net.tazpvp.tazpvp.utils.data.PunishmentServiceImpl;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PunishmentFunctions {

    public static void ban(Player target, String time) {
        ban(target, time, "Unfair Advantage");
    }

    public static void ban(Player target, String time, String reason) {
        TimeToken timeToken = new TimeToken(time);

        final PunishmentService punishmentService = new PunishmentServiceImpl();
        punishmentService.punish(target.getUniqueId(), PunishmentService.PunishmentType.BANNED, timeToken.getUnixTimestamp(), reason);

        target.setGameMode(GameMode.SPECTATOR);

        ChatFunctions.announce(target, CC.GRAY + "You've been banned for: " + CC.RED + reason, Sound.BLOCK_ANVIL_LAND);
        TextComponent component = new TextComponent(CC.GREEN + "Join the discord to appeal [Click here]");
        component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/56rdkbSqa8"));
        target.spigot().sendMessage(component);
        target.sendMessage("");

        target.kickPlayer(CC.GRAY + "You've been banned for: " + CC.RED + reason);
    }

    public static void mute(Player target, String time) {
        mute(target, time, "Chat Infraction");
    }

    public static void mute(Player target, String time, String reason) {
        TimeToken timeToken = new TimeToken(time);

        final PunishmentService punishmentService = new PunishmentServiceImpl();
        punishmentService.punish(target.getUniqueId(), PunishmentService.PunishmentType.MUTED, timeToken.getUnixTimestamp(), reason);
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

package net.tazpvp.tazpvp.utils.functions;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.listeners.CommandSend;
import net.tazpvp.tazpvp.utils.TimeToken;
import net.tazpvp.tazpvp.data.services.PunishmentService;
import net.tazpvp.tazpvp.data.implementations.PunishmentServiceImpl;
import net.tazpvp.tazpvp.utils.discord.webhook.BanWebhook;
import net.tazpvp.tazpvp.utils.discord.webhook.MuteWebhook;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PunishmentFunctions {

    public static void ban(OfflinePlayer target, String time, CommandSender commandSender) {
        ban(target, time,commandSender, "Unfair Advantage");
    }

    public static void ban(OfflinePlayer target, String time, CommandSender commandSender, String reason) {
        TimeToken timeToken = new TimeToken(time);

        final PunishmentService punishmentService = new PunishmentServiceImpl();
        punishmentService.punish(target.getUniqueId(), PunishmentService.PunishmentType.BANNED, timeToken.getUnixTimestamp(), reason);

        if (commandSender instanceof Player p) {
            ChatFunctions.announce(CC.RED + target.getName() + " was banned by " + p, Sound.BLOCK_STONE_BUTTON_CLICK_OFF);
        } else {
            ChatFunctions.announce(CC.RED + target.getName() + " has been banned.", Sound.BLOCK_STONE_BUTTON_CLICK_OFF);
        }

        if (target.isOnline()) {
            Player banned = target.getPlayer();
            if (banned == null) return;
            ChatFunctions.announce(banned, CC.GRAY + "You've been banned for: " + CC.RED + reason, Sound.BLOCK_ANVIL_LAND);
            TextComponent component = new TextComponent(CC.GREEN + "Join the discord to appeal [Click here]");
            component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/56rdkbSqa8"));
            banned.spigot().sendMessage(component);
            banned.sendMessage("");

            banned.setGameMode(GameMode.SPECTATOR);
        }
        new BanWebhook(target.getName(), commandSender.getName(), reason);
    }

    public static void mute(Player target, String time, CommandSender commandSender) {
        mute(target, time, commandSender, "Chat Infraction");
    }

    public static void mute(Player target, String time, CommandSender commandSender, String reason) {
        TimeToken timeToken = new TimeToken(time);

        final PunishmentService punishmentService = new PunishmentServiceImpl();
        punishmentService.punish(target.getUniqueId(), PunishmentService.PunishmentType.MUTED, timeToken.getUnixTimestamp(), reason);
        new MuteWebhook(target.getName(), commandSender.getName(), reason);
    }

    public static void unmute(UUID target) {
        PunishmentService punishmentService = new PunishmentServiceImpl();
        punishmentService.unpunish(target);
    }

    public static void unban(Player target) {
        PunishmentService punishmentService = new PunishmentServiceImpl();
        punishmentService.unpunish(target.getUniqueId());
    }
}

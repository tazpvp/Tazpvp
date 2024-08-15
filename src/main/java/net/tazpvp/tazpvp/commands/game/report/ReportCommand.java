package net.tazpvp.tazpvp.commands.game.report;

import lombok.NonNull;
import net.tazpvp.tazpvp.commands.game.report.utils.ReportDebounce;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.utils.discord.webhook.ReportWebhook;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.ChatUtils;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.nrcore.utils.command.simple.builder.LabelBuilder;

import java.util.List;

public class ReportCommand extends NRCommand {
    public ReportCommand() {
        super(LabelBuilder.of("report", null).alias("wdr").usage("/report <player> <reason>").build().make());
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendIncorrectUsage(sender);
            return false;
        }

        if (args.length < 2) {
            sendIncorrectUsage(sender, "Too few args!");
            return false;
        }

        final String targetName = args[0];
        final Player targetPlayer = Bukkit.getPlayer(targetName);

        if (targetPlayer == null) {
            sendIncorrectUsage(sender, "Cannot find player!");
            return false;
        }

        PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);

        for (ReportDebounce debounce : playerWrapper.getReportDebouncesList()) {
            if (!debounce.canReport()) {
                sendIncorrectUsage(sender, "You are on cooldown for reporting this player!");
                return false;
            }
        }

        final String reason = ChatUtils.builder(args, 1);

        playerWrapper.reportPlayer(targetPlayer, reason);
        p.sendMessage(CC.GOLD + "You've reported " + CC.RED + targetName + CC.GOLD + " for " + CC.RED + reason);
        Bukkit.broadcast(CC.RED + targetName + CC.GOLD + " has been reported for " + CC.RED + reason, "tazpvp.staff");
        new ReportWebhook(targetPlayer, p, reason);

         return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Completer.onlinePlayers(args[0]);
        }
        return List.of("");
    }
}

package net.tazpvp.tazpvp.commands.moderation;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.TimeUtil;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import net.tazpvp.tazpvp.utils.report.ReportLogger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class ReportViewCommand extends NRCommand {
    public ReportViewCommand() {
        super(new Label("viewreport", "tazpvp.staff"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return false;
        }

        if (args.length < 1) {
            sendIncorrectUsage(sender, "No Player specified");
            return false;
        }

        final String targetName = args[0];
        final Player targetPlayer = Bukkit.getPlayer(targetName);

        List<ReportLogger> reportLoggerList = PlayerWrapper.getPlayer(targetPlayer).getReportLoggerList();

        sender.sendMessage(CC.GOLD.toString() + CC.STRIKETHROUGH + "                      ");

        for (ReportLogger reportLogger : reportLoggerList) {
            OfflinePlayer reporter = Bukkit.getOfflinePlayer(reportLogger.uuid());
            final String niceTime = TimeUtil.howLongAgo(System.currentTimeMillis() - reportLogger.timestamp());

            sender.sendMessage(CC.RED + reportLogger.reason() + CC.GOLD + " - " + CC.RED + "by: " + CC.BOLD + reporter.getName() + CC.GOLD + " - " + CC.RED + niceTime);
        }

        sender.sendMessage(CC.GOLD.toString() + CC.STRIKETHROUGH + "                      ");

        return true;
    }
}


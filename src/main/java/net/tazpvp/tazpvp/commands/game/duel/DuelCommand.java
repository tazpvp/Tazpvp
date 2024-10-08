package net.tazpvp.tazpvp.commands.game.duel;

import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.tazpvp.tazpvp.commands.admin.tazload.TazloadCommand;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.game.duels.Classic;
import net.tazpvp.tazpvp.game.duels.Op;
import net.tazpvp.tazpvp.helpers.CombatTagHelper;
import net.tazpvp.tazpvp.objects.DuelObject;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;
import java.util.UUID;

public class DuelCommand extends NRCommand {
    public DuelCommand() {
        super(new Label("duel", null));

        addSubcommand(new DuelSendCommand());
        addSubcommand(new DuelAcceptCommand());
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        if (args.length < 2) {
            sendIncorrectUsage(sender, """
                    Usage: /duel <player> <type>
                    Types:\s
                    - Classic
                    - Op""");
            return true;
        }

        if (PlayerWrapper.getPlayer(p).getDuel() != null) {
            p.sendMessage(DuelObject.prefix + "You cannot use this command while dueling.");
            return true;
        }
        if (CombatTagHelper.isInCombat(p.getUniqueId())) {
            p.sendMessage( DuelObject.prefix + "You cannot use this command while in combat.");
            return true;
        }

        if (TazloadCommand.tazloading) {
            p.sendMessage(DuelObject.prefix + "This feature is disabled while the server is reloading.");
            return true;
        }

        if (args.length == 2) {
            Player target = getPlayer(args[0]);
            if (target != null) {
                if (target.getUniqueId().equals(p.getUniqueId())) {
                    p.sendMessage(DuelObject.prefix + "You cannot send a duel to yourself.");
                    return true;
                }
                duelRequest(p, target, args[1]);
            }
        }
        return true;
    }

    private void duelRequest(Player sender, Player target, String type) {
        UUID senderID = sender.getUniqueId();
        UUID targetID = target.getUniqueId();
        PlayerWrapper targetWrapper = PlayerWrapper.getPlayer(target);

        if (targetWrapper.getDuelRequests().containsKey(senderID)) {
            sender.sendMessage(DuelObject.prefix + "You already sent a duel to this person.");
            return;
        }

        if (type.equalsIgnoreCase("classic")) {
            DuelObject duel = new Classic(senderID, targetID);
            targetWrapper.getDuelRequests().putIfAbsent(senderID, duel);
        } else if (type.equalsIgnoreCase("op")) {
            DuelObject duel = new Op(senderID, targetID);
            targetWrapper.getDuelRequests().putIfAbsent(senderID, duel);
        } else {
            sender.sendMessage(DuelObject.prefix + "Not a valid duel type!");
            return;
        }

        sender.sendMessage(DuelObject.prefix + "You sent a duel request to " + CC.AQUA +  target.getName());

        BaseComponent[] baseComponents = new ComponentBuilder("Duel | " + sender.getName())
                .color(ChatColor.AQUA)
                .append(" sent you a ")
                .color(ChatColor.DARK_AQUA)
                .append(type).color(ChatColor.RED)
                .append(" duel request").color(ChatColor.DARK_AQUA)
                .append("\n[Click to Accept]").color(ChatColor.AQUA).create();

        baseComponents[4].setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept " + sender.getName()));
        baseComponents[4].setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(CC.DARK_AQUA + "Accept the duel?")));

        target.sendMessage("");
        target.spigot().sendMessage(baseComponents);
        target.sendMessage("");
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Completer.onlinePlayers(args[0]);
        } else if (args.length == 2) {
            return List.of("Classic", "Op");
        }
        return List.of();
    }

    private Player getPlayer(String string) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(string)) {
                return p;
            }
        }
        return null;
    }
}

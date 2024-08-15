package net.tazpvp.tazpvp.commands.moderation;

import lombok.NonNull;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.objects.PlayerInventoryStorage;
import net.tazpvp.tazpvp.utils.TimeUtil;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;
import java.util.UUID;

public class RestoreCommand extends NRCommand {
    public RestoreCommand() {
        super(new Label("restore", "tazpvp.restore"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {

        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        if (args.length == 1) {
            if (sender.hasPermission(getLabel().getPermission())) {
                final Player target = Bukkit.getPlayer(args[0]);

                final PlayerInventoryStorage playerInventoryStorage = PlayerInventoryStorage.getStorage(target);

                sender.sendMessage(CC.AQUA + "-----------------------------------");
                if (playerInventoryStorage == null) {
                    sender.sendMessage(String.format(CC.GREEN + "\t%s has no available backups :(", target.getName()));
                } else {
                    final long timeDifference = System.currentTimeMillis() - playerInventoryStorage.getTimestamp();
                    final String friendlyTime = TimeUtil.howLongAgo(timeDifference);

                    TextComponent component = new TextComponent(String.format(CC.GREEN + "\t%s - Created %s ago", playerInventoryStorage.getUuid(), friendlyTime));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(CC.GOLD + "Restore this save")}));
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/restore act %s", target.getUniqueId())));
                    sender.spigot().sendMessage(component);
                }
                sender.sendMessage(CC.AQUA + "-----------------------------------");
            } else {
                if (args[0].equals("self")) {
                    PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);
                    if (playerWrapper.isCanRestore()) {
                        PlayerInventoryStorage.restoreStorage(p);
                        playerWrapper.setCanRestore(false);
                    }
                }
            }
        } else if (args.length == 2) {
            if (args[0].equals("act")) {
                if (sender.hasPermission(getLabel().getPermission())) {
                    UUID uuid = UUID.fromString(args[1]);
                    PlayerInventoryStorage.restoreStorage(uuid);
                    sender.sendMessage(String.format(CC.DARK_AQUA + "Success"));
                }
            }
        }
        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Completer.onlinePlayers(args[0]);
        }
        return List.of();
    }
}

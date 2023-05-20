package net.tazpvp.tazpvp.commands.moderation;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.utils.PlaytimeUtil;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.player.PlayerInventoryStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

import java.util.List;
import java.util.UUID;

public class RestoreCommandFunction extends CommandCore implements CommandFunction {
    public RestoreCommandFunction() {
        super("restore", "restore", "rst");
    }

    @Override
    public List<String> tabCompletion(CommandSender commandSender, String[] strings) {
        return List.of("");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length == 1) {
            final Player target = Bukkit.getPlayer(strings[0]);

            final PlayerInventoryStorage playerInventoryStorage = PlayerInventoryStorage.getStorage(target);

            commandSender.sendMessage(CC.AQUA + "-----------------------------------");
            if (playerInventoryStorage == null) {
                commandSender.sendMessage(String.format(CC.GREEN + "\t%s has no available backups :(", target.getName()));
            } else {
                final long timeDifference = System.currentTimeMillis() - playerInventoryStorage.getTimestamp();
                final String friendlyTime = PlaytimeUtil.secondsToDDHHMMSS(timeDifference);

                TextComponent component = new TextComponent(String.format(CC.GREEN + "\t%s - Created %s ago", playerInventoryStorage.getUuid(), friendlyTime));
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(CC.GOLD + "Restore this save")}));
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/restore act %s", target.getUniqueId())));
                commandSender.spigot().sendMessage(component);
            }
            commandSender.sendMessage(CC.AQUA + "-----------------------------------");
        } else if (strings.length == 2) {
            if (strings[0].equals("act")) {
                UUID uuid = UUID.fromString(strings[1]);
                PlayerInventoryStorage.restoreStorage(uuid);
                commandSender.sendMessage(String.format(CC.DARK_AQUA + "Success"));
            }
        }
    }
}

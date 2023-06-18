package net.tazpvp.tazpvp.commands.network;

import lombok.NonNull;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class AdCommand extends NRCommand {
    final String ad = "/ad Tazpvp &bDUELS &f/ &3PVP &f/ &bFFA &f/ &bEVENTS";
    public AdCommand() {
        super(new Label("advertise", null, "ad"));
        setNativeExecutor((sender, args) -> {
            if (!(sender instanceof Player p)) {
                sendNoPermission(sender);
                return true;
            }

            TextComponent copy = new TextComponent("\n" + CC.YELLOW + " Click " + CC.GOLD + "" + CC.BOLD + "Here" + CC.YELLOW + " to copy the advertisement for this server.\n");
            copy.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, ad));
            p.spigot().sendMessage(copy);
            return true;
        });

    }
}

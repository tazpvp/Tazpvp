package net.tazpvp.tazpvp.commands.network;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class StoreCommand extends NRCommand {
    public StoreCommand() {
        super(new Label("store", null));
        setNativeExecutor((sender, args) -> {
            if (!(sender instanceof Player p)) {
                return true;
            }

            String storeLink = "https://taznet.tebex.io/";

            TextComponent component = new TextComponent(CC.GREEN + "" + CC.BOLD + "SERVER STORE:" + CC.GREEN + " [Click Here]");
            component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, storeLink));

            p.spigot().sendMessage(component);

            return true;
        });
    }
}

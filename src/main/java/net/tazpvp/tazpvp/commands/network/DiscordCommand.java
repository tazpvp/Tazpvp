package net.tazpvp.tazpvp.commands.network;

import lombok.NonNull;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class DiscordCommand extends NRCommand {
    public DiscordCommand(@NonNull Label label) {
        super(new Label("discord", null));
        setNativeExecutor((sender, args) -> {
            if (!(sender instanceof Player p)) {
                return true;
            }

            String discordLink = "https://discord.gg/example"; // Replace with your actual Discord invite link

            TextComponent component = new TextComponent(CC.GREEN + "Click here to join the discord.");
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Click to join Discord").create()));
            component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, discordLink));

            p.spigot().sendMessage(component);

            return true;
        });
    }
}

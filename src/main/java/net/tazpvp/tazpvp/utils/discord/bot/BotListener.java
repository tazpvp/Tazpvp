package net.tazpvp.tazpvp.utils.discord.bot;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class BotListener extends ListenerAdapter {
    private final long channelId;

    public BotListener(long channelId) {
        this.channelId = channelId;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        final MessageChannelUnion messageChannelUnion = event.getChannel();

        if (messageChannelUnion.getIdLong() != channelId) {
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        final String prefix = ChatFunctions.gradient("#db3bff", "DISCORD", true);
        final String name = ChatColor.GRAY + event.getAuthor().getEffectiveName();
        final String message = ChatColor.WHITE + event.getMessage().getContentStripped();

        final String completion = String.format("%s %s %s", prefix, name, message);

        Bukkit.broadcastMessage(completion);
    }
}

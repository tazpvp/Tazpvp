package net.tazpvp.tazpvp.utils.discord.webhook;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;

import java.awt.*;

@Getter
public class LoggingWebhook {
    private DiscordWebhook discordWebhook;
    private DiscordWebhook.EmbedObject embedObject;

    public LoggingWebhook(String title) {
        discordWebhook = new DiscordWebhook(Tazpvp.getInstance().getConfig().getString("report-webhook"));

        embedObject = new DiscordWebhook.EmbedObject();
        embedObject.setTitle(title);
        embedObject.setFooter("Uh oh Stinky Log", null);
        embedObject.setColor(Color.GREEN);

        discordWebhook.addEmbed(embedObject);
    }
}

package net.tazpvp.tazpvp.utils.discord.webhook;

public class MuteWebhook extends LoggingWebhook {
    public MuteWebhook(String bannedPlayerName, String whoMuted, String reason) {
        super("Mute Log");

        getEmbedObject().addField("IGN", bannedPlayerName, true );
        getEmbedObject().addField("Muted By", whoMuted, true);
        getEmbedObject().addField("Reason", reason, true);

        getDiscordWebhook().execute();
    }
}

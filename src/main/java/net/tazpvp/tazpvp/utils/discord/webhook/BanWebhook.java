package net.tazpvp.tazpvp.utils.discord.webhook;

public class BanWebhook extends LoggingWebhook {
    public BanWebhook(String bannedPlayerName, String whoBanned, String reason) {
        super("Ban Log");

        getEmbedObject().addField("IGN", bannedPlayerName, true );
        getEmbedObject().addField("Banned By", whoBanned, true);
        getEmbedObject().addField("Reason", reason, true);

        getDiscordWebhook().execute();
    }
}

package net.tazpvp.tazpvp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ScoreboardEnum {
    RANK(CC.DARK_AQUA.toString(), "rank", "✦ ʀᴀɴᴋ"),
    LEVEL(CC.AQUA.toString(), "level", "✳ ʟᴇᴠᴇʟ"),
    COINS(CC.GOLD.toString(), "coins", "❂ ᴄᴏɪɴꜱ"),
    KILLS(CC.YELLOW.toString(), "kills", "⚔ ᴋɪʟʟꜱ"),
    DEATHS(CC.DARK_PURPLE.toString(), "deaths", "☠ ᴅᴇᴀᴛʜꜱ"),
    KDR(CC.GRAY.toString(), "kdr", "✚ ᴋᴅʀ");

    private final String id;
    private final String name;
    private final String prefix;

}

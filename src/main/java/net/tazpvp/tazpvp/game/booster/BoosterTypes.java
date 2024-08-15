package net.tazpvp.tazpvp.game.booster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.ChatHelper;

@Getter
@AllArgsConstructor
public enum BoosterTypes {
    XP(CC.GREEN + CC.BOLD.toString() + "XP Booster", 1.20),
    COINS(CC.GOLD + CC.BOLD.toString() + "Coin Booster", 1.20),
    MEGA(ChatHelper.gradient("#ba8fdb", "MEGA Booster", true), 1.25);

    private final String name;
    private final double multiplier;
}

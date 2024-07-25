package net.tazpvp.tazpvp.utils.leaderboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import org.checkerframework.common.value.qual.ArrayLen;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum LeaderboardEnum {
    KILLS("kills", PlayerStatEntity::getKills),
    DEATHS("deaths", PlayerStatEntity::getDeaths),
    PRESTIGE("prestige", PlayerStatEntity::getPrestige),
    LEVEL("level", PlayerStatEntity::getLevel),
    COINS("coins", PlayerStatEntity::getCoins),;

    private final String columnName;
    private final Function<PlayerStatEntity, Integer> statEntityIntegerFunction;
}

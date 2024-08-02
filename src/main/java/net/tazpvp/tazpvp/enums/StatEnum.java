package net.tazpvp.tazpvp.enums;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Getter
public enum StatEnum {
    COINS(PlayerStatEntity::setCoins, PlayerStatEntity::getCoins, Integer.class, ScoreboardEnum.COINS),
    XP(PlayerStatEntity::setXp, PlayerStatEntity::getXp, Integer.class),
    LEVEL(PlayerStatEntity::setLevel, PlayerStatEntity::getLevel, Integer.class, ScoreboardEnum.LEVEL),
    MMR(PlayerStatEntity::setMMR, PlayerStatEntity::getMMR, Integer.class),
    DUEL_MMR(PlayerStatEntity::setDuelMMR, PlayerStatEntity::getDuelMMR, Integer.class),
    KILLS(PlayerStatEntity::setKills, PlayerStatEntity::getKills, Integer.class, ScoreboardEnum.KILLS),
    DEATHS(PlayerStatEntity::setDeaths, PlayerStatEntity::getDeaths, Integer.class, ScoreboardEnum.DEATHS),
    PRESTIGE(PlayerStatEntity::setPrestige, PlayerStatEntity::getPrestige, Integer.class),
    PLAYTIME(PlayerStatEntity::setPlaytime, PlayerStatEntity::getPlaytime, Long.class),
    LAST_CLAIM(PlayerStatEntity::setLastClaim, PlayerStatEntity::getLastClaim, Long.class);

    private final BiConsumer<PlayerStatEntity, Number> set;
    private final Function<PlayerStatEntity, Number> get;
    private final Class<? extends Number> type;
    private final ScoreboardEnum scoreboardEnum;
    private static final PlayerStatService playerStatService = Tazpvp.getInstance().getPlayerStatService();

    <T extends Number> StatEnum(BiConsumer<PlayerStatEntity, T> setter, Function<PlayerStatEntity, T> getter, Class<T> type, ScoreboardEnum scoreboardEnum) {
        this.scoreboardEnum = scoreboardEnum;
        this.set = (entity, value) -> setter.accept(entity, type.cast(value));
        this.get = getter::apply;
        this.type = type;
    }

    <T extends Number> StatEnum(BiConsumer<PlayerStatEntity, T> setter, Function<PlayerStatEntity, T> getter, Class<T> type) {
        this.scoreboardEnum = null;
        this.set = (entity, value) -> setter.accept(entity, type.cast(value));
        this.get = getter::apply;
        this.type = type;
    }

    public void add(UUID id, Number value) {
        PlayerStatEntity playerStatEntity = playerStatService.getOrDefault(id);
        Number currentValue = get.apply(playerStatEntity);
        if (type == Integer.class) {
            if (scoreboardEnum != null) {
                playerStatService.set(id, currentValue.intValue() + value.intValue(), set, scoreboardEnum);
            } else {
                playerStatService.set(id, currentValue.intValue() + value.intValue(), set);
            }
        } else {
            if (scoreboardEnum != null) {
                playerStatService.set(id, currentValue.longValue() + value.longValue(), set, scoreboardEnum);
            } else {
                playerStatService.set(id, currentValue.longValue() + value.longValue(), set);
            }
        }
    }

    public void remove(UUID id, Number value) {
        PlayerStatEntity playerStatEntity = playerStatService.getOrDefault(id);
        Number currentValue = get.apply(playerStatEntity);
        if (type == Integer.class) {
            if (scoreboardEnum != null) {
                playerStatService.set(id, currentValue.intValue() - value.intValue(), set, scoreboardEnum);
            } else {
                playerStatService.set(id, currentValue.intValue() - value.intValue(), set);
            }
        } else {
            if (scoreboardEnum != null) {
                playerStatService.set(id, currentValue.longValue() - value.longValue(), set, scoreboardEnum);
            } else {
                playerStatService.set(id, currentValue.longValue() - value.longValue(), set);
            }
        }
    }

    public void set(UUID id, Number value) {
        if (scoreboardEnum != null) {
            playerStatService.set(id, value, set, scoreboardEnum);
        } else {
            playerStatService.set(id, value, set);
        }
    }

    public Number get(UUID id) {
        PlayerStatEntity playerStatEntity = playerStatService.getOrDefault(id);
        return get.apply(playerStatEntity);
    }
}
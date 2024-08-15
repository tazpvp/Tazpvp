package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.enums.ScoreboardEnum;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface PlayerStatService extends DataService {
    Dao<PlayerStatEntity, UUID> getDao();

    PlayerStatEntity getOrDefault(UUID uuid);

    void save(PlayerStatEntity playerStatEntity);

    void delete(PlayerStatEntity playerStatEntity);

    List<PlayerStatEntity> getTop10Most(String columnName);

    void set(UUID id, Number value, BiConsumer<PlayerStatEntity, Number> consumer);

    void set(UUID id, Number value, BiConsumer<PlayerStatEntity, Number> consumer, ScoreboardEnum scoreboardEnum);
}

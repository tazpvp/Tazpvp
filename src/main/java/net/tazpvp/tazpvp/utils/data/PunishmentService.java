package net.tazpvp.tazpvp.utils.data;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.utils.data.entity.PunishmentEntity;
import net.tazpvp.tazpvp.utils.data.entity.RankEntity;

import java.util.UUID;

public interface PunishmentService extends DataService {
    Dao<PunishmentEntity, UUID> getUserDao();
    void savePunishmentEntity(PunishmentEntity rankEntity);
    PunishmentEntity getPunishmentEntity(UUID uuid);
    boolean punishmentEntityExists(UUID uuid);
    PunishmentEntity getOrDefault(UUID uuid);
    void punish(UUID uuid, PunishmentType punishmentType, final long time, final String reason);
    void unpunish(UUID uuid);
    long getTimeRemaining(UUID uuid);
    boolean isPunished(UUID uuid);
    PunishmentType getPunishment(UUID uuid);

    enum PunishmentType {
        MUTED, BANNED
    }
}

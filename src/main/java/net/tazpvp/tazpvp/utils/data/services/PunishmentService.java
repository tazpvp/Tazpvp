package net.tazpvp.tazpvp.utils.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.utils.data.DataService;
import net.tazpvp.tazpvp.utils.data.entity.PunishmentEntity;

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

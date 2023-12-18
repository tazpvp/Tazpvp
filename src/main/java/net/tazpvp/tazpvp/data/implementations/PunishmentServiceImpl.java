package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PunishmentEntity;
import net.tazpvp.tazpvp.data.services.PunishmentService;

import java.sql.SQLException;
import java.util.UUID;

public class PunishmentServiceImpl implements PunishmentService {
    @Override
    public Dao<PunishmentEntity, UUID> getUserDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), PunishmentEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePunishmentEntity(final PunishmentEntity rankEntity) {
        try {
            getUserDao().createOrUpdate(rankEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PunishmentEntity getPunishmentEntity(final UUID uuid) {
        try {
            return getUserDao().queryForId(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean punishmentEntityExists(final UUID uuid) {
        try {
            return getUserDao().idExists(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PunishmentEntity getOrDefault(final UUID uuid) {
        if (punishmentEntityExists(uuid)) {
            return getPunishmentEntity(uuid);
        } else {
            return new PunishmentEntity(uuid, 0, null, null);
        }
    }

    @Override
    public void punish(final UUID uuid, final PunishmentType punishmentType, final long time, final String reason) {
        unpunish(uuid);
        savePunishmentEntity(new PunishmentEntity(uuid, time, punishmentType.toString(), reason));
    }

    @Override
    public void unpunish(final UUID uuid) {
        if (punishmentEntityExists(uuid)) {
            try {
                getUserDao().deleteById(uuid);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public long getTimeRemaining(final UUID uuid) {
        if (punishmentEntityExists(uuid)) {
            return getOrDefault(uuid).getTimestamp() - System.currentTimeMillis();
        }
        return 0;
    }

    @Override
    public boolean isPunished(final UUID uuid) {
        return punishmentEntityExists(uuid);
    }

    @Override
    public PunishmentType getPunishment(final UUID uuid) {
        final PunishmentEntity punishmentEntity = getPunishmentEntity(uuid);
        if (punishmentEntity == null) {
            return null;
        }
        return PunishmentType.valueOf(getPunishmentEntity(uuid).getPunishmentType());
    }
}

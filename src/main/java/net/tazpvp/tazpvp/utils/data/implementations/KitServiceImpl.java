package net.tazpvp.tazpvp.utils.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.data.entity.KitEntity;
import net.tazpvp.tazpvp.utils.data.services.KitService;

import java.sql.SQLException;
import java.util.UUID;

public class KitServiceImpl implements KitService {
    @Override
    public Dao<KitEntity, UUID> getUserDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), KitEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveKitEntity(final KitEntity kitEntity) {
        try {
            getUserDao().createOrUpdate(kitEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public KitEntity getKitEntity(final UUID uuid) {
        try {
            return getUserDao().queryForId(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean kitEntityExists(final UUID uuid) {
        try {
            return getUserDao().idExists(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public KitEntity getOrDefault(final UUID uuid) {
        if (kitEntityExists(uuid)) {
            return getKitEntity(uuid);
        } else {
            return new KitEntity(uuid, "");
        }
    }
}

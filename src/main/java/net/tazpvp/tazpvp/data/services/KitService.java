package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.KitEntity;

import java.util.UUID;

public interface KitService extends DataService {
    Dao<KitEntity, UUID> getUserDao();
    void saveKitEntity(KitEntity kitEntity);
    KitEntity getKitEntity(UUID uuid);
    boolean kitEntityExists(UUID uuid);
    KitEntity getOrDefault(UUID uuid);
}

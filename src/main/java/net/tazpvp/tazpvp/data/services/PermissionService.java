package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.entity.KitEntity;
import net.tazpvp.tazpvp.data.entity.PermissionEntity;

import java.util.UUID;

public interface PermissionService extends DataService {
    Dao<PermissionEntity, Integer> getUserDao();
    PermissionEntity createPermission(GameRankEntity gameRankEntity, String permission);
    PermissionEntity findByPermission(GameRankEntity gameRankEntity, String name);
    void deletePermission(PermissionEntity permissionEntity);
}

package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.query.In;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.database.PostgresqlDatabase;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.entity.PermissionEntity;
import net.tazpvp.tazpvp.data.services.GameRankService;
import net.tazpvp.tazpvp.data.services.PermissionService;

import java.sql.SQLException;
import java.util.List;

public class GameRankServiceImpl implements GameRankService {

    @Override
    public Dao<GameRankEntity, Integer> getUserDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), GameRankEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getPermissionFromRank(GameRankEntity gameRankEntity) {
        final ForeignCollection<PermissionEntity> permissions = gameRankEntity.getPermissions();

        return permissions.stream().map(PermissionEntity::getPermission).toList();
    }

    @Override
    public GameRankEntity createGameRank(String name, String prefix, int hierarchy) {
        final GameRankEntity gameRankEntity = new GameRankEntity();

        gameRankEntity.setName(name);
        gameRankEntity.setPrefix(prefix);
        gameRankEntity.setHierarchy(hierarchy);
        gameRankEntity.setWeight(5);

        saveGameRank(gameRankEntity);

        return gameRankEntity;
    }

    @Override
    public void addPermissionToGameRank(GameRankEntity gameRankEntity, String permission) {
        ForeignCollection<PermissionEntity> permissionEntities = gameRankEntity.getPermissions();
        final PermissionService permissionService = new PermissionServiceImpl();

        final PermissionEntity permissionEntity = permissionService.createPermission(gameRankEntity, permission);

        permissionEntities.add(permissionEntity);

        saveGameRank(gameRankEntity);
    }

    @Override
    public void removePermissionFromGameRank(GameRankEntity gameRankEntity, String permission) {
        final PermissionService permissionService = new PermissionServiceImpl();
        final PermissionEntity permissionEntity = permissionService.findByPermission(gameRankEntity, permission);
        final ForeignCollection<PermissionEntity> permissionEntities = gameRankEntity.getPermissions();
        permissionEntities.remove(permissionEntity);

        gameRankEntity.setPermissions(permissionEntities);
        saveGameRank(gameRankEntity);
        permissionService.deletePermission(permissionEntity);
    }

    @Override
    public void saveGameRank(GameRankEntity gameRankEntity) {
        try {
            getUserDao().createOrUpdate(gameRankEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameRankEntity getGameRankFromName(String name) {
        QueryBuilder<GameRankEntity, Integer> queryBuilder = getUserDao().queryBuilder();

        try {
            queryBuilder.where().eq("name", name);

            return queryBuilder.queryForFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameRankEntity getOrCreateIfNotExists(String name, String prefix, int hierarchy) {
        GameRankEntity gameRankEntity = getGameRankFromName(name);
        if (gameRankEntity == null) {
            final GameRankEntity gameRankEntity1 = createGameRank(name, prefix, hierarchy);
            saveGameRank(gameRankEntity1);
            return gameRankEntity1;
        }
        return gameRankEntity;
    }

    @Override
    public List<String> getAllGameRanks() {
        try {
            return getUserDao().queryForAll().stream().map(GameRankEntity::getName).toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

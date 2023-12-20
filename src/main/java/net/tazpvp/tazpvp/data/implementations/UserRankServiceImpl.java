package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.ExpirationRankEntity;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.entity.UserRankEntity;
import net.tazpvp.tazpvp.data.services.ExpirationRankService;
import net.tazpvp.tazpvp.data.services.GameRankService;
import net.tazpvp.tazpvp.data.services.UserRankService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class UserRankServiceImpl implements UserRankService {
    @Override
    public Dao<UserRankEntity, UUID> getUserDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), UserRankEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUserRankEntity(UserRankEntity userRankEntity) {
        try {
            getUserDao().createOrUpdate(userRankEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRankEntity getUserRankEntity(UUID uuid) {
        try {
            return getUserDao().queryForId(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean userRankEntityExists(UUID uuid) {
        try {
            return getUserDao().idExists(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRankEntity getOrDefault(UUID uuid) {
        final UserRankEntity userRankEntity = getUserRankEntity(uuid);

        if (userRankEntity == null) {
            final UserRankEntity userRankEntityTemp = new UserRankEntity();
            userRankEntityTemp.setUuid(uuid);
            userRankEntityTemp.setDeathParticle(null);
            userRankEntityTemp.setArrowParticle(null);
            userRankEntityTemp.setCustomPrefix(null);
            try {
                getUserDao().assignEmptyForeignCollection(userRankEntityTemp, "ranks");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ForeignCollection<ExpirationRankEntity> expirationRankEntities = userRankEntityTemp.getRanks();

            final ExpirationRankService expirationRankService = new ExpirationRankServiceImpl();
            final GameRankService gameRankService = new GameRankServiceImpl();

            final ExpirationRankEntity expirationRankEntity = expirationRankService
                    .createExpirationRank(userRankEntityTemp,
                            gameRankService.getOrCreateIfNotExists("default", "", 0),
                            0L);

            expirationRankEntities.add(expirationRankEntity);
            userRankEntityTemp.setRanks(expirationRankEntities);

            return userRankEntityTemp;
        }

        return userRankEntity;
    }

    @Override
    public UserRankEntity addRank(UserRankEntity userRankEntity, GameRankEntity gameRankEntity) {
        return addExpiringRank(userRankEntity, gameRankEntity, 0L);
    }

    @Override
    public UserRankEntity addExpiringRank(UserRankEntity userRankEntity, GameRankEntity gameRankEntity, long timestamp) {
        final ForeignCollection<ExpirationRankEntity> expirationRankEntities = userRankEntity.getRanks();

        final ExpirationRankService expirationRankService = new ExpirationRankServiceImpl();
        expirationRankEntities.add(expirationRankService.createExpirationRank(userRankEntity, gameRankEntity, timestamp));

        userRankEntity.setRanks(expirationRankEntities);
        saveUserRankEntity(userRankEntity);

        return userRankEntity;
    }

    @Override
    public void removeExpiringRank(UserRankEntity userRankEntity, GameRankEntity gameRankEntity) {
        final ForeignCollection<ExpirationRankEntity> expirationRankEntities = userRankEntity.getRanks();

        final List<ExpirationRankEntity> filteredEntities = expirationRankEntities
                .stream()
                .filter(expirationRankEntity -> expirationRankEntity.getGameRankEntity().getId() != gameRankEntity.getId())
                .toList();


        expirationRankEntities.clear();
        expirationRankEntities.addAll(filteredEntities);

        userRankEntity.setRanks(expirationRankEntities);
        saveUserRankEntity(userRankEntity);
    }

    @Override
    public GameRankEntity getHighestRank(UserRankEntity userRankEntity) {

        final List<GameRankEntity> gameRankEntities = userRankEntity.getRanks().stream()
                .map(ExpirationRankEntity::getGameRankEntity)
                .toList();

        return gameRankEntities.stream().max(Comparator.comparing(GameRankEntity::getHierarchy)).get();
    }

    @Override
    public List<String> getPermissions(UserRankEntity userRankEntity) {
        final List<String> perms = new ArrayList<>();

        userRankEntity.getRanks()
                .forEach(expirationRankEntity -> expirationRankEntity.getGameRankEntity().getPermissions()
                        .forEach(permissionEntity -> perms.add(permissionEntity.getPermission())));

        return perms;
    }

    @Override
    public String getHighestPrefix(UserRankEntity userRankEntity) {
        return getHighestRank(userRankEntity).getPrefix();
    }

    @Override
    public boolean hasRank(UserRankEntity userRankEntity, String rankName) {

        List<String> ranks = userRankEntity.getRanks()
                .stream()
                .map(ExpirationRankEntity::getGameRankEntity)
                .map(GameRankEntity::getName)
                .filter(name -> name.equalsIgnoreCase(rankName))
                .toList();

        return !ranks.isEmpty();
    }

    @Override
    public void removeAllExpiredRanks(UserRankEntity userRankEntity) {
        final ExpirationRankService expirationRankService = new ExpirationRankServiceImpl();
        final ForeignCollection<ExpirationRankEntity> expirationRankEntities = userRankEntity.getRanks();

        final List<ExpirationRankEntity> entitiesFiltered = expirationRankEntities
                .stream()
                .filter(entity -> !expirationRankService.hasRankExpired(entity))
                .toList();

        expirationRankEntities.clear();
        expirationRankEntities.addAll(entitiesFiltered);
        saveUserRankEntity(userRankEntity);
    }
}

package net.tazpvp.tazpvp.data.services;

import com.j256.ormlite.dao.Dao;
import net.tazpvp.tazpvp.data.DataService;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;

import java.util.List;

public interface GameRankService extends DataService {
    Dao<GameRankEntity, Integer> getUserDao();
    List<String> getPermissionFromRank(GameRankEntity gameRankEntity);
    GameRankEntity createGameRank(String name, String prefix, int hierarchy);
    void addPermissionToGameRank(GameRankEntity gameRankEntity, String permission);
    void removePermissionFromGameRank(GameRankEntity gameRankEntity, String permission);
    void saveGameRank(GameRankEntity gameRankEntity);
    GameRankEntity getGameRankFromName(String name);
    GameRankEntity getOrCreateIfNotExists(String name, String prefix, int hierarchy);
    List<String> getAllGameRanks();

}

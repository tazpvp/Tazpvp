package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.data.services.TalentService;

import java.sql.SQLException;
import java.util.UUID;

public class TalentServiceImpl implements TalentService {

    //data access object
    @Override
    public Dao<TalentEntity, UUID> getUserDao() {
        try {
            return DaoManager.createDao(Tazpvp.getPostgresqlDatabase().getConnectionSource(), TalentEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveTalentEntity(TalentEntity talentEntity) {
        try {
            getUserDao().createOrUpdate(talentEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TalentEntity getTalentEntity(UUID uuid) {
        try {
            return getUserDao().queryForId(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean talentEntityExists(UUID uuid) {
        try {
            return getUserDao().idExists(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TalentEntity getOrDefault(UUID uuid) {
        TalentEntity talentEntity = getTalentEntity(uuid);

        if (talentEntity == null) {
            TalentEntity talentEntity1 = new TalentEntity();
            talentEntity1.setUuid(uuid);

            return talentEntity1;
        }
        return talentEntity;
    }
}

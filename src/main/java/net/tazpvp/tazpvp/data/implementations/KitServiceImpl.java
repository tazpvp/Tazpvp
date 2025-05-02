package net.tazpvp.tazpvp.data.implementations;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.KitEntity;
import net.tazpvp.tazpvp.data.services.KitService;

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
            return new KitEntity(uuid, "rO0ABXcEAAAAKXNyABpvcmcuYnVra2l0LnV0aWwuaW8uV3JhcHBlcvJQR+zxEm8FAgABTAADbWFw\n" +
                    "dAAPTGphdmEvdXRpbC9NYXA7eHBzcgA1Y29tLmdvb2dsZS5jb21tb24uY29sbGVjdC5JbW11dGFi\n" +
                    "bGVNYXAkU2VyaWFsaXplZEZvcm0AAAAAAAAAAAIAAkwABGtleXN0ABJMamF2YS9sYW5nL09iamVj\n" +
                    "dDtMAAZ2YWx1ZXNxAH4ABHhwdXIAE1tMamF2YS5sYW5nLk9iamVjdDuQzlifEHMpbAIAAHhwAAAA\n" +
                    "BHQAAj09dAABdnQABHR5cGV0AARtZXRhdXEAfgAGAAAABHQAHm9yZy5idWtraXQuaW52ZW50b3J5\n" +
                    "Lkl0ZW1TdGFja3NyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2\n" +
                    "YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAA9xdAAPTkVUSEVSSVRFX1NXT1JEc3EAfgAAc3EA\n" +
                    "fgADdXEAfgAGAAAAA3EAfgAIdAAJbWV0YS10eXBldAAIZW5jaGFudHN1cQB+AAYAAAADdAAISXRl\n" +
                    "bU1ldGF0AApVTlNQRUNJRklDc3EAfgADdXEAfgAGAAAABXQAE21pbmVjcmFmdDprbm9ja2JhY2t0\n" +
                    "ABFtaW5lY3JhZnQ6bWVuZGluZ3QAE21pbmVjcmFmdDpzaGFycG5lc3N0ABdtaW5lY3JhZnQ6c3dl\n" +
                    "ZXBpbmdfZWRnZXQAFG1pbmVjcmFmdDp1bmJyZWFraW5ndXEAfgAGAAAABXNxAH4ADgAAAAFxAH4A\n" +
                    "InNxAH4ADgAAAAVzcQB+AA4AAAADcQB+ACRzcQB+AABzcQB+AAN1cQB+AAYAAAAEcQB+AAhxAH4A\n" +
                    "CXEAfgAKdAAGYW1vdW50dXEAfgAGAAAABHEAfgANc3EAfgAOAAAPcXQAC0VOREVSX1BFQVJMc3EA\n" +
                    "fgAOAAAAEHNxAH4AAHNxAH4AA3VxAH4ABgAAAARxAH4ACHEAfgAJcQB+AApxAH4AKHVxAH4ABgAA\n" +
                    "AARxAH4ADXNxAH4ADgAAD3F0AAxHT0xERU5fQVBQTEVzcQB+AA4AAABAc3EAfgAAc3EAfgADdXEA\n" +
                    "fgAGAAAABHEAfgAIcQB+AAlxAH4ACnEAfgAodXEAfgAGAAAABHEAfgANc3EAfgAOAAAPcXQACE9C\n" +
                    "U0lESUFOcQB+ADNzcQB+AABzcQB+AAN1cQB+AAYAAAAEcQB+AAhxAH4ACXEAfgAKcQB+ACh1cQB+\n" +
                    "AAYAAAAEcQB+AA1zcQB+AA4AAA9xdAALRU5EX0NSWVNUQUxxAH4AM3NxAH4AAHNxAH4AA3VxAH4A\n" +
                    "BgAAAARxAH4ACHEAfgAJcQB+AApxAH4AC3VxAH4ABgAAAARxAH4ADXNxAH4ADgAAD3F0AAhDUk9T\n" +
                    "U0JPV3NxAH4AAHNxAH4AA3VxAH4ABgAAAANxAH4ACHEAfgAVcQB+ABZ1cQB+AAYAAAADcQB+ABhx\n" +
                    "AH4ARXNxAH4AA3VxAH4ABgAAAAR0ABFtaW5lY3JhZnQ6bWVuZGluZ3QAE21pbmVjcmFmdDptdWx0\n" +
                    "aXNob3R0ABZtaW5lY3JhZnQ6cXVpY2tfY2hhcmdldAAUbWluZWNyYWZ0OnVuYnJlYWtpbmd1cQB+\n" +
                    "AAYAAAAEcQB+ACJxAH4AInEAfgAkcQB+ACRzcQB+AABzcQB+AAN1cQB+AAYAAAADcQB+AAhxAH4A\n" +
                    "CXEAfgAKdXEAfgAGAAAAA3EAfgANc3EAfgAOAAAPcXQAEFRPVEVNX09GX1VORFlJTkdzcQB+AABz\n" +
                    "cQB+AAN1cQB+AAYAAAAEcQB+AAhxAH4ACXEAfgAKcQB+AAt1cQB+AAYAAAAEcQB+AA1zcQB+AA4A\n" +
                    "AA9xdAANU1BMQVNIX1BPVElPTnNxAH4AAHNxAH4AA3VxAH4ABgAAAARxAH4ACHEAfgAVdAALcG90\n" +
                    "aW9uLXR5cGV0AA5jdXN0b20tZWZmZWN0c3VxAH4ABgAAAARxAH4AGHQABlBPVElPTnQAGG1pbmVj\n" +
                    "cmFmdDpsb25nX3N3aWZ0bmVzc3NyADZjb20uZ29vZ2xlLmNvbW1vbi5jb2xsZWN0LkltbXV0YWJs\n" +
                    "ZUxpc3QkU2VyaWFsaXplZEZvcm0AAAAAAAAAAAIAAVsACGVsZW1lbnRzdAATW0xqYXZhL2xhbmcv\n" +
                    "T2JqZWN0O3hwdXEAfgAGAAAAAHNxAH4AAHNxAH4AA3VxAH4ABgAAAARxAH4ACHEAfgAJcQB+AApx\n" +
                    "AH4AC3VxAH4ABgAAAARxAH4ADXNxAH4ADgAAD3FxAH4AXHNxAH4AAHNxAH4AA3VxAH4ABgAAAARx\n" +
                    "AH4ACHEAfgAVcQB+AGBxAH4AYXVxAH4ABgAAAARxAH4AGHEAfgBjdAAbbWluZWNyYWZ0Omxvbmdf\n" +
                    "cmVnZW5lcmF0aW9ucQB+AGdzcQB+AABzcQB+AAN1cQB+AAYAAAAFcQB+AAhxAH4ACXEAfgAKcQB+\n" +
                    "AChxAH4AC3VxAH4ABgAAAAVxAH4ADXNxAH4ADgAAD3F0AAxUSVBQRURfQVJST1dxAH4AM3NxAH4A\n" +
                    "AHNxAH4AA3VxAH4ABgAAAARxAH4ACHEAfgAVcQB+AGBxAH4AYXVxAH4ABgAAAARxAH4AGHEAfgBj\n" +
                    "dAAbbWluZWNyYWZ0Omxvbmdfc2xvd19mYWxsaW5ncQB+AGdzcQB+AABzcQB+AAN1cQB+AAYAAAAE\n" +
                    "cQB+AAhxAH4ACXEAfgAKcQB+AAt1cQB+AAYAAAAEcQB+AA1zcQB+AA4AAA9xdAARTkVUSEVSSVRF\n" +
                    "X1BJQ0tBWEVzcQB+AABzcQB+AAN1cQB+AAYAAAADcQB+AAhxAH4AFXEAfgAWdXEAfgAGAAAAA3EA\n" +
                    "fgAYcQB+ABlzcQB+AAN1cQB+AAYAAAADdAAUbWluZWNyYWZ0OmVmZmljaWVuY3l0ABFtaW5lY3Jh\n" +
                    "ZnQ6bWVuZGluZ3QAFG1pbmVjcmFmdDp1bmJyZWFraW5ndXEAfgAGAAAAA3EAfgAjcQB+ACJxAH4A\n" +
                    "JHNxAH4AAHNxAH4AA3VxAH4ABgAAAANxAH4ACHEAfgAJcQB+AAp1cQB+AAYAAAADcQB+AA1zcQB+\n" +
                    "AA4AAA9xcQB+AFZzcQB+AABzcQB+AAN1cQB+AAYAAAADcQB+AAhxAH4ACXEAfgAKdXEAfgAGAAAA\n" +
                    "A3EAfgANc3EAfgAOAAAPcXEAfgBWc3EAfgAAc3EAfgADdXEAfgAGAAAAA3EAfgAIcQB+AAlxAH4A\n" +
                    "CnVxAH4ABgAAAANxAH4ADXNxAH4ADgAAD3FxAH4AVnNxAH4AAHNxAH4AA3VxAH4ABgAAAANxAH4A\n" +
                    "CHEAfgAJcQB+AAp1cQB+AAYAAAADcQB+AA1zcQB+AA4AAA9xcQB+AFZzcQB+AABzcQB+AAN1cQB+\n" +
                    "AAYAAAADcQB+AAhxAH4ACXEAfgAKdXEAfgAGAAAAA3EAfgANc3EAfgAOAAAPcXEAfgBWc3EAfgAA\n" +
                    "c3EAfgADdXEAfgAGAAAABHEAfgAIcQB+AAlxAH4ACnEAfgALdXEAfgAGAAAABHEAfgANc3EAfgAO\n" +
                    "AAAPcXEAfgBcc3EAfgAAc3EAfgADdXEAfgAGAAAABHEAfgAIcQB+ABVxAH4AYHEAfgBhdXEAfgAG\n" +
                    "AAAABHEAfgAYcQB+AGN0ABhtaW5lY3JhZnQ6bG9uZ19zd2lmdG5lc3NxAH4AZ3NxAH4AAHNxAH4A\n" +
                    "A3VxAH4ABgAAAARxAH4ACHEAfgAJcQB+AApxAH4AC3VxAH4ABgAAAARxAH4ADXNxAH4ADgAAD3Fx\n" +
                    "AH4AXHNxAH4AAHNxAH4AA3VxAH4ABgAAAARxAH4ACHEAfgAVcQB+AGBxAH4AYXVxAH4ABgAAAARx\n" +
                    "AH4AGHEAfgBjdAAbbWluZWNyYWZ0OmxvbmdfcmVnZW5lcmF0aW9ucQB+AGdzcQB+AABzcQB+AAN1\n" +
                    "cQB+AAYAAAAEcQB+AAhxAH4ACXEAfgAKcQB+ACh1cQB+AAYAAAAEcQB+AA1zcQB+AA4AAA9xcQB+\n" +
                    "ACtxAH4ALHNxAH4AAHNxAH4AA3VxAH4ABgAAAARxAH4ACHEAfgAJcQB+AApxAH4AKHVxAH4ABgAA\n" +
                    "AARxAH4ADXNxAH4ADgAAD3FxAH4AK3EAfgAsc3EAfgAAc3EAfgADdXEAfgAGAAAAA3EAfgAIcQB+\n" +
                    "AAlxAH4ACnVxAH4ABgAAAANxAH4ADXNxAH4ADgAAD3FxAH4AVnNxAH4AAHNxAH4AA3VxAH4ABgAA\n" +
                    "AANxAH4ACHEAfgAJcQB+AAp1cQB+AAYAAAADcQB+AA1zcQB+AA4AAA9xcQB+AFZzcQB+AABzcQB+\n" +
                    "AAN1cQB+AAYAAAADcQB+AAhxAH4ACXEAfgAKdXEAfgAGAAAAA3EAfgANc3EAfgAOAAAPcXEAfgBW\n" +
                    "c3EAfgAAc3EAfgADdXEAfgAGAAAAA3EAfgAIcQB+AAlxAH4ACnVxAH4ABgAAAANxAH4ADXNxAH4A\n" +
                    "DgAAD3FxAH4AVnNxAH4AAHNxAH4AA3VxAH4ABgAAAANxAH4ACHEAfgAJcQB+AAp1cQB+AAYAAAAD\n" +
                    "cQB+AA1zcQB+AA4AAA9xcQB+AFZzcQB+AABzcQB+AAN1cQB+AAYAAAAEcQB+AAhxAH4ACXEAfgAK\n" +
                    "cQB+AAt1cQB+AAYAAAAEcQB+AA1zcQB+AA4AAA9xcQB+AFxzcQB+AABzcQB+AAN1cQB+AAYAAAAE\n" +
                    "cQB+AAhxAH4AFXEAfgBgcQB+AGF1cQB+AAYAAAAEcQB+ABhxAH4AY3QAGG1pbmVjcmFmdDpsb25n\n" +
                    "X3N3aWZ0bmVzc3EAfgBnc3EAfgAAc3EAfgADdXEAfgAGAAAABHEAfgAIcQB+AAlxAH4ACnEAfgAL\n" +
                    "dXEAfgAGAAAABHEAfgANc3EAfgAOAAAPcXEAfgBcc3EAfgAAc3EAfgADdXEAfgAGAAAABHEAfgAI\n" +
                    "cQB+ABVxAH4AYHEAfgBhdXEAfgAGAAAABHEAfgAYcQB+AGN0ABttaW5lY3JhZnQ6bG9uZ19yZWdl\n" +
                    "bmVyYXRpb25xAH4AZ3NxAH4AAHNxAH4AA3VxAH4ABgAAAARxAH4ACHEAfgAJcQB+AApxAH4AKHVx\n" +
                    "AH4ABgAAAARxAH4ADXNxAH4ADgAAD3FxAH4AK3EAfgAsc3EAfgAAc3EAfgADdXEAfgAGAAAABHEA\n" +
                    "fgAIcQB+AAlxAH4ACnEAfgAodXEAfgAGAAAABHEAfgANc3EAfgAOAAAPcXEAfgArcQB+ACxzcQB+\n" +
                    "AABzcQB+AAN1cQB+AAYAAAAEcQB+AAhxAH4ACXEAfgAKcQB+ACh1cQB+AAYAAAAEcQB+AA1zcQB+\n" +
                    "AA4AAA9xcQB+ACtxAH4ALHNxAH4AAHNxAH4AA3VxAH4ABgAAAARxAH4ACHEAfgAJcQB+AApxAH4A\n" +
                    "KHVxAH4ABgAAAARxAH4ADXNxAH4ADgAAD3FxAH4AOXEAfgAzc3EAfgAAc3EAfgADdXEAfgAGAAAA\n" +
                    "BHEAfgAIcQB+AAlxAH4ACnEAfgAodXEAfgAGAAAABHEAfgANc3EAfgAOAAAPcXEAfgA/cQB+ADNz\n" +
                    "cQB+AABzcQB+AAN1cQB+AAYAAAAEcQB+AAhxAH4ACXEAfgAKcQB+ACh1cQB+AAYAAAAEcQB+AA1z\n" +
                    "cQB+AA4AAA9xdAARRVhQRVJJRU5DRV9CT1RUTEVxAH4AM3NxAH4AAHNxAH4AA3VxAH4ABgAAAARx\n" +
                    "AH4ACHEAfgAJcQB+AApxAH4AKHVxAH4ABgAAAARxAH4ADXNxAH4ADgAAD3FxAH4BEHEAfgAzc3EA\n" +
                    "fgAAc3EAfgADdXEAfgAGAAAABHEAfgAIcQB+AAlxAH4ACnEAfgAodXEAfgAGAAAABHEAfgANc3EA\n" +
                    "fgAOAAAPcXQADlJFU1BBV05fQU5DSE9ScQB+ADNzcQB+AABzcQB+AAN1cQB+AAYAAAAEcQB+AAhx\n" +
                    "AH4ACXEAfgAKcQB+ACh1cQB+AAYAAAAEcQB+AA1zcQB+AA4AAA9xdAAJR0xPV1NUT05FcQB+ADNw\n" +
                    "cHBwcA==\n");
        }
    }
}

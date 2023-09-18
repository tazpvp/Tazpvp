/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2023, n-tdi
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.tazpvp.tazpvp.utils.data;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.module.Column;
import world.ntdi.postglam.sql.module.Row;
import world.ntdi.postglam.sql.module.Table;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class PunishmentData extends Table {

    private static final String NAME = "punishments";
    private static final PunishmentData punishmentData;

    static {
        try {
            punishmentData = new PunishmentData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PunishmentData() throws SQLException {
        super(Tazpvp.getDatabase(), NAME, Map.entry("id", DataTypes.UUID), new LinkedHashMap<>() {{
                    put("time", DataTypes.BIGINT);
                    put("punishment", DataTypes.TEXT);
                }}
        );
    }

    public static void punish(final @NonNull UUID uuid, final @NonNull PunishmentType punishmentType, final long time) {
        try {
            if (punishmentData.doesRowExist(uuid.toString())) {
                Row punishmentRow = new Row(punishmentData, uuid.toString());
                punishmentRow.drop();
            }
            Row punishmentRow = new Row(punishmentData, uuid.toString(), String.valueOf(System.currentTimeMillis() + time), punishmentType.name());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unpunish(final @NonNull UUID uuid) {
        try {
            if (punishmentData.doesRowExist(uuid.toString())) {
                final Row punishmentRow = new Row(punishmentData, uuid.toString());
                punishmentRow.drop();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static long getTimeRemaining(final @NonNull UUID uuid) {
        try {
            if (punishmentData.doesRowExist(uuid.toString())) {
                Row punishmentRow = new Row(punishmentData, uuid.toString());
                Column timeColumn = new Column(punishmentRow.getTable(), "time");
                return (long) punishmentRow.fetch(timeColumn) - System.currentTimeMillis();
            } else {
                return 0L;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isPermanentlyPunished(final @NonNull UUID uuid) {
        return isPunished(uuid) && getTimeRemaining(uuid) == 0;
    }

    public static PunishmentType getPunishment(final @NonNull UUID uuid) {
        try {
            if (punishmentData.doesRowExist(uuid.toString())) {
                Row punishmentRow = new Row(punishmentData, uuid.toString());
                Column punishmentTypeColumn = new Column(punishmentRow.getTable(), "punishment");
                return PunishmentType.valueOf((String) punishmentRow.fetch(punishmentTypeColumn));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isPunished(final @NonNull UUID uuid) {
        try {
            return punishmentData.doesRowExist(uuid.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isMuted(final @NonNull UUID uuid) {
        return isPunished(uuid) && getPunishment(uuid) == PunishmentType.MUTED;
    }

    public enum PunishmentType {
        MUTED, BANNED, PERMANENT_BAN;
    }
}



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

import lombok.Getter;
import world.ntdi.nrcore.utils.sql.SQLHelper;

import javax.annotation.Nonnull;
import java.util.UUID;

public class PunishmentData {

    public static final String NAME = "punishments";
    public static final String ID = "id";

    public static void punish(@Nonnull final UUID uuid, @Nonnull final PunishTypes type, final long timestamp) {
        if (!isInTable(uuid)) {
            SQLHelper.initializeValues(NAME, "ID, TIME, MUTED, BANNED", "'" + uuid + "', " + timestamp + ", false, false");
            SQLHelper.updateValue(NAME, ID, uuid.toString(), type.columnName, true);
            return;
        }

        SQLHelper.deleteRow(NAME, ID, uuid.toString());
        punish(uuid, type, timestamp);
    }

    public static void unpunish(@Nonnull final UUID uuid) {
        if (isInTable(uuid)) {
            SQLHelper.deleteRow(NAME, ID, uuid.toString());
        }
    }

    public static long timeLeft(@Nonnull final UUID uuid) {
        if (!isInTable(uuid))
            throw new IllegalArgumentException(uuid + " is not in table! Should check with `isInTable()` first");

        return SQLHelper.getInt(NAME, ID, uuid.toString(), PunishTypes.TIME.getColumnIndex());
    }

    public static boolean isPunished(PunishTypes type, UUID id) {
        if (!isInTable(id)) return false; // Check if in table
        if (type == PunishTypes.MUTE && isPunished(PunishTypes.BAN, id)) return true; // Check if muted when banned
        return SQLHelper.getBool(NAME, ID, id.toString(), type.columnIndex); // Final check for punishment
    }

    public static boolean isInTable(@Nonnull final UUID uuid) {
        return SQLHelper.ifRowExists(NAME, ID, uuid.toString());
    }

    public enum PunishTypes {
        TIME("time", 2),
        BAN("muted", 3),
        MUTE("banned", 4);

        @Getter
        private final String columnName;
        @Getter
        private final int columnIndex;

        PunishTypes(@Nonnull final String columnName, final int columnIndex) {
            this.columnName = columnName;
            this.columnIndex = columnIndex;
        }
    }



}



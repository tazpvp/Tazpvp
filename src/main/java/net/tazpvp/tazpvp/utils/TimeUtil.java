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
 *
 */

package net.tazpvp.tazpvp.utils;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class TimeUtil {
    public static String howLongAgo(long timestamp) {
        Map<TimeType, Integer> counts = new HashMap<>();
        Arrays.stream(TimeType.values()).toList().forEach(timeType -> counts.put(timeType, 0));

        while (timestamp > 1000 * 60 * 60 * 24) {
            timestamp = timestamp - 1000 * 60 * 60 * 24;
            counts.put(TimeType.DAY, counts.get(TimeType.DAY) + 1);
        }
        while (timestamp > 1000 * 60 * 60) {
            timestamp = timestamp - 1000 * 60 * 60;
            counts.put(TimeType.HOUR, counts.get(TimeType.HOUR) + 1);
        }
        while (timestamp > 1000 * 60) {
            timestamp = timestamp - 1000 * 60;
            counts.put(TimeType.MINUTE, counts.get(TimeType.MINUTE) + 1);
        }
        while (timestamp > 1000) {
            timestamp = timestamp - 1000;
            counts.put(TimeType.SECOND, counts.get(TimeType.SECOND) + 1);
        }

        StringBuilder sb = new StringBuilder();
        counts.forEach((timeType, integer) -> {
            if (integer > 0) {
                sb.append(integer).append(" ").append(timeType.getSingularOrMultipleForm(integer)).append(" ");
            }
        });

        return sb.toString();
    }

    @Getter
    public enum TimeType {
        DAY("Day"), HOUR("Hour"), MINUTE("Minute"), SECOND("Second");

        private final String prettyForm;

        TimeType(String prettyForm) {
            this.prettyForm = prettyForm;
        }

        public String getSingularOrMultipleForm(int amount) {
            if (amount > 1) {
                return this.getPrettyForm() + "s";
            }
            return this.getPrettyForm();
        }
    }
}

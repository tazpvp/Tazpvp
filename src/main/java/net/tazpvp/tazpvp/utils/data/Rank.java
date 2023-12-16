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
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.ChatColor;

import java.util.List;

public enum Rank {
    DEFAULT("default", null, ChatColor.GRAY, 9, List.of("")),
    PREMIUM("premium", CC.GREEN+"❂", ChatColor.GREEN, 8, List.of("")),
    HELPER("helper", ChatFunctions.gradient("#04FF17", "HELPER", true), ChatColor.GREEN, 7,
            List.of("tazpvp.mute", "tazpvp.staff")),
    MODERATOR("moderator", ChatFunctions.gradient("#04A0FF", "MOD", true), ChatColor.AQUA, 6,
            List.of("tazpvp.ban", "tazpvp.restore", "vulcan.alerts", "tazpvp.vanish", "tazpvp.hide")),
    ADMIN("admin", ChatFunctions.gradient("#FFE104", "ADMIN", true), ChatColor.YELLOW, 5,
            List.of("tazpvp.restore", "tazpvp.kit")),
    MANAGER("manager", ChatFunctions.gradient("#db3bff", "MANAGER", true), ChatColor.BLUE, 4,
            List.of("tazpvp.stats")),
    OWNER("owner", ChatFunctions.gradient("#ff6417", "OWNER", true), ChatColor.RED, 1,
            List.of("is.op"));

    @Getter
    private final String name;
    @Getter
    private final String prefix;
    @Getter
    private final ChatColor color;
    @Getter
    private final int rank;
    @Getter
    private final List<String> permissions;

    Rank(String name, String prefix, ChatColor color, int rank, List<String> permissions) {
        this.name = name;
        this.prefix = prefix;
        this.color = color;
        this.rank = rank;
        this.permissions = permissions;
    }
}

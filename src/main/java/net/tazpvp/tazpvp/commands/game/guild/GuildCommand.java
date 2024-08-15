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

package net.tazpvp.tazpvp.commands.game.guild;

import lombok.Getter;
import net.tazpvp.tazpvp.commands.game.guild.sub.*;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;


public class GuildCommand extends NRCommand {
    @Getter
    private static final String notInGuild = "You are not in a guild";
    @Getter
    private static final String noPerms = "You do not have permission to do this.";

    /*
    Holy FUCK, the amount of classes, and abstraction that was used to make this easier to maintain, i think i might kms - @n-tdi
     */
    public GuildCommand() {
        super(new Label("guild", null, "g"));

        addSubcommand(new GuildHelpCommand());
        addSubcommand(new GuildInviteCommand());
        addSubcommand(new GuildAcceptCommand());
        addSubcommand(new GuildLeaveCommand());
        addSubcommand(new GuildDisbandCommand());
        addSubcommand(new GuildDeleteCommand());
        addSubcommand(new GuildKickCommand());
        addSubcommand(new GuildPromoteCommand());
        addSubcommand(new GuildDemoteCommand());
        addSubcommand(new GuildTransferCommand());
    }
}

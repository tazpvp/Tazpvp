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

package net.tazpvp.tazpvp.commands.game.guild.sub;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.commands.game.guild.GuildCommand;
import net.tazpvp.tazpvp.commands.game.guild.handler.GuildAbstractArgumentCommand;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.services.GuildService;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;

public class GuildTransferCommand extends GuildAbstractArgumentCommand {

    private static final GuildService guildService = Tazpvp.getInstance().getGuildService();

    public GuildTransferCommand() {
        super(new Label("transfer", null));
    }

    @Override
    public boolean executeFunction(@NonNull Player p, GuildEntity guildEntity, @NonNull Player target) {
        if (!guildEntity.getOwner().equals(p.getUniqueId())) {
            p.sendMessage(GuildCommand.getNoPerms());
            return true;
        }

        if (!guildService.isInGuild(target.getUniqueId(), guildEntity)) {
            p.sendMessage(target.getName() + " must be a member of your guild in order to transfer it to them!");
            return false;
        }

        guildEntity.setOwner(target.getUniqueId());
        guildService.saveGuild(guildEntity);

        target.sendMessage("You are now the leader of " + guildEntity.getName());
        p.sendMessage("Transferred " + guildEntity.getName() + " to " + target.getName() + ", you are now a general.");

        return true;
    }
}

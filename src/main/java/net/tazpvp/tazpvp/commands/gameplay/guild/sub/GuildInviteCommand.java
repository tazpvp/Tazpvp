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

package net.tazpvp.tazpvp.commands.gameplay.guild.sub;

import lombok.NonNull;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.commands.gameplay.guild.handler.GuildAbstractArgumentCommand;
import net.tazpvp.tazpvp.game.guilds.Guild;
import net.tazpvp.tazpvp.game.guilds.GuildUtils;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import world.ntdi.nrcore.utils.command.simple.Label;

public class GuildInviteCommand extends GuildAbstractArgumentCommand {
    public GuildInviteCommand() {
        super(new Label("invite", null));
    }

    @Override
    public boolean executeFunction(@NonNull Player p, @NonNull Guild g, @NonNull Player target) {
        invite(p, target, g);
        return true;
    }

    private static void invite(Player p, Player target, Guild g) {
        if (target == null) return;

        if (g.hasElevatedPerms(p.getUniqueId())) {
            if (g.getAllMembers().length >= 21) {
                p.sendMessage("Your guild is full");
                return;
            } else if (g.isInGuild(target.getUniqueId())) {
                p.sendMessage("This user is already in your guild.");
                return;
            } else if (GuildUtils.isInGuild(target)) {
                p.sendMessage(target.getName() + " is already in a guild.");
                return;
            } else if (g.isInvited(target.getUniqueId())) {
                p.sendMessage(target + " was already invited to your guild.");
                return;
            }

            TextComponent component = new TextComponent(TextComponent.fromLegacyText("[Click Here] to join"));

            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guild accept"));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Accept Invite")));

            target.spigot().sendMessage(component);

            g.invitePlayer(target.getUniqueId(), p.getUniqueId());
            target.setMetadata("guildInvited", new FixedMetadataValue(Tazpvp.getInstance(), g.getID()));

        } else {
            p.sendMessage("You do not have permission to invite");
        }
    }
}

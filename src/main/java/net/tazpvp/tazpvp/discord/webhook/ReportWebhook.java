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

package net.tazpvp.tazpvp.discord.webhook;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.entity.Player;

public class ReportWebhook {
    public ReportWebhook(Player player, Player reportedBy, String reason) {
        DiscordWebhook reportWebhook = new DiscordWebhook(Tazpvp.getInstance().getConfig().getString("report-webhook"));
        reportWebhook.setUsername("Homosexual Reporter");

        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();

        embed.setThumbnail("https://crafatar.com/avatars/" + player.getUniqueId());
        embed.setTitle(player.getName());
        embed.setAuthor("repoted by " + reportedBy.getName(), "https://www.ntdi.world", "https://crafater.com/avatars/" + reportedBy.getUniqueId());
        embed.addField("Reason", reason, false);
        embed.addField("View All Reports", "```/viewreports " + player.getName() + "```", false);

        embed.setFooter("Rownox likes toes", "https://crafatar.com/avatars/36233471-e1d8-497d-a007-3e2e3bc1289f");

        reportWebhook.addEmbed(embed);
        reportWebhook.execute();
    }
}

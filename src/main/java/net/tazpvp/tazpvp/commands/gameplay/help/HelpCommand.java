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

package net.tazpvp.tazpvp.commands.gameplay.help;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.LinkedList;
import java.util.List;

public class HelpCommand extends NRCommand {
    private final List<HelpPage> helpPages = new LinkedList<>();

    public HelpCommand() {
        super(new Label("help", null, "h"));

        helpPages.add(new HelpPage("LALALALA", "im &cRED"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage(helpPages.get(0).getFormattedMessage());
            return true;
        }

        final String targetPage = args[0];

        try {
            final int pageNumber = Integer.parseInt(targetPage) + 1; // We add one because help doesn't start at 0, but arrays do.
            final String page = helpPages.get(pageNumber).getFormattedMessage();

            sender.sendMessage(page);
        } catch (NumberFormatException e) {
            sendIncorrectUsage(sender, "Not a valid number!");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            sendIncorrectUsage(sender, "Page number does not exist!");
            return false;
        }

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        return Completer.intRange(1, this.helpPages.size(), 1);
    }
}

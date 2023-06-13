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

package net.tazpvp.tazpvp.npc.dialogue;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@Data
public class Dialogues {
    private final String name;
    private final List<String> dialoguesSad;
    private final List<String> dialoguesNormal;
    private final List<String> dialoguesHappy;

    public String getRandomDialogue() {
        final int playerCount = Bukkit.getOnlinePlayers().size();
        if (playerCount < 5) {
            return formatDialogue(dialogueFromList(dialoguesSad));
        } else if (playerCount < 25) {
            return formatDialogue(dialogueFromList(dialoguesNormal));
        } else {
            return formatDialogue(dialogueFromList(dialoguesSad));
        }
    }

    private String formatDialogue(String dialogue) {
        return CC.YELLOW + "[" + name + "] " + CC.WHITE + dialogue;
    }

    private String dialogueFromList(List<String> dialogues) {
        final int randomElementIndex = ThreadLocalRandom.current().nextInt(dialoguesSad.size()) % dialoguesSad.size();
        return dialogues.get(randomElementIndex);
    }
}

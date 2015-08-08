/*
 * New BSD License (BSD-new)
 *
 * Copyright (c) 2015 Maxim Roncacé
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     - Neither the name of the copyright holder nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.caseif.flint.steel.util.file;

import net.caseif.flint.minigame.Minigame;

import org.bukkit.Bukkit;

import java.io.File;

/**
 * Represents a {@link Minigame}-specific data file.
 *
 * @author Max Roncacé
 */
public class MinigameDataFile extends DataFile {

    public MinigameDataFile(String fileName, boolean isDirectory) {
        super(fileName, isDirectory);
    }

    public MinigameDataFile(String fileName) {
        super(fileName);
    }

    /**
     * Gets the {@link File} backing this {@link MinigameDataFile} for the given
     * {@link Minigame}.
     *
     * @param minigame The {@link Minigame} to retrieve a {@link File} for
     * @return The {@link File} backing this {@link MinigameDataFile} for the
     *     given {@link Minigame}.
     */
    public File getFile(Minigame minigame) {
        return new File(Bukkit.getPluginManager().getPlugin(minigame.getPlugin()).getDataFolder(),
                DataFiles.ROOT_DATA_DIR + File.pathSeparatorChar + getFileName());
    }

}
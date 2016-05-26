/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-2016, Max Roncace <me@caseif.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.caseif.flint.steel.lobby;

import net.caseif.flint.common.arena.CommonArena;
import net.caseif.flint.common.lobby.CommonLobbySign;
import net.caseif.flint.lobby.LobbySign;
import net.caseif.flint.steel.SteelCore;
import net.caseif.flint.steel.SteelMain;
import net.caseif.flint.steel.arena.SteelArena;
import net.caseif.flint.steel.lobby.type.SteelChallengerListingLobbySign;
import net.caseif.flint.steel.lobby.type.SteelStatusLobbySign;
import net.caseif.flint.steel.util.helper.LocationHelper;
import net.caseif.flint.util.physical.Location3D;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

/**
 * Implements {@link LobbySign}.
 *
 * @author Max Roncacé
 */
public abstract class SteelLobbySign extends CommonLobbySign {

    public SteelLobbySign(Location3D location, CommonArena arena) {
        super(location, arena);
        final LobbySign sign = this;
        Bukkit.getScheduler().runTask(SteelMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                sign.update();
            }
        });
    }

    @Override
    public void unregister() {
        super.unregister();
        // blank the physical sign block
        World world = Bukkit.getWorld(getLocation().getWorld().get());
        if (world == null) {
            SteelCore.logVerbose("Cannot blank unregistered lobby sign: world is not loaded");
        }
        Block block = LocationHelper.convertLocation(getLocation()).getBlock();
        if (block.getState() instanceof Sign) {
            for (int i = 0; i < ((Sign) block.getState()).getLines().length; i++) {
                ((Sign) block.getState()).setLine(i, "");
            }
        } else {
            SteelCore.logWarning("Cannot blank unregistered lobby sign: not a sign");
        }
        orphan();
    }

    public Block getBlock() {
        World world = Bukkit.getWorld(getLocation().getWorld().get());
        if (world == null) {
            throw new IllegalStateException("Cannot get world \"" + getLocation().getWorld().get()
                    + "\" for lobby sign");
        }
        return world.getBlockAt((int) getLocation().getX(), (int) getLocation().getY(), (int) getLocation().getZ());
    }

    public static SteelLobbySign of(Location3D location, SteelArena arena, JsonObject json)
            throws IllegalArgumentException {
        if (json.has(PERSIST_TYPE_KEY)) {
            String type = json.get(PERSIST_TYPE_KEY).getAsString();
            switch (type) {
                case PERSIST_TYPE_STATUS: {
                    return new SteelStatusLobbySign(location, arena);
                }
                case PERSIST_TYPE_LISTING: {
                    if (!json.has(PERSIST_INDEX_KEY)) {
                        break;
                    }
                    int index = json.get(PERSIST_INDEX_KEY).getAsInt();
                    return new SteelChallengerListingLobbySign(location, arena, index);
                }
                default: { // continue to IllegalArgumentException
                }
            }
        }
        throw new IllegalArgumentException("Invalid ConfigurationSection for LobbySign");
    }

}

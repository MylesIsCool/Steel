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

package net.caseif.flint.steel.listener.rollback;

import net.caseif.flint.steel.util.agent.rollback.RollbackAgent;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Listener for physical events logged by the rollback engine.
 *
 * @author Max Roncacé
 */
public class RollbackBlockListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        RollbackAgent.checkBlockChange(event.getBlock().getLocation(), event.getBlock().getState(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        RollbackAgent.checkBlockChange(event.getBlock().getLocation(), event.getBlock().getState(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBurn(BlockBurnEvent event) {
        RollbackAgent.checkBlockChange(event.getBlock().getLocation(), event.getBlock().getState(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockFade(BlockFadeEvent event) {
        RollbackAgent.checkBlockChange(event.getBlock().getLocation(), event.getBlock().getState(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockFromTo(BlockFromToEvent event) {
        RollbackAgent.checkBlockChange(event.getBlock().getLocation(), event.getBlock().getState(), event);
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockSpread(BlockSpreadEvent event) {
        RollbackAgent.checkBlockChange(event.getBlock().getLocation(), event.getBlock().getState(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        RollbackAgent.checkBlockChange(event.getBlock().getLocation(), event.getBlock().getState(), event);
        for (Block b : event.getBlocks()) {
            RollbackAgent.checkBlockChange(b.getLocation(), b.getState(), event);
            // Check if any new blocks new to be set back to air
            Block target = b.getRelative(event.getDirection());
            RollbackAgent.checkBlockChange(target.getLocation(), target.getState(), event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        RollbackAgent.checkBlockChange(event.getClickedBlock().getLocation(), event.getClickedBlock().getState(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        Block target = event.getBlock().getRelative(event.getDirection().getOppositeFace());
        RollbackAgent.checkBlockChange(target.getLocation(), target.getState(), event);
        if (!event.isSticky()) return;
        for (Block b : event.getBlocks()) {
            RollbackAgent.checkBlockChange(b.getLocation(), b.getState(), event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockForm(BlockFormEvent event) {
        RollbackAgent.checkBlockChange(event.getBlock().getLocation(), event.getBlock().getState(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockGrow(BlockGrowEvent event) {
        RollbackAgent.checkBlockChange(event.getBlock().getLocation(), event.getBlock().getState(), event);
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockMultiPlace(BlockMultiPlaceEvent event) {
        for (BlockState state : event.getReplacedBlockStates()) {
            RollbackAgent.checkBlockChange(state.getLocation(), state.getLocation().getBlock().getState(), event);
        }
        RollbackAgent.checkBlockChange(event.getBlock().getLocation(), event.getBlock().getState(), event);
    }

}

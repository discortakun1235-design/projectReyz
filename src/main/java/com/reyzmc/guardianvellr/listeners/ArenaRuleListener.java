package com.namamu.guardiansvaller.listeners;

import com.namamu.guardiansvaller.GuardiansValler;
import com.namamu.guardiansvaller.arena.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ArenaRuleListener implements Listener {

    private final GuardiansValler plugin;
    private final ArenaManager arenaManager;

    public ArenaRuleListener(GuardiansValler plugin, ArenaManager arenaManager) {
        this.plugin = plugin;
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (arenaManager.isInArena(player)) {
            event.setCancelled(true);
            player.sendMessage("§cKamu tidak bisa menghancurkan block saat berada di dalam Arena Bos!");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (arenaManager.isInArena(player)) {
            event.setCancelled(true);
            player.sendMessage("§cKamu tidak bisa meletakkan block saat berada di dalam Arena Bos!");
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (arenaManager.isInArena(player)) {
            String command = event.getMessage().toLowerCase();
            if (!command.startsWith("/dungeon leave") && !command.startsWith("/gv")) {
                event.setCancelled(true);
                player.sendMessage("§cKamu tidak bisa menggunakan command lain saat raid bos!");
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (arenaManager.isInArena(player)) {
            arenaManager.leaveArena(player, null);
        }
    }
  }
      

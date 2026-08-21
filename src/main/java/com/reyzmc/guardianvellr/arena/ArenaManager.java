package com.reyzmc.guardianvellr.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArenaManager {

    private final Map<String, Location> arenas;
    private final Map<UUID, String> playersInArena;

    public ArenaManager() {
        this.arenas = new HashMap<>();
        this.playersInArena = new HashMap<>();
    }

    public void createArena(String name, Location spawnLocation) {
        arenas.put(name, spawnLocation);
    }

    public void removeArena(String name) {
        arenas.remove(name);
    }

    public void joinArena(Player player, String arenaName) {
        if (arenas.containsKey(arenaName)) {
            playersInArena.put(player.getUniqueId(), arenaName);
            player.teleport(arenas.get(arenaName));
        }
    }

    public void leaveArena(Player player, Location returnLocation) {
        if (playersInArena.containsKey(player.getUniqueId())) {
            playersInArena.remove(player.getUniqueId());
            if (returnLocation != null) {
                player.teleport(returnLocation);
            }
        }
    }

    public boolean isInArena(Player player) {
        return playersInArena.containsKey(player.getUniqueId());
    }
    
    public String getPlayerArena(Player player) {
        return playersInArena.get(player.getUniqueId());
    }
}

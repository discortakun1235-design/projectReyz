package com.namamu.guardiansvaller;

import com.namamu.guardiansvaller.arena.ArenaManager;
import com.namamu.guardiansvaller.commands.AdminCommand;
import com.namamu.guardiansvaller.commands.DungeonCommand;
import com.namamu.guardiansvaller.listeners.ArenaRuleListener;
import com.namamu.guardiansvaller.listeners.BossCombatListener;
import org.bukkit.plugin.java.JavaPlugin;

public class GuardiansValler extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        arenaManager = new ArenaManager();

        getCommand("gv").setExecutor(new AdminCommand(this));
        getCommand("dungeon").setExecutor(new DungeonCommand(this));

        getServer().getPluginManager().registerEvents(new BossCombatListener(this), this);
        getServer().getPluginManager().registerEvents(new ArenaRuleListener(this, arenaManager), this);

        getLogger().info("GuardiansValler berhasil diaktifkan!");
    }

    @Override
    public void onDisable() {
        getLogger().info("GuardiansValler telah dimatikan!");
    }
    
    public ArenaManager getArenaManager() {
        return arenaManager;
    }
}

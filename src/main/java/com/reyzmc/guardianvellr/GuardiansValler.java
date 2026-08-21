package com.namamu.guardiansvaller;

import org.bukkit.plugin.java.JavaPlugin;

public class GuardiansValler extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Guardians Valler has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Guardians Valler has been disabled!");
    }
}

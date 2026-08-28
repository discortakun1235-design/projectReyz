package com.Reyzmc.projectme;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Economy econ = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        if (!setupEconomy()) {
            getLogger().severe("Vault tidak ditemukan! Mematikan plugin Blood Money...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        getServer().getPluginManager().registerEvents(new KillListener(this), this);
        getLogger().info("Blood Money berhasil dinyalakan!");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}

package com.Reyzmc.projectme;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {
    private Main plugin;

    public KillListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer != null && killer instanceof Player) {
            Economy econ = Main.getEconomy();
            
            double persentase = plugin.getConfig().getDouble("steal-percentage") / 100.0;
            double victimBalance = econ.getBalance(victim);
            
            double stolenMoney = victimBalance * persentase;
            
            if (stolenMoney > 0) {
                econ.withdrawPlayer(victim, stolenMoney);
                econ.depositPlayer(killer, stolenMoney);
                
                killer.sendMessage(ChatColor.GREEN + "Kamu membunuh " + victim.getName() + " dan merampok $" + String.format("%.0f", stolenMoney) + "!");
                victim.sendMessage(ChatColor.RED + "Kamu dibunuh oleh " + killer.getName() + " dan kehilangan $" + String.format("%.0f", stolenMoney) + "!");
                
                if (plugin.getConfig().getBoolean("broadcast-global")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_RED + "[Blood Money] " + ChatColor.YELLOW + killer.getName() + 
                        " baru saja merampok $" + String.format("%.0f", stolenMoney) + " dari mayat " + victim.getName() + "!");
                }
            }
        }
    }
                    }

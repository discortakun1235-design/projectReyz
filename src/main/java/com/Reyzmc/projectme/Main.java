package com.Reyzmc.projectme;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin implements CommandExecutor {
    private static Economy econ = null;
    private HashMap<UUID, SewaRequest> pendingRequests = new HashMap<>();

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            getLogger().severe("Vault tidak ditemukan! Mematikan plugin Mercenary...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        getCommand("sewa").setExecutor(this);
        getCommand("terima").setExecutor(this);
        getLogger().info("Plugin Mercenary berhasil dinyalakan!");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("sewa")) {
            if (args.length < 3) {
                player.sendMessage(ChatColor.RED + "Gunakan: /sewa <nama_player> <harga> <menit>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(ChatColor.RED + "Player tersebut tidak online!");
                return true;
            }

            if (target.equals(player)) {
                player.sendMessage(ChatColor.RED + "Kamu tidak bisa menyewa dirimu sendiri!");
                return true;
            }

            double harga;
            int menit;
            try {
                harga = Double.parseDouble(args[1]);
                menit = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Harga dan menit harus berupa angka!");
                return true;
            }

            if (econ.getBalance(player) < harga) {
                player.sendMessage(ChatColor.RED + "Uangmu tidak cukup! Saldomu: $" + String.format("%.0f", econ.getBalance(player)));
                return true;
            }

            pendingRequests.put(target.getUniqueId(), new SewaRequest(player.getUniqueId(), harga, menit));
            
            player.sendMessage(ChatColor.GREEN + "Tawaran dikirim ke " + target.getName() + " seharga $" + harga + " untuk " + menit + " menit.");
            target.sendMessage(ChatColor.GOLD + "=== Tuntutan Pekerjaan Mercenary ===");
            target.sendMessage(ChatColor.YELLOW + player.getName() + " ingin menyewamu sebagai pengawal!");
            target.sendMessage(ChatColor.YELLOW + "Bayaran: $" + harga + " | Durasi: " + menit + " menit.");
            target.sendMessage(ChatColor.GREEN + "Ketik /terima untuk menyetujui kontrak.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("terima")) {
            SewaRequest req = pendingRequests.get(player.getUniqueId());
            if (req == null) {
                player.sendMessage(ChatColor.RED + "Kamu tidak memiliki tawaran pekerjaan yang masuk!");
                return true;
            }

            Player penyewa = Bukkit.getPlayer(req.penyewaId);
            if (penyewa == null || !penyewa.isOnline()) {
                player.sendMessage(ChatColor.RED + "Penyewa sudah offline, kontrak batal.");
                pendingRequests.remove(player.getUniqueId());
                return true;
            }

            if (econ.getBalance(penyewa) < req.harga) {
                player.sendMessage(ChatColor.RED + "Uang penyewa ternyata kurang, kontrak batal!");
                penyewa.sendMessage(ChatColor.RED + "Kontrak pengawalan batal karena uangmu tidak cukup!");
                pendingRequests.remove(player.getUniqueId());
                return true;
            }

            econ.withdrawPlayer(penyewa, req.harga);
            econ.depositPlayer(player, req.harga);
            pendingRequests.remove(player.getUniqueId());

            Bukkit.broadcastMessage(ChatColor.AQUA + "[Mercenary] " + ChatColor.YELLOW + player.getName() + 
                " resmi disewa menjadi pengawal " + penyewa.getName() + " selama " + req.menit + " menit!");

            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage(ChatColor.AQUA + "[Mercenary] " + ChatColor.YELLOW + "Waktu habis! Kontrak pengawalan " + 
                        player.getName() + " untuk " + penyewa.getName() + " telah selesai!");
                }
            }.runTaskLater(this, 20L * 60L * req.menit);

            return true;
        }

        return true;
    }

    private class SewaRequest {
        UUID penyewaId;
        double harga;
        int menit;

        SewaRequest(UUID penyewaId, double harga, int menit) {
            this.penyewaId = penyewaId;
            this.harga = harga;
            this.menit = menit;
        }
    }
}

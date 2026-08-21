package com.reyzmc.guardianvellr.commands;

import com.namamu.guardiansvaller.GuardiansValler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {

    private final GuardiansValler plugin;

    public AdminCommand(GuardiansValler plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Hanya player yang bisa menggunakan command ini.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("guardiansvaller.admin")) {
            player.sendMessage("§cAnda tidak memiliki izin untuk command ini.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§e/gv spawn <tipe>");
            player.sendMessage("§e/gv arena create <nama>");
            return true;
        }

        if (args[0].equalsIgnoreCase("spawn")) {
            if (args.length < 2) {
                player.sendMessage("§cTentukan tipe bos: frostwarden, titanbehemoth");
                return true;
            }
            
            String bossType = args[1].toLowerCase();
            
            if (bossType.equals("frostwarden")) {
                player.sendMessage("§aMen-spawn Frost Warden di lokasi Anda...");
            } else if (bossType.equals("titanbehemoth")) {
                player.sendMessage("§aMen-spawn Titan Behemoth di lokasi Anda...");
            } else {
                player.sendMessage("§cTipe bos tidak ditemukan.");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("arena")) {
            if (args.length < 3 || !args[1].equalsIgnoreCase("create")) {
                player.sendMessage("§cGunakan: /gv arena create <nama>");
                return true;
            }
            
            String arenaName = args[2];
            player.sendMessage("§aArena " + arenaName + " berhasil dibuat di lokasi Anda.");
            return true;
        }

        return true;
    }
              }

package com.reyzmc.guardianvellr.commands;

import com.reyzmc.guardianvellr.GuardiansValler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DungeonCommand implements CommandExecutor {

    private final GuardiansValler plugin;

    public DungeonCommand(GuardiansValler plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Hanya player yang bisa menggunakan command ini.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§e/dungeon join <nama>");
            player.sendMessage("§e/dungeon leave");
            return true;
        }

        if (args[0].equalsIgnoreCase("join")) {
            if (args.length < 2) {
                player.sendMessage("§cTentukan nama arena.");
                return true;
            }

            String arenaName = args[1];
            player.sendMessage("§aTeleportasi ke arena " + arenaName + "...");
            return true;
        }

        if (args[0].equalsIgnoreCase("leave")) {
            player.sendMessage("§aMeninggalkan arena...");
            return true;
        }

        return true;
    }
}

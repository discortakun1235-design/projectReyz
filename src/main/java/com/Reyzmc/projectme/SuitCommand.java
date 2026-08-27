package com.Reyzmc.projectme;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SuitCommand implements CommandExecutor {

    private Main plugin;
    public static HashMap<UUID, UUID> activeGames = new HashMap<>();
    public static HashMap<UUID, String> playerChoices = new HashMap<>();

    public SuitCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Hanya player di dalam game yang bisa menggunakan command ini!");
            return true;
        }

        Player p1 = (Player) sender;

        if (args.length != 1) {
            p1.sendMessage("§cPenggunaan yang benar: /suit <nama_player>");
            return true;
        }

        Player p2 = Bukkit.getPlayer(args[0]);

        if (p2 == null || !p2.isOnline()) {
            p1.sendMessage("§cTidak ada player dengan nama tersebut yang sedang online.");
            return true;
        }

        if (p1.getUniqueId().equals(p2.getUniqueId())) {
            p1.sendMessage("§cKamu tidak bisa bermain suit dengan dirimu sendiri!");
            return true;
        }

        if (activeGames.containsKey(p1.getUniqueId()) || activeGames.containsValue(p1.getUniqueId())) {
            p1.sendMessage("§cKamu masih dalam sesi pertandingan suit!");
            return true;
        }

        if (activeGames.containsKey(p2.getUniqueId()) || activeGames.containsValue(p2.getUniqueId())) {
            p1.sendMessage("§cPlayer tersebut sedang bermain suit dengan orang lain!");
            return true;
        }

        activeGames.put(p1.getUniqueId(), p2.getUniqueId());
        
        p1.sendMessage("§aKamu menantang §e" + p2.getName() + "§a bermain suit!");
        p1.sendMessage("§aSilakan ketik §ebatu§a, §egunting§a, atau §ekertas §adi chat!");
        
        p2.sendMessage("§e" + p1.getName() + " §amenantangmu bermain suit!");
        p2.sendMessage("§aSilakan ketik §ebatu§a, §egunting§a, atau §ekertas §adi chat!");

        return true;
    }
}


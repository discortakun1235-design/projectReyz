package com.Reyzmc.projectme;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.UUID;

public class ChatListener implements Listener {

    private Main plugin;

    public ChatListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        UUID opponentUuid = null;

        if (SuitCommand.activeGames.containsKey(uuid)) {
            opponentUuid = SuitCommand.activeGames.get(uuid);
        } else if (SuitCommand.activeGames.containsValue(uuid)) {
            for (Map.Entry<UUID, UUID> entry : SuitCommand.activeGames.entrySet()) {
                if (entry.getValue().equals(uuid)) {
                    opponentUuid = entry.getKey();
                    break;
                }
            }
        }

        if (opponentUuid == null) {
            return;
        }

        String msg = event.getMessage().toLowerCase();
        
        if (msg.equals("batu") || msg.equals("gunting") || msg.equals("kertas")) {
            event.setCancelled(true);
            
            if (SuitCommand.playerChoices.containsKey(uuid)) {
                p.sendMessage("§cKamu sudah memilih! Tunggu lawanmu.");
                return;
            }

            SuitCommand.playerChoices.put(uuid, msg);
            p.sendMessage("§aPilihanmu (§e" + msg + "§a) berhasil disimpan. Menunggu lawan...");

            if (SuitCommand.playerChoices.containsKey(opponentUuid)) {
                String pChoice = msg;
                String oppChoice = SuitCommand.playerChoices.get(opponentUuid);
                Player opponent = Bukkit.getPlayer(opponentUuid);
                
                Bukkit.getScheduler().runTask(plugin, () -> {
                    resolveWinner(p, opponent, pChoice, oppChoice);
                });
            }
        } else {
            event.setCancelled(true);
            p.sendMessage("§cTolong ketik hanya: batu, gunting, atau kertas!");
        }
    }

    private void resolveWinner(Player p1, Player p2, String choice1, String choice2) {
        SuitCommand.activeGames.remove(p1.getUniqueId());
        SuitCommand.activeGames.remove(p2.getUniqueId());
        SuitCommand.playerChoices.remove(p1.getUniqueId());
        SuitCommand.playerChoices.remove(p2.getUniqueId());

        if (p1 == null || p2 == null || !p1.isOnline() || !p2.isOnline()) {
            return;
        }

        int prize = plugin.getConfig().getInt("hadiah-menang");

        if (choice1.equals(choice2)) {
            String msgSeri = translateColor(plugin.getConfig().getString("pesan-seri"));
            p1.sendMessage(msgSeri);
            p2.sendMessage(msgSeri);
            return;
        }

        Player winner;
        Player loser;

        if ((choice1.equals("batu") && choice2.equals("gunting")) ||
            (choice1.equals("gunting") && choice2.equals("kertas")) ||
            (choice1.equals("kertas") && choice2.equals("batu"))) {
            winner = p1;
            loser = p2;
        } else {
            winner = p2;
            loser = p1;
        }

        Main.getEconomy().depositPlayer(winner, prize);

        String msgMenang = translateColor(plugin.getConfig().getString("pesan-menang"))
                .replace("%hadiah%", String.valueOf(prize))
                .replace("%lawan%", loser.getName());
                
        String msgKalah = translateColor(plugin.getConfig().getString("pesan-kalah"))
                .replace("%lawan%", winner.getName());

        winner.sendMessage(msgMenang);
        loser.sendMessage(msgKalah);
    }

    private String translateColor(String text) {
        if (text == null) return "";
        return ChatColor.translateAlternateColorCodes('&', text);
    }
                       }
                               

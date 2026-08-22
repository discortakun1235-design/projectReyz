package com.reyzmc.guardianvellr.listeners;

import com.reyzmc.guardianvellr.GuardiansValler;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class BossCombatListener implements Listener {

    private final GuardiansValler plugin;

    public BossCombatListener(GuardiansValler plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBossDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }

        LivingEntity entity = (LivingEntity) event.getEntity();

        if (entity.getCustomName() == null) {
            return;
        }

        if (entity.getCustomName().contains("Frost Warden") || entity.getCustomName().contains("Titan Behemoth")) {
            
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                
                if (entity.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null) {
                    double currentHealth = entity.getHealth() - event.getFinalDamage();
                    double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                    
                    if (currentHealth > 0 && currentHealth <= (maxHealth / 2)) {
                        
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        
        if (entity.getCustomName() != null) {
            if (entity.getCustomName().contains("Frost Warden") || entity.getCustomName().contains("Titan Behemoth")) {
                event.getDrops().clear();
                event.setDroppedExp(500);
            }
        }
    }
}

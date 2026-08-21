package com.reyzmc.guardianvellr.boss.types;

import com.reyzmc.guardiansvellr.boss.CustomBoss;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Stray;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FrostWarden extends CustomBoss {

    public FrostWarden() {
        super("§bFrost Warden", 500.0);
    }

    @Override
    public void spawn(Location location) {
        if (location.getWorld() != null) {
            entity = (Stray) location.getWorld().spawnEntity(location, EntityType.STRAY);
            entity.setCustomName(name);
            entity.setCustomNameVisible(true);
            
            if (entity.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null) {
                entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
                entity.setHealth(maxHealth);
            }
        }
    }

    @Override
    public void useSkill(Player target) {
        if (target != null && target.isOnline()) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 4));
        }
    }

    @Override
    public void onDeath() {
        if (entity != null && entity.getWorld() != null) {
            entity.getWorld().createExplosion(entity.getLocation(), 2.0f, false, false);
        }
    }
                  }

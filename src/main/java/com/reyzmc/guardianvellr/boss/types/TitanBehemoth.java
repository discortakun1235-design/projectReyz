package com.reyzmc.guardianvellr.boss.types;

import com.reyzmc.guardianvellr.boss.CustomBoss;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TitanBehemoth extends CustomBoss {

    public TitanBehemoth() {
        super("§8Titan Behemoth", 1000.0);
    }

    @Override
    public void spawn(Location location) {
        if (location.getWorld() != null) {
            entity = (IronGolem) location.getWorld().spawnEntity(location, EntityType.IRON_GOLEM);
            entity.setCustomName(name);
            entity.setCustomNameVisible(true);
            
            if (entity.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null) {
                entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
                entity.setHealth(maxHealth);
            }
            
            if (entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null) {
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(15.0);
            }
        }
    }

    @Override
    public void useSkill(Player target) {
        if (target != null && target.isOnline()) {
            Vector throwUp = new Vector(0, 1.2, 0);
            target.setVelocity(throwUp);
            target.damage(5.0, entity);
        }
    }

    @Override
    public void onDeath() {
        if (entity != null && entity.getWorld() != null) {
            entity.getWorld().strikeLightningEffect(entity.getLocation());
        }
    }
}

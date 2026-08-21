package com.namamu.guardiansvaller.boss;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class CustomBoss {

    protected LivingEntity entity;
    protected String name;
    protected double maxHealth;

    public CustomBoss(String name, double maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
    }

    public abstract void spawn(Location location);

    public abstract void useSkill(Player target);

    public abstract void onDeath();

    public LivingEntity getEntity() {
        return entity;
    }

    public String getName() {
        return name;
    }

    public double getMaxHealth() {
        return maxHealth;
    }
}

package com.reyzmc.guardianvellr.arena;

import org.bukkit.Location;

public class ArenaData {

    private String name;
    private Location spawnLocation;
    private Location minBoundary;
    private Location maxBoundary;

    public ArenaData(String name, Location spawnLocation, Location pos1, Location pos2) {
        this.name = name;
        this.spawnLocation = spawnLocation;
        setBounds(pos1, pos2);
    }

    public void setBounds(Location pos1, Location pos2) {
        if (pos1 == null || pos2 == null || pos1.getWorld() == null) return;
        
        double minX = Math.min(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        
        double maxX = Math.max(pos1.getX(), pos2.getX());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());
        
        this.minBoundary = new Location(pos1.getWorld(), minX, minY, minZ);
        this.maxBoundary = new Location(pos1.getWorld(), maxX, maxY, maxZ);
    }

    public boolean isInside(Location location) {
        if (minBoundary == null || maxBoundary == null || location.getWorld() == null) {
            return false;
        }
        
        if (!location.getWorld().equals(minBoundary.getWorld())) {
            return false;
        }
        
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        
        return x >= minBoundary.getX() && x <= maxBoundary.getX() &&
               y >= minBoundary.getY() && y <= maxBoundary.getY() &&
               z >= minBoundary.getZ() && z <= maxBoundary.getZ();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public Location getMinBoundary() {
        return minBoundary;
    }

    public Location getMaxBoundary() {
        return maxBoundary;
    }
}

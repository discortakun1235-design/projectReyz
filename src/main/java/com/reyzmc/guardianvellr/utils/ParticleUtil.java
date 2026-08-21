package com.reyzmc.guardianvellr.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class ParticleUtil {

    public static void spawnCircle(Location center, Particle particle, double radius, int points) {
        World world = center.getWorld();
        if (world == null) return;

        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            Location loc = new Location(world, x, center.getY(), z);
            world.spawnParticle(particle, loc, 1, 0, 0, 0, 0);
        }
    }

    public static void spawnLine(Location start, Location end, Particle particle, double space) {
        World world = start.getWorld();
        if (world == null || !world.equals(end.getWorld())) return;

        double distance = start.distance(end);
        Vector p1 = start.toVector();
        Vector p2 = end.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);

        for (double length = 0; length < distance; p1.add(vector)) {
            world.spawnParticle(particle, p1.getX(), p1.getY(), p1.getZ(), 1, 0, 0, 0, 0);
            length += space;
        }
    }
}

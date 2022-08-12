package com.infinium.api.world;


import com.infinium.Infinium;
import net.minecraft.world.World;

public class Location implements Cloneable {

    private double X;
    private double Y;
    private double Z;
    private final World world;

    public Location(double X, double Y, double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        world = Infinium.getInstance().getCore().getServer().getWorld(World.OVERWORLD);
    }


    public Location(World world, double X, double Y, double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.world = world;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getZ() {
        return Z;
    }

    public World getWorld() {
        return world;
    }

    public Location add(int x, int y, int z) {
        X += x;
        Y += Y;
        Z += Z;
        return this;
    }

    @Override
    public Location clone() {
        try {
            Location clone = (Location) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

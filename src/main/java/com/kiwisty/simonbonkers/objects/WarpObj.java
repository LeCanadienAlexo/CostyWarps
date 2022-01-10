package com.kiwisty.simonbonkers.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class WarpObj
{
    int warpX;
    int warpY;
    int warpZ;
    String warpWorld;

    UUID owner;
    String warpName;

    public WarpObj(Location _warpLocation, UUID _owner, String _warpName)
    {
        warpX = _warpLocation.getBlockX();
        warpY = _warpLocation.getBlockY();
        warpZ = _warpLocation.getBlockZ();
        warpWorld = _warpLocation.getWorld().getName();

        owner = _owner;
        warpName = _warpName;
    }
    
    public Location getWarpLocation()
    {
        return new Location(Bukkit.getWorld(warpWorld), warpX, warpY, warpZ);
    }

    public UUID getOwner() {
        return owner;
    }

    public String getWarpName() {
        return warpName;
    }
}

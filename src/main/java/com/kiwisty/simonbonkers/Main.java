package com.kiwisty.simonbonkers;

import com.kiwisty.simonbonkers.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        getLogger().info("Bonkers");

        WarpManager.loadFile();
        WarpManager.forceBackup();

        this.getCommand("warp").setExecutor(new WarpBase(this));
    }
}

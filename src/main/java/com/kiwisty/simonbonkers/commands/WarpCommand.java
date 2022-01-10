package com.kiwisty.simonbonkers.commands;

import com.kiwisty.simonbonkers.helpers.MessageFormatting;
import com.kiwisty.simonbonkers.Main;
import com.kiwisty.simonbonkers.objects.WarpObj;
import com.kiwisty.simonbonkers.WarpManager;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class WarpCommand
{
    private final int DELAYBEFORETP = 5;
    private final int ARGSCONSTANT = 0;

    private Main mainInstance;

    public WarpCommand(Main _mainInstance)
    {
        mainInstance = _mainInstance;
    }

    public boolean onCommand(Player player, Command command, String label, String[] args)
    {
        if(args.length == ARGSCONSTANT)
        {
            player.sendMessage(MessageFormatting.error("You need to provide a warp name to teleport, use /warp list for a list of available warps"));
            return false;
        }

        List<WarpObj> playerWarpList = WarpManager.getWarpList(player.getUniqueId());

        for(WarpObj o : playerWarpList)
        {
            if(o.getWarpName().equals(args[ARGSCONSTANT]))
            {
                player.sendMessage(MessageFormatting.warning("Teleportation in 5 seconds"));
                Block currentPlayerLocation = player.getLocation().getBlock();
                final boolean[] teleported = {false};

                //Initiating X seconds wait for warp
                BukkitTask warpTask = new BukkitRunnable(){
                    @Override
                    public void run(){
                        player.teleport(o.getWarpLocation());
                        player.sendMessage(MessageFormatting.success("Teleportation successful: " + args[ARGSCONSTANT]));
                        teleported[0] = true;
                    }
                }.runTaskLater(mainInstance, 20 * DELAYBEFORETP);

                //Verification that the player isn't moving
                BukkitTask movementCheckTask = new BukkitRunnable(){
                    @Override
                    public void run(){
                        if(teleported[0])
                            this.cancel();
                        else if(!player.getLocation().getBlock().equals(currentPlayerLocation))
                        {
                            player.sendMessage(MessageFormatting.error("Teleportation aborted, please don't move during the countdown"));
                            warpTask.cancel();
                            this.cancel();
                        }
                    }
                }.runTaskTimer(mainInstance, 0, 10);
                return true;
            }
        }
        player.sendMessage(MessageFormatting.error("No warp with the name: " + args[ARGSCONSTANT]));
        return true;
    }
}

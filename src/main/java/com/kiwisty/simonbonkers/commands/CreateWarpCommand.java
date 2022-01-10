package com.kiwisty.simonbonkers.commands;
import com.kiwisty.simonbonkers.helpers.MessageFormatting;
import com.kiwisty.simonbonkers.objects.WarpObj;
import com.kiwisty.simonbonkers.WarpManager;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

public class CreateWarpCommand
{
    private int priceMultiplier = 10;
    private final int ARGSCONSTANT = 1;
    public boolean onCommand(Player player, Command command, String label, String[] args)
    {
        if(args.length == ARGSCONSTANT)
        {
            player.sendMessage(MessageFormatting.error("Don't forget to give your new warp a name!"));
            return false;
        }

        List<WarpObj> playerWarpList = WarpManager.getWarpList(player.getUniqueId());
        for(WarpObj o : playerWarpList)
        {
            if(o.getWarpName().equals(args[ARGSCONSTANT]))
            {
                player.sendMessage(MessageFormatting.error("A warp using that name already exists"));
                return false;
            }
        }

        int newWarpPrice = (WarpManager.getWarpAmount(player.getUniqueId()) + 1) * priceMultiplier;

        if(player.getLevel() >= newWarpPrice)
        {
            WarpManager.addWarp(player.getLocation(), player.getUniqueId(), args[ARGSCONSTANT]);
            player.sendMessage(MessageFormatting.success("Warp created: " + args[ARGSCONSTANT]));
            player.setLevel(player.getLevel() - newWarpPrice);
        }else
        {
            player.sendMessage(MessageFormatting.error("You need " + newWarpPrice + " levels to create that warp!"));
        }
        return true;
    }
}

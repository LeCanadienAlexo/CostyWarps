package com.kiwisty.simonbonkers.commands;

import com.kiwisty.simonbonkers.helpers.MessageFormatting;
import com.kiwisty.simonbonkers.objects.WarpObj;
import com.kiwisty.simonbonkers.WarpManager;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

public class ListWarpCommand
{
    public boolean onCommand(Player player, Command command, String label, String[] args)
    {
        List<WarpObj> warpwarp = WarpManager.getWarpList(player.getUniqueId());

        if(warpwarp.size() == 0)
        {
            player.sendMessage(MessageFormatting.information("No warp"));
            return true;
        }

        String warpListText = MessageFormatting.information("List of warp(s): ");
        WarpObj lastWarp = warpwarp.get(warpwarp.size() - 1);
        for(WarpObj o : warpwarp)
        {
            if(o.equals(lastWarp))
                warpListText += o.getWarpName();
            else
                warpListText += o.getWarpName() + ", ";
        }
        player.sendMessage(warpListText);
        return true;
    }
}

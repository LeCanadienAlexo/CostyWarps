package com.kiwisty.simonbonkers.commands;

import com.kiwisty.simonbonkers.helpers.MessageFormatting;
import com.kiwisty.simonbonkers.WarpManager;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class DeleteWarpCommand
{
    private final int ARGSCONSTANT = 1;
    public boolean onCommand(Player player, Command command, String label, String[] args)
    {
        if(args.length == ARGSCONSTANT)
        {
            player.sendMessage(MessageFormatting.error("Please provide a warp name to delete it, use /warp list for a list of available warp(s)"));
            return false;
        }

        if(!WarpManager.tryDeleteWarp(args[ARGSCONSTANT], player.getUniqueId()))
        {
            player.sendMessage(MessageFormatting.error("No warp with that name: " + args[ARGSCONSTANT]));
            return true;
        }

        player.sendMessage(MessageFormatting.success("Warp deleted: " + args[ARGSCONSTANT]));
        return true;
    }
}

package com.kiwisty.simonbonkers.commands;

import com.kiwisty.simonbonkers.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpBase implements CommandExecutor
{
    WarpCommand warpCommand;
    DeleteWarpCommand deleteWarpCommand;
    CreateWarpCommand createWarpCommand;
    ListWarpCommand listWarpCommand;

    public WarpBase(Main mainInstance)
    {
        warpCommand = new WarpCommand(mainInstance);
        deleteWarpCommand = new DeleteWarpCommand();
        createWarpCommand = new CreateWarpCommand();
        listWarpCommand = new ListWarpCommand();
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if(commandSender instanceof Player)
        {
            Player player = (Player) commandSender;

            switch (args[0].toLowerCase())
            {
                case "delete":
                    deleteWarpCommand.onCommand(player, command, s, args); break;
                case "create":
                    createWarpCommand.onCommand(player, command, s, args); break;
                case "list":
                    listWarpCommand.onCommand(player, command, s, args); break;
                default:
                    warpCommand.onCommand(player, command, s, args); break;
            }
            return true;
        }
        return false; // Command sender was not human
    }
}

package com.kiwisty.simonbonkers.helpers;

import org.bukkit.ChatColor;

public class MessageFormatting
{
    public static String error(String message)
    {
        return ChatColor.RED + "[Erreur]: " + ChatColor.WHITE + message;
    }

    public static String success(String message)
    {
        return ChatColor.GREEN + "[Succès]: " + ChatColor.WHITE + message;
    }

    public static String warning(String message) { return ChatColor.YELLOW + "[Alerte]: " + ChatColor.WHITE + message; }

    public static String information(String message) { return ChatColor.YELLOW + "[Info]: " + ChatColor.WHITE + message; }
}

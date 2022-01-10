package com.kiwisty.simonbonkers;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kiwisty.simonbonkers.helpers.MessageFormatting;
import com.kiwisty.simonbonkers.objects.WarpObj;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.*;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.io.filefilter.TrueFileFilter.TRUE;

public class WarpManager
{
    private static List<WarpObj> warpList = new ArrayList<>();

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Type arrayListType = new TypeToken<ArrayList<WarpObj>>(){}.getType();

    private static File backupFile = new File("./WarpSaves");
    private static File file = new File(backupFile + "ActiveWarpFile.txt");


    private static int maxBackupInFolder = 10;

    public static void addWarp(Location location, UUID playerUUID, String warpName)
    {
        if(playerUUID != null && warpName != null && location != null)
            warpList.add(new WarpObj(location, playerUUID, warpName));
        else
            Bukkit.getPlayer(playerUUID).sendMessage(MessageFormatting.error("Alright, something went wrong, please contact the developer for support!"));
        saveFile();
    }

    public static int getWarpAmount(UUID playerUUID)
    {
        int amount = 0;

        for(WarpObj o : warpList)
        {
            if(o.getOwner().equals(playerUUID))
                amount++;
        }

        return amount;
    }

    public static List<WarpObj> getWarpList(UUID playerUUID)
    {
        List<WarpObj> warpwarp = new ArrayList<WarpObj>();

        for(WarpObj o : warpList)
        {
            if(o.getOwner().equals(playerUUID))
                warpwarp.add(o);
        }

        return warpwarp;
    }

    public static boolean tryDeleteWarp(String warpName, UUID playerUUID)
    {
        List<WarpObj> playerWarpList = getWarpList(playerUUID);

        for(WarpObj o : playerWarpList)
        {
            if(o.getWarpName().equals(warpName))
            {
                warpList.remove(o);
                saveFile();
                return true;
            }
        }
        return false;
    }

    public static void saveFile()
    {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(warpList));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");

    public static void forceBackup()
    {
        //Folder creation
        if(backupFile.mkdir())
            Bukkit.getLogger().info("Warp backup folder created");
        else
            Bukkit.getLogger().info("Warp backup folder wasn't created");

        //Remove backups if needed
        deleteOldFiles(backupFile);

        //Save new backup
        try {
            String fileName = "Backup - " + dateFormat.format(new Date());

            FileWriter writer = new FileWriter(backupFile + "/" + fileName);

            writer.write(gson.toJson(warpList));
            writer.close();
            Bukkit.getLogger().info("Backup successful: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFile()
    {
        try
        {
            if(!file.exists())
            {
                Bukkit.getLogger().info("Warp file doesn't exist, this should only happen if no one has ever created a warp");
                return;
            }

            Scanner scanner = new Scanner(file);
            String recombobulatedFile = "";

            while(scanner.hasNextLine()) { recombobulatedFile += scanner.nextLine() + "\n"; }

            warpList = gson.fromJson(recombobulatedFile, arrayListType);
            Bukkit.getLogger().info("Backup loaded: " + warpList.size() + " item(s) loaded");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static void deleteOldFiles(File file)
    {
        if(file.isDirectory())
        {
            int deletedFiles = 0;

            long cutoff = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000);
            Iterator<File> filesToDelete =
                    FileUtils.iterateFiles(file, new AgeFileFilter(cutoff), TRUE);
            for (Iterator<File> it = filesToDelete; it.hasNext(); ) {
                File aFile = it.next();
                aFile.delete();
                deletedFiles++;
            }
            Bukkit.getLogger().info("Done, deleted " + deletedFiles + " files.");
            return;
        }
        Bukkit.getLogger().info("File passed to deleteOldFiles was not a directory D: ??");
    }
}
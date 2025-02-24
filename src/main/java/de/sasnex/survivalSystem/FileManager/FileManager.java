package de.sasnex.survivalSystem.FileManager;

import de.sasnex.survivalSystem.SurvivalSystem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public File path = new File("plugins/SurvivalSystem");

    public File spawnFile = new File(path+"/spawn.yml");
    public File warpsFile = new File(path + "/warps.yml");

    public YamlConfiguration spawnCFG = YamlConfiguration.loadConfiguration(spawnFile);
    public YamlConfiguration warpsCFG = YamlConfiguration.loadConfiguration(warpsFile);

    public YamlConfiguration getSpawnCFG() {loadSpawnCFG(); return spawnCFG; }
    public YamlConfiguration getWarpsCFG() {loadWarpCFG(); return warpsCFG;}


    public void createFolder(){
        if(!path.exists()){
            path.mkdir();
        }
    }

    public void setSpawnData(String path, Object value){
        try{
            loadSpawnCFG();
            spawnCFG.set(path,value);
            saveSpawnCFG();
        } catch (Exception e){
            SurvivalSystem.sendConsoleMsg("&cDatei &5spawn.yml &cDaten können nicht gesetzt werden\n Grund: &4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setWarpsData(String path, Object value){
        try{
            loadWarpCFG();
            warpsCFG.set(path,value);
            saveWarpCFG();
        } catch (Exception e){
            SurvivalSystem.sendConsoleMsg("&cDatei &5warp.yml &cDaten können nicht gesetzt werden\n Grund: &4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    //Lädt die Spawncfg datei
    public void loadSpawnCFG(){
        try{
            spawnCFG.load(spawnFile);
        } catch (IOException | InvalidConfigurationException e){
            SurvivalSystem.sendConsoleMsg("&cDatei &5spawn.yml &ckonnte nicht geladen werden \n Grund: &4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    //Lädt die Warpcfg Datei
    public void loadWarpCFG(){
        try{
            warpsCFG.load(warpsFile);
        } catch (IOException | InvalidConfigurationException e){
            SurvivalSystem.sendConsoleMsg("&cDatei &5warp.yml &ckonnte nicht geladen werden \n Grund: &4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveSpawnCFG(){
        try {
            spawnCFG.save(spawnFile);
        } catch (IOException e) {
            SurvivalSystem.sendConsoleMsg("&cDatei &5spawn.yml &ckonnte nicht gespeichert werden \n Grund: &4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveWarpCFG(){
        try {
            warpsCFG.save(warpsFile);
        } catch (IOException e) {
            SurvivalSystem.sendConsoleMsg("&cDatei &5warp.yml &ckonnte nicht gespeichert werden \n Grund: &4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //checkt ob spawn Datei Existiert
    public boolean checkSpawnFileExists(){
        if(!spawnFile.exists()){
            SurvivalSystem.getInstance().saveResource("spawn.yml", false);
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        spawnFile.createNewFile();
                        SurvivalSystem.sendConsoleMsg("&6'spawn.yml' &awurde erstellt");
                    } catch (IOException e) {
                        SurvivalSystem.sendConsoleMsg("&6Konnte 'spawn.yml' &6nicht erstellen, &4" + e.getMessage());
                    }
                }
            }.runTaskLater(SurvivalSystem.getInstance(), 20);
            return false;
        }
        return false;
    }

    //checkt ob warp Datei Existiert
    public boolean checkWarpFileExists(){
        if(!warpsFile.exists()){
            SurvivalSystem.getInstance().saveResource("warp.yml", false);
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        warpsFile.createNewFile();
                        SurvivalSystem.sendConsoleMsg("&6'warp.yml' &awurde erstellt");
                    } catch (IOException e) {
                        SurvivalSystem.sendConsoleMsg("&6Konnte 'warp.yml' &6nicht erstellen, &4\" + e.getMessage()");
                    }
                }
            }.runTaskLater(SurvivalSystem.getInstance(), 20);
            return false;
        }
        return false;
    }
}

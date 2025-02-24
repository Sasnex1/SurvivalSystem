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

    public YamlConfiguration spawnCFG = YamlConfiguration.loadConfiguration(spawnFile);
    public YamlConfiguration getSpawnCFG() {loadSpawnCFG(); return spawnCFG; }

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
            Bukkit.getConsoleSender().sendMessage("§cDatei §5spawn.yml §cDaten können nicht gesetzt werden\n Grund: §4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void loadSpawnCFG(){
        try{
            spawnCFG.load(spawnFile);
        } catch (IOException | InvalidConfigurationException e){
            Bukkit.getConsoleSender().sendMessage("§cDatei §5spawn.yml §ckonnte nicht geladen werden \n Grund: §4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveSpawnCFG(){
        try {
            spawnCFG.save(spawnFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§cDatei §5spawn.yml §ckonnte nicht gespeichert werden \n Grund: §4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean checkSpawnFileExists(){
        if(!spawnFile.exists()){
            SurvivalSystem.getInstance().saveResource("spawn.yml", false);
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        spawnFile.createNewFile();
                        SurvivalSystem.getInstance().getServer().getConsoleSender().sendMessage(SurvivalSystem.translateChat("&6'spawn.yml' &awurde erstellt"));
                    } catch (IOException e) {
                        Bukkit.broadcastMessage(SurvivalSystem.translateChat("&6Konnte 'spawn.yml' &6nicht erstellen, &4" + e.getMessage()));
                    }
                }
            }.runTaskLater(SurvivalSystem.getInstance(), 20);
            return false;
        }
        return false;
    }
}

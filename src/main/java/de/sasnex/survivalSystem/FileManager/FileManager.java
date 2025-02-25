package de.sasnex.survivalSystem.FileManager;

import de.sasnex.survivalSystem.SurvivalSystem;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public File path = new File("plugins/SurvivalSystem");

    public File spawnFile = new File(path+"/spawn.yml");
    public File warpsFile = new File(path + "/warp.yml");
    public File homesFile = new File(path + "/home.yml");

    public YamlConfiguration spawnCFG = YamlConfiguration.loadConfiguration(spawnFile);
    public YamlConfiguration warpsCFG = YamlConfiguration.loadConfiguration(warpsFile);
    public YamlConfiguration homesCFG = YamlConfiguration.loadConfiguration(homesFile);

    public YamlConfiguration getSpawnCFG() {loadSpawnCFG(); return spawnCFG; }
    public YamlConfiguration getWarpsCFG() {loadWarpCFG(); return warpsCFG;}
    public YamlConfiguration getHomesCFG() {loadHomeCFG(); return homesCFG;}

    //FOR MSQL
    private File mysqlfile = new File(path, "/mysql.yml");
    private FileConfiguration config;
    private String host;
    private String database;
    private String user;
    private String password;

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

    public void setHomesData(String path, Object value){
        try{
            loadHomeCFG();
            homesCFG.set(path,value);
            saveHomeCFG();
        } catch (Exception e){
            SurvivalSystem.sendConsoleMsg("&cDatei &5home.yml &cDaten können nicht gesetzt werden\n Grund: &4" + e.getMessage());
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

    //Lädt HomeCFG Datei
    public void loadHomeCFG(){
        try{
            homesCFG.load(homesFile);
        } catch (IOException | InvalidConfigurationException e){
            SurvivalSystem.sendConsoleMsg("&cDatei &5home.yml &ckonnte nicht geladen werden \n Grund: &4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Speichert, die Daten die gesetzt wurden
    public void saveSpawnCFG(){
        try {
            spawnCFG.save(spawnFile);
        } catch (IOException e) {
            SurvivalSystem.sendConsoleMsg("&cDatei &5spawn.yml &ckonnte nicht gespeichert werden \n Grund: &4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Speichert, die Daten die gesetzt wurden
    public void saveWarpCFG(){
        try {
            warpsCFG.save(warpsFile);
        } catch (IOException e) {
            SurvivalSystem.sendConsoleMsg("&cDatei &5warp.yml &ckonnte nicht gespeichert werden \n Grund: &4" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveHomeCFG(){
        try {
            homesCFG.save(homesFile);
        } catch (IOException e) {
            SurvivalSystem.sendConsoleMsg("&cDatei &5home.yml &ckonnte nicht gespeichert werden \n Grund: &4" + e.getMessage());
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

    //checkt ob home Datei Existiert
    public boolean checkHomeFileExists(){
        if(!homesFile.exists()){
            SurvivalSystem.getInstance().saveResource("home.yml", false);
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        homesFile.createNewFile();
                        SurvivalSystem.sendConsoleMsg("&6'home.yml' &awurde erstellt");
                    } catch (IOException e) {
                        SurvivalSystem.sendConsoleMsg("&6Konnte 'home.yml' &6nicht erstellen, &4\" + e.getMessage()");
                    }
                }
            }.runTaskLater(SurvivalSystem.getInstance(), 20);
            return false;
        }
        return false;
    }

    public boolean warpExists(String warpname) {
        return getWarpsCFG().contains(warpname+".worldname");
    }

    public boolean homeExists(String playerName, String homeName) {
        return getHomesCFG().contains(playerName + ".homes." + homeName + ".worldname");
    }

    public void deleteWarp(String warpName) {
        if (getWarpsCFG().contains(warpName)) {
            getWarpsCFG().set(warpName, null);  // Entfernt den Eintrag aus der Konfiguration
            saveWarpCFG();  // Speichert die Änderungen in der Datei
        }
    }

    //MYSQL
    private void create() {
        this.config.set("Host", "localhost");
        this.config.set("Database", "database");
        this.config.set("User", "root");
        this.config.set("Password", "password");
        this.config.options().copyDefaults(true);

        try {
            this.config.save(this.mysqlfile);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    private void load() {
        this.host = this.config.getString("Host");
        this.database = this.config.getString("Database");
        this.user = this.config.getString("User");
        this.password = this.config.getString("Password");
    }

    public File getMysqlfile() {
        return this.mysqlfile;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public String getHost() {
        return this.host;
    }

    public String getDatabase() {
        return this.database;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean checkMysqlFileExists(){
        if(!mysqlfile.exists()){
            SurvivalSystem.getInstance().saveResource("mysql.yml", false);
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        mysqlfile.createNewFile();
                        SurvivalSystem.sendConsoleMsg("&6'mysql.yml' &awurde erstellt");
                    } catch (IOException e) {
                        SurvivalSystem.sendConsoleMsg("&6Konnte 'mysql.yml' &6nicht erstellen, &4\" + e.getMessage()");
                    }
                }
            }.runTaskLater(SurvivalSystem.getInstance(), 20);
            return false;
        }
        return false;
    }
}
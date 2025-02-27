package de.sasnex.survivalSystem;

import de.sasnex.survivalSystem.Commands.*;
import de.sasnex.survivalSystem.FileManager.FileManager;
import de.sasnex.survivalSystem.Listeners.BlockListener;
import de.sasnex.survivalSystem.Listeners.JoinListener;
import de.sasnex.survivalSystem.Listeners.QuitListener;
import de.sasnex.survivalSystem.Utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SurvivalSystem extends JavaPlugin {

    static SurvivalSystem instance;
    FileManager fileManager = new FileManager();
    MySQL sql;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        sql = new MySQL();
        sql.connect();

        sendConsoleMsg("&6SurvivalSystem &aAktiviert");
        sendConsoleMsg("&eMade by Sasnex");

        fileManager.createFolder();

        fileManager.checkMysqlFileExists();
        fileManager.checkSpawnFileExists();
        fileManager.checkWarpFileExists();
        fileManager.checkHomeFileExists();

        listener();
        commands();
    }

    void commands(){
        new SpawnCMD();
        new WarpCMD();
        new HomeCMD();
        new InvseeCMD();
        new FlyCMD();
        new GamemodeCMD();
    }

    void listener(){
        getServer().getPluginManager().registerEvents(new JoinListener(), instance);
        getServer().getPluginManager().registerEvents(new QuitListener(), instance);
        getServer().getPluginManager().registerEvents(new BlockListener(), instance);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        sql.disconnect();
        sendConsoleMsg("&6SurvivalSystem &cDeaktiviert");
    }

    public static SurvivalSystem getInstance() {
        return instance;
    }

    public static String getPrefix() {return "&8» &a&lBreezy&f&lMC &8• ";}
    public static String translateChat(String msg){return ChatColor.translateAlternateColorCodes('&', msg);}
    public static void sendConsoleMsg(String msg){
        Bukkit.getConsoleSender().sendMessage(translateChat(getPrefix()+msg));
    }

    public static String noPerms(){
        return "&cDafür hast du keine Rechte!";
    }
}

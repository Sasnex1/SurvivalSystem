package de.sasnex.survivalSystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SurvivalSystem extends JavaPlugin {

    SurvivalSystem instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        sendConsoleMsg("&6SurvivalSystem &aAktiviert");
        sendConsoleMsg("&eMade by Sasnex");
    }

    void commands(){

    }

    void listener(){

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        sendConsoleMsg("&6SurvivalSystem &cDeaktiviert");
    }

    public static String getPrefix() {return "&8» &a&lBreezy&f&lMC &8• ";}
    public static String translateChat(String msg){return ChatColor.translateAlternateColorCodes('&', msg);}
    public static void sendConsoleMsg(String msg){
        Bukkit.getConsoleSender().sendMessage(translateChat(getPrefix()+msg));
    }
}

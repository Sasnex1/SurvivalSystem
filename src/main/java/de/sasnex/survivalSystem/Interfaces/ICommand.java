package de.sasnex.survivalSystem.Interfaces;

import de.sasnex.survivalSystem.FileManager.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

/// ist nur ein Interface nichts Besonderes
public interface ICommand {
    //Kits kits = new Kits();
    //GUIS gui = new GUIS();
    FileManager fm = new FileManager();
    boolean onCommand(@NonNull CommandSender sender,@NonNull Command cmd,@NonNull String s,@NonNull String[] args);
}

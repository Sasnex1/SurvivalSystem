package de.sasnex.survivalSystem.Interfaces;

import de.sasnex.survivalSystem.FileManager.*;
import de.sasnex.survivalSystem.Utils.MoneySystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

/// ist nur ein Interface nichts Besonderes
public interface ICommand {
    MoneySystem ms = MoneySystem.getInstance();
    FileManager fm = new FileManager();
    boolean onCommand(@NonNull CommandSender sender,@NonNull Command cmd,@NonNull String s,@NonNull String[] args);
}

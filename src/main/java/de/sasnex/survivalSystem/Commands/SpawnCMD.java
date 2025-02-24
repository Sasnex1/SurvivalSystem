package de.sasnex.survivalSystem.Commands;

import de.sasnex.survivalSystem.Interfaces.ICommand;
import de.sasnex.survivalSystem.SurvivalSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SpawnCMD implements ICommand, CommandExecutor {

    public SpawnCMD() {
        Objects.requireNonNull(SurvivalSystem.getInstance().getCommand("spawn")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String s, @NotNull @NonNull String[] args) {
        if(sender instanceof Player player){
            if(args.length == 0){
                player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix()+"&cDu wirst zum Spawn Teleportiert"));
            } else if(args[0].equalsIgnoreCase("set")){
                player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix()+"&cSpawn gesetzt"));
                return true;
            }
        } else {
            sender.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix()+"&cNur Spieler können das ausführen"));
            return true;
        }
        return false;
    }
}

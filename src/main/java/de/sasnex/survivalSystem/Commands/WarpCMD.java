package de.sasnex.survivalSystem.Commands;

import de.sasnex.survivalSystem.Interfaces.ICommand;
import de.sasnex.survivalSystem.SurvivalSystem;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WarpCMD implements ICommand, CommandExecutor {

    public WarpCMD() {
        Objects.requireNonNull(SurvivalSystem.getInstance().getCommand("warp")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String s, @NotNull @NonNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                //Setzt den Warp
            } else if (args[0].equalsIgnoreCase("set")) {
                if (player.hasPermission("ss.setwarp")) {
                    Location loc = player.getLocation();

                    double x = loc.getX();
                    double y = loc.getY();
                    double z = loc.getZ();
                    double yaw = loc.getYaw();
                    double pitch = loc.getPitch();
                    String worldname = Objects.requireNonNull(loc.getWorld()).getName();
                    String warpname = args[0];

                    //Überprüft ob warpname nicht leer ist sprich →" "
                    if (!warpname.isEmpty()) {
                        fm.setWarpsData(warpname + ".worldname", worldname);
                        fm.setWarpsData(warpname + ".x", x);
                        fm.setWarpsData(warpname + ".y", y);
                        fm.setWarpsData(warpname + ".z", z);
                        fm.setWarpsData(warpname + ".yaw", yaw);
                        fm.setWarpsData(warpname + ".pitch", pitch);

                        player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + " &6Warp wurde gesetzt &a" + warpname));
                        return true;
                    } else {
                        player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cEs wurde kein warpname eingegeben!"));
                        return true;
                    }
                }
            }
        } else {
            sender.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cNur Spieler können das ausführen"));
            return true;
        }
        return false;
    }
}

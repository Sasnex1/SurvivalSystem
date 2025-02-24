package de.sasnex.survivalSystem.Commands;

import de.sasnex.survivalSystem.Interfaces.ICommand;
import de.sasnex.survivalSystem.SurvivalSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
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
                //Spawnt den Spieler am Spawn Logischerweise
                Location loc = player.getLocation();

                double x = filemanager.getSpawnCFG().getDouble("spawn.x");
                double y = filemanager.getSpawnCFG().getDouble("spawn.y");;
                double z = filemanager.getSpawnCFG().getDouble("spawn.z");;
                double yaw = filemanager.getSpawnCFG().getDouble("spawn.yaw");;
                double pitch = filemanager.getSpawnCFG().getDouble("spawn.pitch");;
                String worldname = filemanager.getSpawnCFG().getString("spawn.worldname");

                if(worldname != null) {
                    World world = Bukkit.getWorld(worldname);
                    loc.setX(x);
                    loc.setY(y);
                    loc.setZ(z);
                    loc.setYaw((float)yaw);
                    loc.setPitch((float)pitch);
                    loc.setWorld(world);

                    player.sendActionBar(SurvivalSystem.translateChat(SurvivalSystem.getPrefix()+"&aDu wirst in 3 Sekunden Teleportiert"));

                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            player.teleport(loc);
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,1.0f,1.0f);
                            player.sendActionBar(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&aDu wurdest Teleportiert"));
                        }
                    }.runTaskLater(SurvivalSystem.getInstance(), 3*20L);
                    return true;
                }
                player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix()+"&cDu wirst zum Spawn Teleportiert"));
            } else if(args[0].equalsIgnoreCase("set")){
                if(player.hasPermission("ss.set")){
                    //Setzt die Spawn Location
                    Location loc = player.getLocation();

                    double x = loc.getX();
                    double y = loc.getY();
                    double z = loc.getZ();
                    double yaw = loc.getYaw();
                    double pitch = loc.getPitch();
                    String worldname = Objects.requireNonNull(loc.getWorld()).getName();

                    filemanager.setSpawnData("spawn.worldname", worldname);
                    filemanager.setSpawnData("spawn.x", x);
                    filemanager.setSpawnData("spawn.y", y);
                    filemanager.setSpawnData("spawn.z", z);
                    filemanager.setSpawnData("spawn.yaw", yaw);
                    filemanager.setSpawnData("spawn.pitch", pitch);
                    player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&6Spawn wurde gesetzt"));
                    return true;
                } else {
                    player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix()+SurvivalSystem.noPerms()));
                }
                return true;
            }
        } else {
            sender.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix()+"&cNur Spieler können das ausführen"));
            return true;
        }
        return false;
    }
}

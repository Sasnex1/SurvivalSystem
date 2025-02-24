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

public class WarpCMD implements ICommand, CommandExecutor {

    public WarpCMD() {
        Objects.requireNonNull(SurvivalSystem.getInstance().getCommand("warp")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String s, @NotNull @NonNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                //Teleportiert dich zum Warp
                String warpName = args[0];
                String worldname = fm.getWarpsCFG().getString(warpName + ".worldname");
                double x = fm.getWarpsCFG().getDouble(warpName + ".x");
                double y = fm.getWarpsCFG().getDouble(warpName + ".y");
                double z = fm.getWarpsCFG().getDouble(warpName + ".z");
                double yaw = fm.getWarpsCFG().getDouble(warpName + ".yaw");
                double pitch = fm.getWarpsCFG().getDouble(warpName + ".pitch");
                assert worldname != null;
                World world = Bukkit.getWorld(worldname);
                Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);

                player.sendActionBar(SurvivalSystem.translateChat(SurvivalSystem.getPrefix()+"&aDu wirst in 3 Sekunden Teleportiert"));

                if (warpName != null) {
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            player.teleport(loc);
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,1.0f,1.0f);
                            player.sendActionBar(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&aDu wurdest Teleportiert"));
                        }
                    }.runTaskLater(SurvivalSystem.getInstance(), 3*20L);
                }
            } else if (args[0].equalsIgnoreCase("set")) {
                // /warp set home
                if (player.hasPermission("ss.setwarp")) {
                    Location loc = player.getLocation();

                    double x = loc.getX();
                    double y = loc.getY();
                    double z = loc.getZ();
                    double yaw = loc.getYaw();
                    double pitch = loc.getPitch();
                    String worldname = Objects.requireNonNull(loc.getWorld()).getName();
                    String warpname = args[1];

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
            } else if (args[0].equalsIgnoreCase("delete")){
                player.sendMessage("Warp wurde gelöscht");
                return true;
            }
        } else {
            sender.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cNur Spieler können das ausführen"));
            return true;
        }
        return false;
    }
}

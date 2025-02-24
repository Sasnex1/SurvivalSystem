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
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class WarpCMD implements ICommand, CommandExecutor {

    public WarpCMD() {
        Objects.requireNonNull(SurvivalSystem.getInstance().getCommand("warp")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                // Zeige alle gesetzten Warps an
                if (fm.getWarpsCFG().getKeys(false).isEmpty()) {
                    player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cEs wurden keine Warps gesetzt."));
                } else {
                    StringBuilder warpsList = new StringBuilder(SurvivalSystem.translateChat(
                            SurvivalSystem.getPrefix() + "&aVerfügbare Warps:\n"
                    ));
                    for (String warp : fm.getWarpsCFG().getKeys(false)) {
                        warpsList.append(" &6- &f").append(warp).append("\n");
                    }
                    player.sendMessage(SurvivalSystem.translateChat(warpsList.toString()));
                }
                return true;
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
                // Setze einen neuen Warp
                if (player.hasPermission("ss.setwarp")) {
                    String warpname = args[1];
                    Location loc = player.getLocation();

                    fm.setWarpsData(warpname + ".worldname", Objects.requireNonNull(loc.getWorld()).getName());
                    fm.setWarpsData(warpname + ".x", loc.getX());
                    fm.setWarpsData(warpname + ".y", loc.getY());
                    fm.setWarpsData(warpname + ".z", loc.getZ());
                    fm.setWarpsData(warpname + ".yaw", loc.getYaw());
                    fm.setWarpsData(warpname + ".pitch", loc.getPitch());

                    player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + " &6Warp gesetzt: &a" + warpname));
                } else {
                    player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cDazu hast du keine Berechtigung!"));
                }
                return true;
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("del")) {
                // Lösche einen Warp
                if (player.hasPermission("ss.delwarp")) {
                    String warpname = args[1];
                    if (fm.warpExists(warpname)) {
                        fm.deleteWarp(warpname);
                        player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&aWarp gelöscht: " + warpname));
                    } else {
                        player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cDieser Warp existiert nicht!"));
                    }
                } else {
                    player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cDazu hast du keine Berechtigung!"));
                }
                return true;
            }

            if (args.length == 1) {
                // Teleportiere zu einem Warp
                String warpname = args[0];
                if (fm.warpExists(warpname)) {
                    String worldname = fm.getWarpsCFG().getString(warpname + ".worldname");
                    double x = fm.getWarpsCFG().getDouble(warpname + ".x");
                    double y = fm.getWarpsCFG().getDouble(warpname + ".y");
                    double z = fm.getWarpsCFG().getDouble(warpname + ".z");
                    double yaw = fm.getWarpsCFG().getDouble(warpname + ".yaw");
                    double pitch = fm.getWarpsCFG().getDouble(warpname + ".pitch");

                    if (worldname == null || Bukkit.getWorld(worldname) == null) {
                        player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cDie Welt existiert nicht!"));
                        return true;
                    }

                    World world = Bukkit.getWorld(worldname);
                    Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);

                    player.sendActionBar(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&aTeleportiere zu Warp: &6" + warpname));

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.teleport(loc);
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                            player.sendActionBar(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&aDu wurdest teleportiert."));
                        }
                    }.runTaskLater(SurvivalSystem.getInstance(), 3 * 20L);
                } else {
                    player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cDieser Warp existiert nicht!"));
                }
                return true;
            }

        } else {
            sender.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cNur Spieler können diesen Befehl ausführen!"));
        }
        return false;
    }
}

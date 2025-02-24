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

public class WarpCMD implements ICommand, CommandExecutor { ;

    public WarpCMD() {
        Objects.requireNonNull(SurvivalSystem.getInstance().getCommand("warp")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                // Teleportation zu einem Warp
                String warpName = args[0];
                if (fm.warpExists(warpName)) {
                    String worldname = fm.getWarpsCFG().getString(warpName + ".worldname");
                    double x = fm.getWarpsCFG().getDouble(warpName + ".x");
                    double y = fm.getWarpsCFG().getDouble(warpName + ".y");
                    double z = fm.getWarpsCFG().getDouble(warpName + ".z");
                    double yaw = fm.getWarpsCFG().getDouble(warpName + ".yaw");
                    double pitch = fm.getWarpsCFG().getDouble(warpName + ".pitch");

                    assert worldname != null;
                    World world = Bukkit.getWorld(worldname);

                    if (world == null) {
                        player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + " &cDie Welt existiert nicht!"));
                        return true;
                    }

                    Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);

                    player.sendActionBar(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&aDu wirst in 3 Sekunden teleportiert"));

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.teleport(loc);
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                            player.sendActionBar(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&aDu wurdest teleportiert"));
                        }
                    }.runTaskLater(SurvivalSystem.getInstance(), 3 * 20L);
                } else {
                    player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + " &cDieser Warp existiert nicht!"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("set")) {
                if (player.hasPermission("ss.setwarp")) {
                    if (args.length >= 2) {
                        String warpname = args[1];
                        if (!warpname.isEmpty()) {
                            Location loc = player.getLocation();

                            fm.setWarpsData(warpname + ".worldname", Objects.requireNonNull(loc.getWorld()).getName());
                            fm.setWarpsData(warpname + ".x", loc.getX());
                            fm.setWarpsData(warpname + ".y", loc.getY());
                            fm.setWarpsData(warpname + ".z", loc.getZ());
                            fm.setWarpsData(warpname + ".yaw", loc.getYaw());
                            fm.setWarpsData(warpname + ".pitch", loc.getPitch());

                            player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + " &6Warp wurde gesetzt: &a" + warpname));
                        } else {
                            player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cKein Warpname angegeben!"));
                        }
                    } else {
                        player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cBitte gib einen Warpnamen an!"));
                    }
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("delete")) {
                if (player.hasPermission("ss.deletewarp")) {
                    if (args.length >= 2) {
                        String warpname = args[1];
                        if (fm.warpExists(warpname)) {
                            fm.deleteWarp(warpname);
                            player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&aWarp gelöscht: " + warpname));
                        } else {
                            player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cDieser Warp existiert nicht!"));
                        }
                    } else {
                        player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cBitte gib einen Warpnamen an!"));
                    }
                    return true;
                }
            }
        } else {
            sender.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cNur Spieler können diesen Befehl ausführen"));
        }
        return false;
    }
}

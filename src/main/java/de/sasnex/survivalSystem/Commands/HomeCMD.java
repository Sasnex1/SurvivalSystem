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
import java.util.Set;

public class HomeCMD implements ICommand, CommandExecutor {

    public HomeCMD(){
        Objects.requireNonNull(SurvivalSystem.getInstance().getCommand("home")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String s, @NotNull @NonNull String[] args) {
        if(sender instanceof Player player){
            if(args.length == 1){
                //Teleportiert dich zum Home
                String homename = args[0];
                if(fm.homeExists(player.getName(), homename)){
                    String worldname = fm.getHomesCFG().getString(player.getName() + ".homes." + homename + ".worldname");
                    double x = fm.getHomesCFG().getDouble(player.getName() + ".homes." + homename + ".x");
                    double y = fm.getHomesCFG().getDouble(player.getName() + ".homes." + homename + ".y");
                    double z = fm.getHomesCFG().getDouble(player.getName() + ".homes." + homename + ".z");
                    double yaw = fm.getHomesCFG().getDouble(player.getName() + ".homes." + homename + ".yaw");
                    double pitch = fm.getHomesCFG().getDouble(player.getName() + ".homes." + homename + ".pitch");

                    if(worldname == null || Bukkit.getWorld(worldname) == null){
                        player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cDie Welt für dieses Home existiert nicht."));
                        return true;
                    }

                    World world = Bukkit.getWorld(worldname);
                    Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);

                    player.sendActionBar(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&aDu wirst in 3 Sekunden teleportiert &6"+homename));

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.teleport(loc);
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                            player.sendActionBar(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&aDu wurdest zu deinem Home teleportiert."));
                        }
                    }.runTaskLater(SurvivalSystem.getInstance(), 3 * 20L);
                } else {
                    player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + "&cDieses Home existiert nicht."));
                }

            } else if (args.length >= 2 &&args[0].equalsIgnoreCase("set")){
                String homename = args[1];
                Location loc = player.getLocation();
                String worldname = Objects.requireNonNull(loc.getWorld()).getName();

                fm.setHomesData(player.getName() + ".homes." + homename + ".worldname", worldname);
                fm.setHomesData(player.getName() + ".homes." + homename + ".x", loc.getX());
                fm.setHomesData(player.getName() + ".homes." + homename + ".y", loc.getY());
                fm.setHomesData(player.getName() + ".homes." + homename + ".z", loc.getZ());
                fm.setHomesData(player.getName() + ".homes." + homename + ".yaw", loc.getYaw());
                fm.setHomesData(player.getName() + ".homes." + homename + ".pitch", loc.getPitch());

                player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix() + " &6Home wurde gesetzt: &a" + homename));

            } else if (args.length >= 2 && args[0].equalsIgnoreCase("delete")){
                String homename = args[1];
                fm.getHomesCFG().set(player.getName() + ".homes." + homename, null);
                fm.saveHomeCFG();
            } else if (args.length >= 2 && args[0].equalsIgnoreCase("list")){
                if(fm.getHomesCFG().contains(player.getName()+".homes")){
                    Set<String> homes = Objects.requireNonNull(
                            fm.getHomesCFG().getConfigurationSection(player.getName()+".home")
                    ).getKeys(false);
                    if(!homes.isEmpty()){
                        StringBuilder homesList = new StringBuilder(SurvivalSystem.translateChat(
                                SurvivalSystem.getPrefix() + "&aDeine Homes:\n"
                        ));

                        for (String home : homes) {
                            homesList.append(" &6- &f").append(home).append("\n");
                        }

                        player.sendMessage(homesList.toString());
                    } else {
                        player.sendMessage(SurvivalSystem.translateChat(
                                SurvivalSystem.getPrefix() + "&cDu hast keine Homes gesetzt."
                        ));
                    }

                    player.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix()+"&aDeine Homes\n"));
                } else {
                    player.sendMessage(SurvivalSystem.translateChat(
                            SurvivalSystem.getPrefix() + "&cDu hast keine Homes gesetzt."
                    ));
                }
            }
        } else {
            sender.sendMessage(SurvivalSystem.translateChat(SurvivalSystem.getPrefix()+"&cNur Spieler können das ausführen"));
            return true;
        }
        return false;
    }
}

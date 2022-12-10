package io.github.cardsandhuskers.lobbyplugin.commands;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SavePodiumLocationCommand implements CommandExecutor {
    private LobbyPlugin plugin;
    public SavePodiumLocationCommand(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p) {
            if(p.isOp()) {
                try {
                    int podiumIndex = Integer.parseInt(args[1]);
                    String podiumType = args[0].toLowerCase();
                    Location location = p.getLocation();
                    if(podiumType.equals("team") || podiumType.equals("player")) {
                        plugin.getConfig().set("Podiums." + podiumType + "." + podiumIndex, location);
                    } else {
                        throw new Exception();
                    }

                    plugin.saveConfig();
                    p.sendMessage("Location set at:\nWorld: " + location.getWorld() + "\nX: " + location.getX() + " Y: " + location.getY() + " Z: " + location.getZ());

                } catch (Exception e) {
                    p.sendMessage(ChatColor.RED + "ERROR: Command must have 1 string to represent either team or player and 1 integer to represent placement");
                }
            } else {
                p.sendMessage(ChatColor.RED + "You do not have Permission to do this");
            }


        } else {
            System.out.println("ERROR: cannot run from console.");
        }

        return true;
    }
}

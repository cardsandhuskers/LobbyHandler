package io.github.cardsandhuskers.lobbyplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminBypassCommand implements CommandExecutor {
private static HashMap<Player, Boolean> bypassed = new HashMap<>();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p) {
            if (p.isOp()) {
                if(isBypassed(p)) {
                    p.sendMessage(ChatColor.GREEN + "You are no longer able to interact in the lobby");
                    bypassed.put(p, false);
                } else {
                    p.sendMessage(ChatColor.GREEN + "You are now able to interact in the lobby");
                    bypassed.put(p, true);
                }



            } else {
                p.sendMessage(ChatColor.RED + "ERROR: You do not have permission to execute this command");
            }
        } else {
            System.out.println(ChatColor.RED + "ERROR: Running from console currently not supported");
        }

        return true;
    }

    public static boolean isBypassed(Player p) {
        Boolean isBypassed = bypassed.get(p);
        if(isBypassed == null || isBypassed == false) {
            return false;
        }
        return true;
    }
}

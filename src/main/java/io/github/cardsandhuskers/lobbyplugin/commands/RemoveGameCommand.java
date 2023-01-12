package io.github.cardsandhuskers.lobbyplugin.commands;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;

public class RemoveGameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p && p.isOp()) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "ERROR: Must specify a game");
                return true;
            }
            LobbyPlugin.NextGame game;
            try {
                game = LobbyPlugin.NextGame.valueOf(args[0].toUpperCase());
                remainingGames.remove(game);
            } catch (Exception e) {
                p.sendMessage(ChatColor.RED + "ERROR: Argument must be an enum of a game that's left");
                return true;
            }
            p.sendMessage(game + " removed");
            return true;
        } else if (sender instanceof Player p && !p.isOp()){
            p.sendMessage(ChatColor.RED + "You cannot do this");
            return true;
        } else {
            if (args.length == 0) {
                System.out.println(ChatColor.RED + "ERROR: Must specify a game");
                return true;
            }
            LobbyPlugin.NextGame game;
            try {
                game = LobbyPlugin.NextGame.valueOf(args[0].toUpperCase());
                remainingGames.remove(game);
            } catch (Exception e) {
                System.out.println(ChatColor.RED + "ERROR: Argument must be an enum of a game that's left");
                return true;
            }
            System.out.println(game + " removed");
            return true;
        }
    }
}

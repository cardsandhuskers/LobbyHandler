package io.github.cardsandhuskers.lobbyplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.gameNumber;
import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.multiplier;

public class SetGameNumber implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p && p.isOp()) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "ERROR: Must specify a number");
                return true;
            }
            int round;
            try {
                round = Integer.parseInt(args[0]);
            } catch (Exception e) {
                p.sendMessage(ChatColor.RED + "ERROR: Argument must be an integer");
                return true;
            }
            gameNumber = round;
            updateMult();
            p.sendMessage("round set to " + round);
            return true;
        } else if (sender instanceof Player p && !p.isOp()){
            p.sendMessage(ChatColor.RED + "You cannot do this");
            return true;
        } else {
            if (args.length == 0) {
                System.out.println(ChatColor.RED + "ERROR: Must specify a number");
                return true;
            }
            int round;
            try {
                round = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println(ChatColor.RED + "ERROR: Argument must be an integer");
                return true;
            }
            gameNumber = round;
            updateMult();
            System.out.println("round set to " + round);
            return true;
        }
    }
    public void updateMult() {
        switch(gameNumber) {
            case 1:
            case 2:
                multiplier = 1;
                break;
            case 3:
            case 4:
                multiplier = 1.5F;
                break;
            case 5:
            case 6:
                multiplier = 2F;
                break;
            case 7:
            case 8:
                multiplier = 2.5F;
                break;
        }
    }
}

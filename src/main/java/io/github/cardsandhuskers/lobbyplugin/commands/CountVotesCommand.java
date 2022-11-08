package io.github.cardsandhuskers.lobbyplugin.commands;

import io.github.cardsandhuskers.lobbyplugin.handlers.VoteCountHandler;
import io.github.cardsandhuskers.lobbyplugin.objects.VotingMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.votingMenuList;

public class CountVotesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p) {
            if (p.isOp()) {
                VoteCountHandler countHandler = new VoteCountHandler();
                countHandler.countVotes();
            } else {
                p.sendMessage(ChatColor.RED + "ERROR: You do not have permission to execute this command");
            }
        } else {
            System.out.println(ChatColor.RED + "ERROR: Running from console currently not supported");
        }



        return true;
    }




}

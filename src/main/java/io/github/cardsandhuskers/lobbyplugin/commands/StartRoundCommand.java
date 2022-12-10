package io.github.cardsandhuskers.lobbyplugin.commands;

import io.github.cardsandhuskers.lobbyplugin.handlers.CitizensHandler;
import io.github.cardsandhuskers.lobbyplugin.handlers.LobbyStageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;
import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.votingMenuList;

public class StartRoundCommand implements CommandExecutor {
    LobbyStageHandler lobbyStageHandler;
    //CitizensHandler citizensHandler;

    public StartRoundCommand(LobbyStageHandler lobbyStageHandler/*, CitizensHandler citizensHandler*/) {
        this.lobbyStageHandler = lobbyStageHandler;
        //this.citizensHandler = citizensHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //move this if statement to list
        if(!lobbyStage) {
            lobbyStage = true;
            lobbyStageHandler.init();
            gameNumber++;
            if((gameNumber + 1) %2 == 0) {
                multiplier += .5;
            }
            votingMenuList.clear();
            //citizensHandler.updateSkins();
        }



        return true;
    }
}

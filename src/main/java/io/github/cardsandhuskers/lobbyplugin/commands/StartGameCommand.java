package io.github.cardsandhuskers.lobbyplugin.commands;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.handlers.LobbyStageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.lobbyStage;

public class StartGameCommand implements CommandExecutor {

    LobbyPlugin plugin;
    LobbyStageHandler stageHandler;

    public StartGameCommand(LobbyPlugin plugin, LobbyStageHandler stageHandler) {
        this.plugin = plugin;
        this.stageHandler = stageHandler;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        LobbyPlugin.voting = true;
        lobbyStage = true;
        stageHandler.init();


        return false;
    }
}

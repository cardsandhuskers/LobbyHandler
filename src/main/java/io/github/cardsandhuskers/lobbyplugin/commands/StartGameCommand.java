package io.github.cardsandhuskers.lobbyplugin.commands;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.handlers.CitizensHandler;
import io.github.cardsandhuskers.lobbyplugin.handlers.LobbyStageHandler;
import io.github.cardsandhuskers.lobbyplugin.listeners.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.lobbyStage;
import static org.bukkit.Bukkit.getServer;

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

        //CitizensHandler citizensHandler = new CitizensHandler(plugin);
        //citizensHandler.buildNPCS();

        plugin.getCommand("startRound").setExecutor(new StartRoundCommand(stageHandler/*, citizensHandler*/));

        getServer().getPluginManager().registerEvents(new InventoryClickListener(plugin), plugin);
        getServer().getPluginManager().registerEvents(new ItemDropListener(plugin), plugin);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(plugin), plugin);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(plugin, stageHandler), plugin);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(plugin), plugin);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(plugin), plugin);




        return false;
    }
}

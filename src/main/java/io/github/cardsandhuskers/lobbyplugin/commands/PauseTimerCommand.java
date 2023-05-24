package io.github.cardsandhuskers.lobbyplugin.commands;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.handlers.LobbyStageHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.nextGame;

public class PauseTimerCommand implements CommandExecutor {
    boolean isPaused = false;
    private LobbyPlugin plugin;
    private LobbyStageHandler lobbyStageHandler;
    public PauseTimerCommand(LobbyPlugin plugin, LobbyStageHandler lobbyStageHandler) {
        this.plugin = plugin;
        this.lobbyStageHandler = lobbyStageHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p && p.isOp()) {
            pauseTimer();
        } else if(sender instanceof Player p) {
            p.sendMessage(ChatColor.RED + "You don't have permission to do this");
        }else {
            pauseTimer();
        }
        return true;
    }

    public void pauseTimer() {
        if(nextGame == LobbyPlugin.NextGame.VOTING) {
            if (isPaused) {
                lobbyStageHandler.votingTimer.scheduleTimer();
                isPaused = false;
            } else {
                lobbyStageHandler.votingTimer.cancelTimer();
                isPaused = true;
            }
        }else if(nextGame != LobbyPlugin.NextGame.IN_GAME) {
            if (isPaused) {
                lobbyStageHandler.pregameTimer.scheduleTimer();
                isPaused = false;
            } else {
                lobbyStageHandler.pregameTimer.cancelTimer();
                isPaused = true;
            }
        }
    }

}

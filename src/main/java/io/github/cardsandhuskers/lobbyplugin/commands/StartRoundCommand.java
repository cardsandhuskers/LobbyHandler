package io.github.cardsandhuskers.lobbyplugin.commands;

import io.github.cardsandhuskers.lobbyplugin.handlers.LobbyStageHandler;
import io.github.cardsandhuskers.lobbyplugin.listeners.ItemClickListener;
import io.github.cardsandhuskers.teams.objects.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;
import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.votingMenuList;
import static io.github.cardsandhuskers.teams.Teams.handler;

public class StartRoundCommand implements CommandExecutor {
    LobbyStageHandler lobbyStageHandler;
    //CitizensHandler citizensHandler;

    public StartRoundCommand(LobbyStageHandler lobbyStageHandler/*, CitizensHandler citizensHandler*/) {
        this.lobbyStageHandler = lobbyStageHandler;
        //this.citizensHandler = citizensHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p && p.isOp()) {
            startRound();
        } else if(sender instanceof Player p) {
            p.sendMessage(ChatColor.RED + "You do not have permission to do this");
        } else {
            startRound();
        }
        return true;
    }

    public void startRound() {
        if(!lobbyStage) {
            lobbyStage = true;
            voting = true;
            gameNumber++;
            lobbyStageHandler.init();
            if((gameNumber + 1) %2 == 0) {
                multiplier += .5;
            }
            votingMenuList.clear();
            //citizensHandler.updateSkins();
            try {
                savePoints();
            } catch (IOException e) {
                throw new RuntimeException("Points file could not be accessed, so points were NOT SAVED");
            }
        }
    }

    private void savePoints() throws IOException {
        File pointsFile = new File(Bukkit.getServer().getPluginManager().getPlugin("LobbyPlugin").getDataFolder(),"points.yml");
        if(!pointsFile.exists()) {
            //if the file does not exist, make it
            pointsFile.createNewFile();
        }
        FileConfiguration pointsFileConfig = YamlConfiguration.loadConfiguration(pointsFile);

        pointsFileConfig.set((gameNumber-1) + ".game", ItemClickListener.currentGame.toString());

        for(Team t: handler.getTeams()) {
            String path = (gameNumber-1) + "." + t.getTeamName() + ".";

            pointsFileConfig.set(path + "color", t.color);
            pointsFileConfig.set(path + "points", t.getPoints());
            pointsFileConfig.set(path + "tempPoints", t.getTempPoints());

            path += "players.";

            for(Player p:t.getOnlinePlayers()) {
                String playerPath = path + p.getUniqueId() + ".";

                pointsFileConfig.set(playerPath + "name", p.getDisplayName());
                pointsFileConfig.set(playerPath + "points", ppAPI.look(p.getUniqueId()));
                pointsFileConfig.set(playerPath + "tempPoints", handler.getPlayerTeam(p).getPlayerTempPointsValue(p));
            }
        }

        try {
            pointsFileConfig.save(pointsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

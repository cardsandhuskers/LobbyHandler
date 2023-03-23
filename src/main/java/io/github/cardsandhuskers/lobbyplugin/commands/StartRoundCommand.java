package io.github.cardsandhuskers.lobbyplugin.commands;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.handlers.LobbyStageHandler;
import io.github.cardsandhuskers.lobbyplugin.objects.StatCalculator;
import io.github.cardsandhuskers.teams.objects.Team;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;
import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.votingMenuList;
import static io.github.cardsandhuskers.lobbyplugin.handlers.LobbyStageHandler.currentGame;
import static io.github.cardsandhuskers.teams.Teams.handler;

public class StartRoundCommand implements CommandExecutor {
    LobbyStageHandler lobbyStageHandler;
    LobbyPlugin plugin;
    StatCalculator statCalculator;

    public StartRoundCommand(LobbyStageHandler lobbyStageHandler, LobbyPlugin plugin, StatCalculator statCalculator) {
        this.lobbyStageHandler = lobbyStageHandler;
        this.plugin = plugin;
        this.statCalculator = statCalculator;
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

            votingMenuList.clear();

            //put tempPoints into the main points holder
            for(Team t:handler.getTeams()) {
                for(OfflinePlayer p:t.getPlayers()) {
                    int points = (int) t.getPlayerTempPointsValue(p);
                    ppAPI.give(p.getUniqueId(), points);
                }
            }

            try {
                savePoints();
            } catch (IOException e) {
                throw new RuntimeException("Points file could not be accessed, so points were NOT SAVED");
            }
            lobbyStageHandler.init();
            if((gameNumber + 1) %2 == 0) {
                multiplier += .5;
            }
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, statCalculator::calculateStats);
    }

    private void savePoints() throws IOException {

        FileWriter writer = new FileWriter("plugins/LobbyPlugin/points" + plugin.getConfig().getInt("eventNum") + ".csv", true);
        FileReader reader = new FileReader("plugins/LobbyPlugin/points" + plugin.getConfig().getInt("eventNum") + ".csv");

        String[] headers = {"Game","Team", "Name", "Points", "Temp Points", "Multiplier"};

        CSVFormat.Builder builder = CSVFormat.Builder.create();
        builder.setHeader(headers);
        CSVFormat format = builder.build();

        CSVParser parser = new CSVParser(reader, format);

        if(!parser.getRecords().isEmpty()) {
            format = CSVFormat.DEFAULT;
        }

        CSVPrinter printer = new CSVPrinter(writer, format);

        //printer.printRecord(currentGame);
        for(Team team: handler.getTeams()) {
            printer.printRecord(currentGame, team.getTeamName(), "Total-", team.getPoints(), team.getTempPoints(), multiplier);
            for(Player p: team.getOnlinePlayers()) {
                printer.printRecord(currentGame, team.getTeamName(), p.getDisplayName(), ppAPI.look(p.getUniqueId()), team.getPlayerTempPointsValue(p), multiplier);
            }
        }

        writer.close();

    }
}

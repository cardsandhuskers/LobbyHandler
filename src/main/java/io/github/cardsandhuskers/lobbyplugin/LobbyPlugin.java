package io.github.cardsandhuskers.lobbyplugin;

import io.github.cardsandhuskers.lobbyplugin.commands.*;
import io.github.cardsandhuskers.lobbyplugin.handlers.LobbyStageHandler;
import io.github.cardsandhuskers.lobbyplugin.listeners.*;
import io.github.cardsandhuskers.lobbyplugin.objects.Placeholder;
import io.github.cardsandhuskers.lobbyplugin.objects.StatCalculator;
import io.github.cardsandhuskers.lobbyplugin.objects.VotingMenu;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.bukkit.Bukkit.getServer;

public final class LobbyPlugin extends JavaPlugin {
    public static ArrayList<VotingMenu> votingMenuList;
    public static PlayerPointsAPI ppAPI;

    public static ArrayList<NextGame> remainingGames;

    public static float multiplier = 1;

    public static int gameNumber;
    public static boolean lobbyStage = true;
    public static boolean voting = false;
    public static int timeVar = 0;
    public static String timerStage = "Voting Ends in";
    public static NextGame nextGame = NextGame.VOTING;
    @Override
    public void onEnable() {

        if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
            this.ppAPI = PlayerPoints.getInstance().getAPI();
        } else {
            System.out.println("PLAYER POINTS API IS NULL");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        StatCalculator statCalculator = new StatCalculator(this);


        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            /*
             * We register the EventListener here, when PlaceholderAPI is installed.
             * Since all events are in the main class (this class), we simply use "this"
             */
            new Placeholder(this, statCalculator).register();

        } else {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            System.out.println("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }


        /*
        if(getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        */

        getServer().getPluginManager().registerEvents(new ItemClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new ItemDropListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerClickEntityEvent(this), this);

        LobbyStageHandler stageHandler = new LobbyStageHandler(this);

        getCommand("setLobby").setExecutor(new SetLobbyCommand(this));
        getCommand("startEvent").setExecutor(new StartGameCommand(this, stageHandler, statCalculator));
        getCommand("setGameNumber").setExecutor(new SetGameNumber());
        getCommand("removeGame").setExecutor(new RemoveGameCommand());
        getCommand("pauseEvent").setExecutor(new PauseTimerCommand(this, stageHandler));

        votingMenuList = new ArrayList<>();
        multiplier = 1;
        gameNumber = 1;

        remainingGames = new ArrayList<>();
        remainingGames.add(NextGame.BATTLEBOX);
        remainingGames.add(NextGame.BINGO);
        remainingGames.add(NextGame.BUILDBATTLE);
        remainingGames.add(NextGame.DROPPER);
        remainingGames.add(NextGame.SKYWARS);
        remainingGames.add(NextGame.SURVIVALGAMES);
        remainingGames.add(NextGame.TAG);
        remainingGames.add(NextGame.TGTTOS);
        remainingGames.add(NextGame.TNTRUN);
        Collections.shuffle(remainingGames);

        statCalculator.calculateStats();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public enum NextGame {
        VOTING,
        IN_GAME,
        BATTLEBOX,
        BINGO,
        BUILDBATTLE,
        DROPPER,
        SURVIVALGAMES,
        TAG,
        TGTTOS,
        TNTRUN,
        SKYWARS,
        LASERDOME,
        //only used for point calculations
        TOTAL

    }
}

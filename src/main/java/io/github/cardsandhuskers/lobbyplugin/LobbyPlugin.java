package io.github.cardsandhuskers.lobbyplugin;

import io.github.cardsandhuskers.lobbyplugin.commands.CountVotesCommand;
import io.github.cardsandhuskers.lobbyplugin.commands.SetLobbyCommand;
import io.github.cardsandhuskers.lobbyplugin.commands.StartGameCommand;
import io.github.cardsandhuskers.lobbyplugin.handlers.LobbyStageHandler;
import io.github.cardsandhuskers.lobbyplugin.listeners.*;
import io.github.cardsandhuskers.lobbyplugin.objects.Placeholder;
import io.github.cardsandhuskers.lobbyplugin.objects.VotingMenu;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class LobbyPlugin extends JavaPlugin {
    public static ArrayList<VotingMenu> votingMenuList;
    public static PlayerPointsAPI ppAPI;

    public static ArrayList<String> remainingGames;

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

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            /*
             * We register the EventListener here, when PlaceholderAPI is installed.
             * Since all events are in the main class (this class), we simply use "this"
             */
            new Placeholder(this).register();

        } else {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            System.out.println("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        LobbyStageHandler stageHandler = new LobbyStageHandler(this);

        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new ItemClickListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemDropListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(this, stageHandler), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);



        getCommand("setLobby").setExecutor(new SetLobbyCommand(this));
        getCommand("countVotes").setExecutor(new CountVotesCommand());
        getCommand("startEvent").setExecutor(new StartGameCommand(this, stageHandler));

        votingMenuList = new ArrayList<>();
        multiplier = 1;
        gameNumber = 1;

        remainingGames = new ArrayList<>();
        remainingGames.add("battlebox");
        remainingGames.add("buildbattle");
        remainingGames.add("tntrun");
        remainingGames.add("survivalgames");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static enum NextGame {
        VOTING,
        BATTLEBOX,
        BUILDBATTLE,
        TNTRUN,
        SURVIVALGAMES,
        SKYWARS,
        DROPPER
    }
}

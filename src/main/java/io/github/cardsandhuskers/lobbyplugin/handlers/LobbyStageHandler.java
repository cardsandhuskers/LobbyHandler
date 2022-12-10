package io.github.cardsandhuskers.lobbyplugin.handlers;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.objects.Countdown;
import io.github.cardsandhuskers.lobbyplugin.objects.LobbyInventory;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;

public class LobbyStageHandler {
    private LobbyPlugin plugin;
    public LobbyStageHandler(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        initVotingTimer();
        System.out.println("VOTING TIMER INIT");
    }

    /**
     * Voting timer, for when players are allowed to vote
     */
    public void initVotingTimer() {
        LobbyInventory lobbyInv = new LobbyInventory();
        Countdown votingTimer = new Countdown((JavaPlugin)plugin,
                //should be 45
                plugin.getConfig().getInt("VotingTime"),
                ()-> {
                    //Timer Start

                    for(Player p:Bukkit.getOnlinePlayers()) {
                        p.sendTitle("Vote Now!", "", 5, 30, 5);
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, .5F);
                        Inventory inv = p.getInventory();
                        inv.clear();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()-> {
                            lobbyInv.addTeamSelector(p);
                            lobbyInv.addVotingItems(p, false);
                        },5L);
                    }
                    nextGame = NextGame.VOTING;
                    timerStage = "Voting Ends in";

                },
                () -> {
                    //Timer End

                    for(Player p:Bukkit.getOnlinePlayers()) {
                        lobbyInv.removeVotingItems(p);
                        initPregameTimer();
                        p.closeInventory();
                    }
                    VoteCountHandler voteCountHandler = new VoteCountHandler();
                    voteCountHandler.countVotes();
                    voting = false;
                },
                (t) -> {
                    //Each Second
                    timeVar = t.getSecondsLeft();
                    for(Player p:Bukkit.getOnlinePlayers()) {
                        if(t.getSecondsLeft() % 2 == 0) {
                            lobbyInv.votingAnimationA(p);
                        } else {
                            lobbyInv.votingAnimationB(p);
                        }
                    }
                }
        );
        votingTimer.scheduleTimer();
    }

    /**
     * Pregame timer, runs after voting ends to count down the time until next round is supposed to start
     */
    public void initPregameTimer() {
        //commenting out the delay, idk why it's here
        //Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()-> {
            Countdown pregameTimer = new Countdown((JavaPlugin)plugin,
                    plugin.getConfig().getInt("PregameTime"),
                    ()-> {
                        //Timer Start
                        for(Player p:Bukkit.getOnlinePlayers()) {
                            p.sendTitle(ChatColor.AQUA + "Voting Over", ChatColor.BLUE + "Next Game: " + nextGame, 5, 50, 5);
                            //p.playNote(p.getLocation(), Instrument.PLING, Note.natural(1));
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, .5F);
                        }
                        timerStage = "Next Game Starts in";
                    },
                    () -> {
                        //Timer End


                    },
                    (t) -> {
                        //Each Second
                        timeVar = t.getSecondsLeft();

                    }
            );
            pregameTimer.scheduleTimer();
        //}, 5L);
    }
}

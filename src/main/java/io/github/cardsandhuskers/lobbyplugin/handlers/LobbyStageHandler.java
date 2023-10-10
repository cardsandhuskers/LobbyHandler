package io.github.cardsandhuskers.lobbyplugin.handlers;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.objects.Countdown;
import io.github.cardsandhuskers.lobbyplugin.objects.LobbyInventory;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;

public class LobbyStageHandler {
    private LobbyPlugin plugin;
    public LobbyStageHandler(LobbyPlugin plugin) {
        this.plugin = plugin;
    }
    public static NextGame currentGame;
    public Countdown pregameTimer, votingTimer;

    public void init() {
        if(gameNumber == 9) {
            nextGame = NextGame.LASERDOME;
            initPregameTimer();
        } else {
            initVotingTimer();
        }
    }

    /**
     * Voting timer, for when players are allowed to vote
     */
    public void initVotingTimer() {
        LobbyInventory lobbyInv = new LobbyInventory();
        for(Player p:Bukkit.getOnlinePlayers()) {
            p.sendTitle("Vote Now!", "", 5, 30, 5);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, .5F);
            Inventory inv = p.getInventory();
            inv.clear();
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()-> {
                lobbyInv.addTeamSelector(p);
                lobbyInv.addVotingItems(p, false);
                p.setHealth(20);
                p.setFoodLevel(20);
                p.setSaturation(20);
            },5L);
            for(PotionEffect effect:p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
        }
        nextGame = NextGame.VOTING;
        timerStage = "Voting Ends in";


        votingTimer = new Countdown((JavaPlugin)plugin,
                //should be 45
                plugin.getConfig().getInt("VotingTime"),
                ()-> {
                    //Timer Start
                },
                () -> {
                    //Timer End

                    for(Player p:Bukkit.getOnlinePlayers()) {
                        lobbyInv.removeVotingItems(p);
                        p.closeInventory();
                    }
                    VoteCountHandler voteCountHandler = new VoteCountHandler();
                    voteCountHandler.countVotes(true);
                    voting = false;
                    initPregameTimer();
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
        int time;
        if(gameNumber == 5) {
            time = plugin.getConfig().getInt("BreakTime");
        } else {
            time = plugin.getConfig().getInt("PregameTime");
        }

        if(gameNumber == 5) {
            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Break Time!");
        }
        if(gameNumber == 9) {
            Bukkit.broadcastMessage("FINAL");
        }
        timerStage = "Next Game Starts in";
        for(Player p:Bukkit.getOnlinePlayers()) {
            if(nextGame == NextGame.LASERDOME) {
                p.sendTitle(ChatColor.AQUA + "FINAL ROUND", ChatColor.BLUE + "The Laserdome", 5, 50, 5);
            } else {
                p.sendTitle(ChatColor.AQUA + "Voting Over", ChatColor.BLUE + "Next Game: " + getGame(), 5, 50, 5);
            }

            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, .5F);
        }

        pregameTimer = new Countdown((JavaPlugin)plugin,
                time,
                ()-> {
                    //Timer Start
                },
                () -> {
                    //Timer End

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "p export confirm");

                    switch(nextGame) {
                        case BATTLEBOX:
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "startbattlebox " + multiplier);
                            break;
                        case BINGO: Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "startBingo " + multiplier);
                            break;
                        case BUILDBATTLE: Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "startBuildBattle " + multiplier);
                            break;
                        case PARKOUR: Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "startParkour " + multiplier);
                            break;
                        case TGTTOS: Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "startTGTTOS " + multiplier);
                            break;
                        case TNTRUN: Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "startTNTRun " + multiplier);
                            break;
                        case SURVIVALGAMES: Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "startsurvivalgames " + multiplier + " SURVIVAL_GAMES");
                            break;
                        case DROPPER: Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "startDropper " + multiplier);
                            break;
                        case SKYWARS: Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "startSurvivalGames " + multiplier + " SKYWARS");
                            break;
                        case LASERDOME: Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "startLaserdome");
                            break;
                        case TAG: Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "startTag " + multiplier);
                            break;
                    }
                    lobbyStage = false;
                    currentGame = nextGame;
                    nextGame = NextGame.IN_GAME;

                },
                (t) -> {
                    //Each Second
                    timeVar = t.getSecondsLeft();

                }
        );
        pregameTimer.scheduleTimer();
    }
    public String getGame() {
        switch(nextGame) {
            case BATTLEBOX: return "Battlebox";
            case BINGO: return "Bingo";
            case BUILDBATTLE: return "Build Battle";
            case DROPPER: return "The Dropper";
            case PARKOUR: return "Hardcore Parkour";
            case SKYWARS: return "Sky Wars";
            case SURVIVALGAMES: return "Survival Games";
            case TAG: return "Parkour Tag";
            case TGTTOS: return "To Get to the Other Side";
            case TNTRUN: return "TNT Run";
            case VOTING: return "Voting...";
            case LASERDOME: return "Laserdome";
            default: return "ERROR";
        }
    }
}

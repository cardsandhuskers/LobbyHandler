package io.github.cardsandhuskers.lobbyplugin.handlers;

import io.github.cardsandhuskers.lobbyplugin.objects.VotingMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;

public class VoteCountHandler {
    private String game;
    public void countVotes() {

        /*
        int battleboxCount = 0;
        int buildbattleCount = 0;
        int tntrunCount = 0;
        int survivalGamesCount = 0;
        int dropperCount = 0;
         */
        ArrayList<Integer> votingCount = new ArrayList<>();
        for(NextGame g:remainingGames) {
            votingCount.add(0);
        }


        for(VotingMenu m:votingMenuList) {
            NextGame vote = m.getVote();
            if(vote!= null) {
                /*
                switch(vote) {
                    case "battlebox":
                        battleboxCount++;
                        break;
                    case "buildbattle":
                        buildbattleCount++;
                        break;
                    case "tntrun":
                        tntrunCount++;
                        break;
                    case "survivalgames":
                        survivalGamesCount++;
                        break;
                    case "dropper":
                        dropperCount++;
                        break;
                }
                */
                int i = 0;
                for(NextGame s:remainingGames) {
                    if(vote.equals(s)) {
                        votingCount.set(i, votingCount.get(i) + 1);
                    }
                    i++;
                }
            }
        }


        //ArrayList<Integer> votesList = new ArrayList<>();
/*
        votesList.add(0, battleboxCount);
        votesList.add(1, buildbattleCount);
        votesList.add(2, tntrunCount);
        votesList.add(3, survivalGamesCount);
        votesList.add(4, dropperCount);

 */
        int max = Collections.max(votingCount);
        int maxLocation = votingCount.indexOf(max);


        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "------------------------------\n" + ChatColor.RESET + "Voting Is Over, Results: ");
        int i = 0;
        for(NextGame s:remainingGames) {
            Bukkit.broadcastMessage(s + ": " + votingCount.get(i));
            i++;
        }
        //Bukkit.broadcastMessage("Battlebox: " + votesList.get(0));
        //Bukkit.broadcastMessage("Build Battle: " + votesList.get(1));
        //Bukkit.broadcastMessage("TNT Run: " + votesList.get(2));
        //Bukkit.broadcastMessage("Survival Games: " + votesList.get(3));
        //Bukkit.broadcastMessage("The Dropper: " + votesList.get(4));


        System.out.println("SWITCH FUNCTION");
        System.out.println(remainingGames);
        /*
        switch(maxLocation) {
            case 0:
                nextGame = NextGame.BATTLEBOX;
                remainingGames.remove("battlebox");
                System.out.println(remainingGames);
            return "battlebox";
            case 1:
                nextGame = NextGame.BUILDBATTLE;
                remainingGames.remove("buildbattle");
            return "buildbattle";
            case 2:
                nextGame = NextGame.TNTRUN;
                remainingGames.remove("tntrun");
            return "tntrun";
            case 3:
                nextGame = NextGame.SURVIVALGAMES;
                remainingGames.remove("survivalgames");
            return "survivalgames";
            case 4:
                nextGame = NextGame.DROPPER;
                remainingGames.remove("dropper");
            return "dropper";
        }

         */
        nextGame = remainingGames.get(maxLocation);
        remainingGames.remove(nextGame);
    }
}

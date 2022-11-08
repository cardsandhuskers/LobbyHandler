package io.github.cardsandhuskers.lobbyplugin.handlers;

import io.github.cardsandhuskers.lobbyplugin.objects.VotingMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;

public class VoteCountHandler {
    private String game;
    public String countVotes() {
        int battleboxCount = 0;
        int buildbattleCount = 0;
        int tntrunCount = 0;
        int survivalGamesCount = 0;


        for(VotingMenu m:votingMenuList) {
            String vote = m.getVote();
            if(vote!= null) {
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
                }
            }
        }


        ArrayList<Integer> votesList = new ArrayList<>();

        votesList.add(0, battleboxCount);
        votesList.add(1, buildbattleCount);
        votesList.add(2, tntrunCount);
        votesList.add(3, survivalGamesCount);
        int max = Collections.max(votesList);
        int maxLocation = votesList.indexOf(max);


        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "------------------------------\n" + ChatColor.RESET + "Voting Is Over, Results: ");
        Bukkit.broadcastMessage("Battlebox: " + votesList.get(0));
        Bukkit.broadcastMessage("Build Battle: " + votesList.get(1));
        Bukkit.broadcastMessage("TNT Run: " + votesList.get(2));
        Bukkit.broadcastMessage("Survival Games: " + votesList.get(3));



        switch(maxLocation) {
            case 0:
                nextGame = NextGame.BATTLEBOX;
                remainingGames.remove("battlebox");
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
        }
        return null;
    }
}

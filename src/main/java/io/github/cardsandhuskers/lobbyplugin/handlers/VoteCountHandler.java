package io.github.cardsandhuskers.lobbyplugin.handlers;

import io.github.cardsandhuskers.lobbyplugin.objects.VotingMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;

public class VoteCountHandler {
    private String game;

    /**
     *
     * @param setGame whether or not to update the game or just get counts
     */
    public ArrayList<Integer> countVotes(boolean setGame) {
        ArrayList<Integer> votingCount = new ArrayList<>();
        for(NextGame g:remainingGames) {
            votingCount.add(0);
        }


        for(VotingMenu m:votingMenuList) {
            NextGame vote = m.getVote();
            if(vote!= null) {
                int i = 0;
                for(NextGame s:remainingGames) {
                    if(vote.equals(s)) {
                        votingCount.set(i, votingCount.get(i) + 1);
                    }
                    i++;
                }
            }
        }
        if(setGame) {
            int max = Collections.max(votingCount);
            int maxLocation = votingCount.indexOf(max);


            Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "------------------------------\n" + ChatColor.RESET + "Voting Is Over, Results: ");
            int i = 0;
            for (NextGame s : remainingGames) {
                Bukkit.broadcastMessage(s + ": " + votingCount.get(i));
                i++;
            }

            nextGame = remainingGames.get(maxLocation);
            remainingGames.remove(nextGame);
        }
        return votingCount;
    }
}

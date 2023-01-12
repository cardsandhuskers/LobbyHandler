package io.github.cardsandhuskers.lobbyplugin.handlers;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.objects.LobbyInventory;
import io.github.cardsandhuskers.lobbyplugin.objects.VotingMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.votingMenuList;

public class VotingListHandler {


    /**
     * opens the voting menu for the specified player (called in event handler)
     * @param p
     */
    public void openVotingMenu(Player p) {
        boolean found = false;
        for(VotingMenu m:votingMenuList) {
            if(m.getPlayer() != null) {
                if (m.getPlayer().equals(p)) {
                    found = true;
                    m.openMenu();
                }
            }
        }

        if(!found) {
            VotingMenu votingInv = new VotingMenu(p.getUniqueId());
            votingMenuList.add(votingInv);
            votingInv.openMenu();
        }
        //System.out.println(votingMenuList.toString());
    }

    /**
     * Called when vote is set by the inventoryClick listener
     * @param p
     * @param vote
     */
    public void setVote(Player p, LobbyPlugin.NextGame vote) {
        boolean found = false;
        for(VotingMenu m:votingMenuList) {
            if(m.getPlayer() != null) {
                if (m.getPlayer().equals(p)) {
                    found = true;
                    m.setVote(vote);
                    LobbyInventory lobbyInv = new LobbyInventory();
                    lobbyInv.addVotingItems(p, true);
                }
            }
        }

        if(!found) {
            VotingMenu votingInv = new VotingMenu(p.getUniqueId());
            votingMenuList.add(votingInv);
            votingInv.setVote(vote);
            p.sendMessage(ChatColor.RED + "THIS IS AN ERROR THAT SHOULD NOT HAPPEN, IF YOU SEE THIS, TELL CARDSANDHUSKERS");
        }

        for(VotingMenu m:votingMenuList) {
            //System.out.println(m.getPlayer().getDisplayName() + " is open: " + m.isOpen());

            if(m.isOpen()) {
                m.updateMenu();
            }
        }
    }

}

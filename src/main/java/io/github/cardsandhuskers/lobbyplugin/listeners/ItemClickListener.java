package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.handlers.VoteCountHandler;
import io.github.cardsandhuskers.lobbyplugin.handlers.VotingListHandler;
import io.github.cardsandhuskers.lobbyplugin.objects.VotingMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;

public class ItemClickListener implements Listener {
    VotingListHandler votingListHandler;
    Location lobby;

    public ItemClickListener(LobbyPlugin plugin) {
        lobby = plugin.getConfig().getLocation("Lobby");
        votingListHandler = new VotingListHandler();
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        //if player is in lobby world
        if (p.getLocation().getWorld().equals(lobby.getWorld())) {
            if(e.getAction() == Action.PHYSICAL) {

            } else {
                e.setCancelled(true);
                if (e.getItem() != null) {
                    if (e.getMaterial() == Material.NETHER_STAR &&
                            e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Select Team")) {
                        //Team Menu
                        p.performCommand("teammenu");
                    }
                    if (e.getMaterial() == Material.DIAMOND &&
                            e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Vote Now!")) {
                        votingListHandler.openVotingMenu(p);
                    }
                    if (e.getItem().getItemMeta().getDisplayName().equals("Admin Rod")) {
                        p.performCommand("pauseEvent");
                    }
                }
            }
        }
    }
}

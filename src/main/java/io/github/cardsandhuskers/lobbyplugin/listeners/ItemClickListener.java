package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.handlers.VoteCountHandler;
import io.github.cardsandhuskers.lobbyplugin.handlers.VotingListHandler;
import io.github.cardsandhuskers.lobbyplugin.objects.VotingMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;

public class ItemClickListener implements Listener {
    private LobbyPlugin plugin;
    VotingListHandler votingListHandler;

    public ItemClickListener(LobbyPlugin plugin) {
        this.plugin = plugin;
        votingListHandler = new VotingListHandler();
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent e) {
        Player p = (Player) e.getPlayer();
        //if player is in lobby world
        if(e.getItem() != null) {
            if (p.getLocation().getWorld().equals(plugin.getConfig().getLocation("Lobby").getWorld())) {
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

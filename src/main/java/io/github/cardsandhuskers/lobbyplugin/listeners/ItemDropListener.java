package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener {
    private LobbyPlugin plugin;

    public ItemDropListener(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if(p.getWorld().equals(plugin.getConfig().getLocation("Lobby").getWorld())) {
            //System.out.println("CANCELLED ACTION");
            e.setCancelled(true);
        }
    }
}

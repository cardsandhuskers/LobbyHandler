package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerInteractEvent implements Listener {
    private LobbyPlugin plugin;

    public PlayerInteractEvent(LobbyPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(p.getWorld().equals(plugin.getConfig().getLocation("Lobby").getWorld())) {
            //System.out.println("CANCELLED ACTION");
            e.setCancelled(true);
        }
    }
}

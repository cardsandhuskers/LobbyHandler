package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerClickEntityEvent implements Listener {
    Location lobby;
    public PlayerClickEntityEvent(LobbyPlugin plugin) {
        lobby = plugin.getConfig().getLocation("Lobby");
    }

    @EventHandler
    public void onEntityClick(PlayerInteractAtEntityEvent e) {
        if (e.getPlayer().getLocation().getWorld().equals(lobby.getWorld())) {
            e.setCancelled(true);
        }
    }
}

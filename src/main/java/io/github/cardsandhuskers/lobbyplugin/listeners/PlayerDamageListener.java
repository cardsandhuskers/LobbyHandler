package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {
    LobbyPlugin plugin = (LobbyPlugin) Bukkit.getPluginManager().getPlugin("LobbyPlugin");
    Location lobby = plugin.getConfig().getLocation("Lobby");
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if(e.getEntity().getLocation().getWorld().equals(lobby.getWorld())) {
            e.setCancelled(true);
        }
    }
}

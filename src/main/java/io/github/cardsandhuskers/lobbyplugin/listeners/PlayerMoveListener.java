package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    LobbyPlugin plugin = (LobbyPlugin) Bukkit.getPluginManager().getPlugin("LobbyPlugin");
    Location lobby = plugin.getConfig().getLocation("Lobby");

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if(e.getPlayer().getLocation().getWorld().equals(lobby.getWorld())) {
            if(e.getPlayer().getLocation().getY() <= -10) {
                e.getPlayer().teleport(lobby);
            }
        }
    }
}

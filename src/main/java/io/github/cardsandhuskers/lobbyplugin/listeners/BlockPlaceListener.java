package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    private LobbyPlugin plugin;
    public BlockPlaceListener(LobbyPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(e.getBlock().getWorld().equals(plugin.getConfig().getLocation("Lobby").getWorld())) {
            e.setCancelled(true);
        }
    }
}

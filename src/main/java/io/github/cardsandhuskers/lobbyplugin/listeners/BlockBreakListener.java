package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    private LobbyPlugin plugin;
    public BlockBreakListener(LobbyPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getBlock().getWorld().equals(plugin.getConfig().getLocation("Lobby").getWorld())) {
            e.setCancelled(true);
        }
    }
}

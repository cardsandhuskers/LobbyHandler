package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.objects.LobbyInventory;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.nextGame;
import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.voting;


public class PlayerJoinListener implements Listener {
    LobbyInventory lobbyInventory;
    LobbyPlugin plugin;

    public PlayerJoinListener(LobbyPlugin plugin) {
        lobbyInventory = new LobbyInventory();
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().setInvisible(false);
        if(nextGame == LobbyPlugin.NextGame.IN_GAME) return;
        Player p = e.getPlayer();
        if(p.getWorld().equals(plugin.getConfig().getLocation("Lobby").getWorld())) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()-> p.setGameMode(GameMode.ADVENTURE),5);

        } else {
            p.teleport(plugin.getConfig().getLocation("Lobby"));
        }
        lobbyInventory.addTeamSelector(p);
        if(voting) {
            lobbyInventory.addVotingItems(p, false);
        }
    }
}

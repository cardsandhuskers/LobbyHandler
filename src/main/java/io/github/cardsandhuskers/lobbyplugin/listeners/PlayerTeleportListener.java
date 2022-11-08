package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.handlers.LobbyStageHandler;
import io.github.cardsandhuskers.lobbyplugin.objects.LobbyInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;

public class PlayerTeleportListener implements Listener {
    LobbyPlugin plugin;
    LobbyStageHandler stageHandler;

    public PlayerTeleportListener(LobbyPlugin plugin, LobbyStageHandler stageHandler) {
        this.plugin = plugin;
        this.stageHandler = stageHandler;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if(p.getWorld().equals(plugin.getConfig().getLocation("Lobby").getWorld())) {
                LobbyInventory inventory = new LobbyInventory();
                inventory.addTeamSelector(p);

                if(!lobbyStage) {
                    lobbyStage = true;
                    stageHandler.init();
                    gameNumber++;
                    if((gameNumber + 1) %2 == 0) {
                        multiplier += .5;
                    }
                    votingMenuList.clear();
                }

            }
        }, 10L);



    }
}

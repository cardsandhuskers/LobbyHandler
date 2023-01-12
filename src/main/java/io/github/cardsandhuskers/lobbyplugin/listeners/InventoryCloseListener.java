package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.objects.VotingMenu;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "voting menu")) {
            for(VotingMenu m: LobbyPlugin.votingMenuList) {
                if(m.getPlayer() != null && m.getPlayer().equals(e.getPlayer())) {
                    m.close();
                }
            }
        }
    }
}

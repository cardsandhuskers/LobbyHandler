package io.github.cardsandhuskers.lobbyplugin.objects;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.remainingGames;

public class VotingMenu {
    private UUID player;
    private LobbyPlugin.NextGame vote;
    public VotingMenu(UUID player) {
        this.player = player;
    }

    public void openMenu() {
        Player p = Bukkit.getPlayer(player);
        Inventory inventory = Bukkit.createInventory(p, 18, ChatColor.AQUA + "Voting Menu");

        int index = 0;
        for(LobbyPlugin.NextGame g:remainingGames) {

            ItemStack stack = new ItemStack(getMaterial(g), 1);
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(getName(g));
            stackMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            if(vote!= null && g.equals(vote)) {
                stackMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            }
            stack.setItemMeta(stackMeta);
            inventory.setItem(index, stack);

            index++;
        }

        p.openInventory(inventory);
    }
    public Material getMaterial(LobbyPlugin.NextGame g) {
        switch(g) {
            case BATTLEBOX:
                return Material.PURPLE_CONCRETE;
            case BUILDBATTLE:
                return Material.SPRUCE_PLANKS;
            case TNTRUN:
                return Material.TNT;
            case SURVIVALGAMES:
                return Material.WOODEN_SWORD;
            case DROPPER:
                return Material.WATER_BUCKET;
            default:
                return Material.AIR;
        }
    }
    public String getName(LobbyPlugin.NextGame g) {
        switch(g) {
            case BATTLEBOX:
                return "Battlebox";
            case BUILDBATTLE:
                return "Build Battle";
            case TNTRUN:
                return "TNT Run";
            case SURVIVALGAMES:
                return "Survival Games";
            case DROPPER:
                return "Dropper";
            default:
                return "NULL";
        }
    }

    public Player getPlayer() {
        Player p = Bukkit.getPlayer(player);
        return p;
    }

    public void setVote(LobbyPlugin.NextGame vote) {
        this.vote = vote;
        openMenu();
    }

    public LobbyPlugin.NextGame getVote() {
        return vote;
    }
}

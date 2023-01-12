package io.github.cardsandhuskers.lobbyplugin.objects;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.handlers.VoteCountHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.remainingGames;

public class VotingMenu {
    private UUID player;
    private boolean isOpen = false;
    private LobbyPlugin.NextGame vote;
    public VotingMenu(UUID player) {
        this.player = player;
    }
    Inventory inventory;

    public void openMenu() {
        Player p = Bukkit.getPlayer(player);
        inventory = Bukkit.createInventory(p, 9, ChatColor.AQUA + "Voting Menu");
        p.openInventory(inventory);
        updateMenu();
        open();
    }
    public void updateMenu() {
        int index = 0;
        VoteCountHandler voteCountHandler = new VoteCountHandler();
        ArrayList<Integer> votes = voteCountHandler.countVotes(false);
        for (LobbyPlugin.NextGame g : remainingGames) {
            int num = votes.get(index);
            if (num == 0) num = 1;


            ItemStack stack = new ItemStack(getMaterial(g), num);
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(getName(g));
            stackMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            if (vote != null && g.equals(vote)) {
                stackMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            }
            stack.setItemMeta(stackMeta);
            inventory.setItem(index, stack);

            index++;
        }
    }

    public Material getMaterial(LobbyPlugin.NextGame g) {
        switch(g) {
            case BATTLEBOX:
                return Material.PURPLE_CONCRETE;
            case BINGO:
                return Material.MAP;
            case BUILDBATTLE:
                return Material.SPRUCE_PLANKS;
            case DROPPER:
                return Material.WATER_BUCKET;
            case SKYWARS:
                return Material.ELYTRA;
            case SURVIVALGAMES:
                return Material.WOODEN_SWORD;
            case TGTTOS:
                return Material.LEATHER_BOOTS;
            case TNTRUN:
                return Material.TNT;
            default:
                return Material.AIR;
        }
    }
    public String getName(LobbyPlugin.NextGame g) {
        switch(g) {
            case BATTLEBOX:
                return "Battlebox";
            case BINGO:
                return "Bingo";
            case BUILDBATTLE:
                return "Build Battle";
            case DROPPER:
                return "Dropper";
            case SKYWARS:
                return "Sky Wars";
            case SURVIVALGAMES:
                return "Survival Games";
            case TGTTOS:
                return "TGTTOS";
            case TNTRUN:
                return "TNT Run";
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
        updateMenu();
    }

    public LobbyPlugin.NextGame getVote() {
        return vote;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public void close() {
        //System.out.println("CLOSING " + getPlayer().getName());
        isOpen = false;
    }
    public void open() {
        //System.out.println("OPENING " + getPlayer().getName());
        isOpen = true;
    }
}

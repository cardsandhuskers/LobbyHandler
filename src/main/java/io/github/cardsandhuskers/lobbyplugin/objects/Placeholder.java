package io.github.cardsandhuskers.lobbyplugin.objects;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;
import static io.github.cardsandhuskers.teams.Teams.handler;

public class Placeholder extends PlaceholderExpansion {
    private final LobbyPlugin plugin;
    StatCalculator statCalculator;

    public Placeholder(LobbyPlugin plugin, StatCalculator statCalculator) {
        this.plugin = plugin;
        this.statCalculator = statCalculator;
    }
    @Override
    public String getIdentifier() {
        return "Lobby";
    }
    @Override
    public String getAuthor() {
        return "cardsandhuskers";
    }
    @Override
    public String getVersion() {
        return "1.0.0";
    }
    @Override
    public boolean persist() {
        return true;
    }



    @Override
    public String onRequest(OfflinePlayer p, String s) {
        if(s.equals("multiplier")) {
            return "" + multiplier;
        }
        if(s.equals("gameNumber")) {
            return "" + gameNumber;
        }
        if(s.equals("nextGame")) {
            switch(nextGame) {
                case BATTLEBOX: return "Battlebox";
                case BINGO: return "Bingo";
                case BUILDBATTLE: return "Build Battle";
                case DROPPER: return "The Dropper";
                case SKYWARS: return "Sky Wars";
                case SURVIVALGAMES: return "Survival Games";
                case TGTTOS: return "TGTTOS";
                case TNTRUN: return "TNT Run";
                case VOTING: return "Voting...";
                case LASERDOME: return "Laserdome";
                case IN_GAME: return "In Game";
            }
        }
        if(s.equalsIgnoreCase("timeLeft")) {
            int mins = timeVar / 60;
            String seconds = String.format("%02d", timeVar - (mins * 60));
            return mins + ":" + seconds;
        }
        if(s.equalsIgnoreCase("timerStage")) {
            return timerStage;
        }

        //Lobby_playerPoints_[game]_[pos]
        String[] values = s.split("_");

        if(values[0].equalsIgnoreCase("playerPoints")) {
            for (NextGame g : NextGame.values()) {
                if (values[1].equalsIgnoreCase(String.valueOf(g))) {
                    ArrayList<StatCalculator.PlayerHolder> playerHolders = statCalculator.getPlayerHolders(g);
                    try {
                        int x = Integer.parseInt(values[2]);
                        StatCalculator.PlayerHolder holder = playerHolders.get(x-1);

                        Player player = Bukkit.getPlayer(holder.name);
                        if(player!= null && handler.getPlayerTeam(player) != null) return handler.getPlayerTeam(player).color + holder.name + ChatColor.RESET + " Event " + holder.event + ": ✪" + (int)(holder.getPoints(g)/holder.getMultiplier(g));
                        return holder.name + " Event " + holder.event + ": ✪" + (int)(holder.getPoints(g)/holder.getMultiplier(g));
                    } catch (Exception e){};
                }
            }
        }

        if(values[0].equalsIgnoreCase("teamPoints")) {
            for (NextGame g : NextGame.values()) {
                if (values[1].equalsIgnoreCase(String.valueOf(g))) {
                    ArrayList<StatCalculator.StatHolder> statHolders = statCalculator.getStatHolders(g);
                    try {
                        int x = Integer.parseInt(values[2]);
                        if(x >= statHolders.size()) return "";
                        StatCalculator.StatHolder holder = statHolders.get(x-1);

                        Player player = Bukkit.getPlayer(holder.name);
                        if(player!= null && handler.getPlayerTeam(player) != null) return handler.getPlayerTeam(player).color + holder.name + ChatColor.RESET + " Event " + holder.event + ": ✪" + (int)(holder.getPoints(g)/holder.getMultiplier(g));
                        return holder.name + " Event " + holder.event + ": ✪" + (int)(holder.getPoints(g)/holder.getMultiplier(g));
                    } catch (Exception e){};
                }
            }
        }

        if(values[0].equalsIgnoreCase("eventWinner")) {
            try {
                int x = Integer.parseInt(values[1]);
                StatCalculator.StatHolder holder = statCalculator.getEventWinner(x);
                return holder.name + " ✪" + holder.getPoints(NextGame.TOTAL);



            } catch (Exception e){};
        }



        return null;
    }

}

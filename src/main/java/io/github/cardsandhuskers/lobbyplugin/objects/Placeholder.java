package io.github.cardsandhuskers.lobbyplugin.objects;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.*;

public class Placeholder extends PlaceholderExpansion {
    private final LobbyPlugin plugin;

    public Placeholder(LobbyPlugin plugin) {
        this.plugin = plugin;
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
                case TNTRUN: return "TNT Run";
                case BUILDBATTLE: return "Build Battle";
                case SURVIVALGAMES: return "Survival Games";
                case DROPPER: return "The Dropper";
                case VOTING: return "Voting...";
            }
        }
        if(s.equals("timeLeft")) {
            int mins = timeVar / 60;
            String seconds = String.format("%02d", timeVar - (mins * 60));
            return mins + ":" + seconds;
        }
        if(s.equals("timerStage")) {
            return timerStage;
        }




        return null;
    }

}

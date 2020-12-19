package fr.overcraftor.mmo.scoreboard;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

    private final Map<Player, MMOScoreboard> scoreboards;

    public ScoreboardManager(){
        this.scoreboards = new HashMap<>();
    }

    public void addPlayer(Player p){
        scoreboards.put(p, new MMOScoreboard(p));
    }

    public void removePlayer(Player p){
        scoreboards.remove(p);
    }
}

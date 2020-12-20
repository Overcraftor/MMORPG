package fr.overcraftor.mmo.scoreboard;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MMOScoreboardManager {

    private final Map<Player, MMOScoreboard> scoreboards;

    public MMOScoreboardManager(){
        this.scoreboards = new HashMap<>();
    }

    public void addPlayer(Player p){
        scoreboards.put(p, new MMOScoreboard(p));
    }

    public void removePlayer(Player p){
        scoreboards.remove(p);
    }

    public MMOScoreboard getScoreboard(Player p){
        return scoreboards.get(p);
    }
}

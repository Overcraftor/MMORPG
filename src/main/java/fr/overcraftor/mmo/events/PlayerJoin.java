package fr.overcraftor.mmo.events;

import fr.overcraftor.mmo.utils.jobs.JobsNames;
import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.JobsSQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();
        JobsSQL.checkTableContainUUID(p.getUniqueId());
        final HashMap<JobsNames, Integer> map = JobsSQL.getXp(p.getUniqueId());

        Main.jobsXp.put(p, map);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        final Player p = e.getPlayer();

        JobsSQL.setAllXp(Main.jobsXp.get(p), p.getUniqueId());
        Main.getInstance().getLogger().info("l'xp du joueur " + p.getName() + " a bien ete sauvegarde sur MYSQL");
        Main.jobsXp.remove(p);
    }
}

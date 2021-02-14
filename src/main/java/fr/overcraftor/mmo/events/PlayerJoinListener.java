package fr.overcraftor.mmo.events;

import fr.overcraftor.mmo.mysql.AptSQL;
import fr.overcraftor.mmo.mysql.GeneralXpSQL;
import fr.overcraftor.mmo.mysql.ManaSQL;
import fr.overcraftor.mmo.utils.jobs.JobsNames;
import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.JobsSQL;
import fr.overcraftor.mmo.utils.mana.PlayerMana;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();
        Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + p.getName() + " 10100 82 10157");

        //JOBS XP
        JobsSQL.checkTableContainUUID(p.getUniqueId());
        final HashMap<JobsNames, Integer> map = JobsSQL.getXp(p.getUniqueId());
        Main.jobsXp.put(p, map);

        //GENERAL XP
        if(GeneralXpSQL.isInTable(p.getUniqueId())){
            final int xp =  GeneralXpSQL.getXp(p.getUniqueId());
            Main.getInstance().generalXp.put(p, xp);
        }else{
            GeneralXpSQL.insert(p.getUniqueId(), 0);
            Main.getInstance().generalXp.put(p, 0);
        }

        //APTITUDE
        if(!AptSQL.isInTable(p.getUniqueId()))
            AptSQL.insert(p.getUniqueId(), 0);

        // Mana
        if(!ManaSQL.isInTable(p.getUniqueId()))
            ManaSQL.insert(p.getUniqueId());

        PlayerMana.create(p);

        //SCOREBOARD
        Main.getInstance().getScoreboardManager().addPlayer(p);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        final Player p = e.getPlayer();

        //JOBS
        JobsSQL.setAllXp(Main.jobsXp.get(p), p.getUniqueId());
        Main.jobsXp.remove(p);

        //GENERAL XP
        GeneralXpSQL.setXp(p.getUniqueId(), Main.getInstance().generalXp.get(p));
        Main.getInstance().generalXp.remove(p);

        //SCOREBOARD
        Main.getInstance().getScoreboardManager().removePlayer(p);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        PlayerMana.getFromPlayer(e.getPlayer()).respawn();
    }
}

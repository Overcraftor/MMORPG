package fr.overcraftor.mmo.timers;

import fr.overcraftor.mmo.utils.jobs.JobsNames;
import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.JobsSQL;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class JobXpTimer extends BukkitRunnable {

    @Override
    public void run() {
        for(Map.Entry<Player, HashMap<JobsNames, Integer>> entry : Main.jobsXp.entrySet()){
            JobsSQL.setAllXp(entry.getValue(), entry.getKey().getUniqueId());
        }
        Main.getInstance().getLogger().info("Sauvegarde de l'exp des jobs !");
    }
}

package fr.overcraftor.mmo.commands.jobscommands;

import fr.overcraftor.mmo.utils.jobs.JobsNames;
import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.utils.Permissions;
import fr.overcraftor.mmo.utils.jobs.JobsXpUtils;
import fr.overcraftor.mmo.utils.jobs.OnLevelUp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddXpJobCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //perm
        if(!Permissions.JOB_ADD_XP.hasPerm(sender)){
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission !");
            return true;
        }

        //args
        if(args.length != 3){
            sender.sendMessage(ChatColor.RED + "La commande est /addxpjobs <joueur> <xp> <job>");
            return true;
        }

        //variables
        final Player target = Bukkit.getPlayer(args[0]);
        int xp;
        final JobsNames job = JobsNames.getFromName(args[2]);

        //TARGET
        if(target == null){
            sender.sendMessage(ChatColor.RED + "Le joueur cible n'est pas connecté !");
            return true;
        }
        //XP
        try {
            xp = Integer.parseInt(args[1]);

            if(xp <= 0){
                sender.sendMessage(ChatColor.RED + "Le nombre doit etre superieur a 0");
                return true;
            }
        }catch (NumberFormatException ignored){
            sender.sendMessage(ChatColor.RED + "Le nombre d'exp indiqué est invalide !");
            return true;
        }
        //job
        if(job == null){
            sender.sendMessage(ChatColor.RED + "Le metier indiqué est invalide !");
            return true;
        }

        if(Main.jobsXp.get(target).get(job) + xp == 2995000){
            return true;
        }

        //COMMAND
        final HashMap<JobsNames, Integer> map = Main.jobsXp.get(target);

        final JobsXpUtils jobsXpUtils = new JobsXpUtils(map.get(job));
        new OnLevelUp(target, job, jobsXpUtils.checkLevelUp(xp));

        Main.jobsXp.get(target).put(job, map.get(job) + xp);
        sender.sendMessage(ChatColor.GREEN + "Vous avez ajouté " + xp + " au metier " + job.toName() + " du joueur " + target.getName() + " !");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> list = new ArrayList<>();

        if(args.length == 1){
            for(Player p : Bukkit.getOnlinePlayers()){
                list.add(p.getName());
            }
        }else if(args.length == 3){
            for(JobsNames job : JobsNames.values()){
                list.add(job.toName());
            }
        }

        return list;
    }
}

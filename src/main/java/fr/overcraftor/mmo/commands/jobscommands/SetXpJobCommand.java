package fr.overcraftor.mmo.commands.jobscommands;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.utils.jobs.JobsNames;
import fr.overcraftor.mmo.utils.Permissions;
import fr.overcraftor.mmo.mysql.JobsSQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetXpJobCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //perm
        if(!Permissions.JOB_SET_XP.hasPerm(sender)){
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission !");
            return true;
        }

        //args
        if(args.length != 3){
            sender.sendMessage(ChatColor.RED + "La commande est /setxpjobs <joueur> <xp> <job>");
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

            if(xp < 0 || xp > 2995000){
                sender.sendMessage(ChatColor.RED + "Veuillez indiquer un nombre superieur ou egale a 0 et inferieur à 2 995 000");
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

        //COMMAND
        JobsSQL.setXp(job, target.getUniqueId(), xp);
        Main.jobsXp.get(target).put(job, xp);
        sender.sendMessage(ChatColor.GREEN + "Vous avez défini le nombre d'exp du joueur " + target.getName() + " au metier " + job.toName() + " à " + xp);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
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
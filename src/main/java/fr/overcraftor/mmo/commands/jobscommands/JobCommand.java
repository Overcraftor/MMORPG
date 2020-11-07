package fr.overcraftor.mmo.commands.jobscommands;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.inventories.JobInventory;
import fr.overcraftor.mmo.utils.Permissions;
import fr.overcraftor.mmo.utils.jobs.JobsNames;
import fr.overcraftor.mmo.utils.jobs.JobsLevel;
import fr.overcraftor.mmo.utils.jobs.JobsXpUtils;
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

public class JobCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0 && sender instanceof Player){

            JobInventory.openInv((Player) sender);

        }else{
            if(!Permissions.JOB_VIEW_OTHER.hasPerm(sender)){
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission !");
                return true;
            }
            final Player target = Bukkit.getPlayer(args[0]);

            if(target == null){
                sender.sendMessage(ChatColor.RED + "Le joueur cible n'est pas co !");
                return true;
            }

            final HashMap<JobsNames, Integer> map = Main.jobsXp.get(target);

            final JobsXpUtils wood_cutter = new JobsXpUtils(map.get(JobsNames.WOOD_CUTTER));
            final JobsXpUtils blacksmith = new JobsXpUtils(map.get(JobsNames.BLACKSMITH));
            final JobsXpUtils miner = new JobsXpUtils(map.get(JobsNames.MINER));
            final JobsXpUtils enchanter = new JobsXpUtils(map.get(JobsNames.ENCHANTER));
            final JobsXpUtils farmer = new JobsXpUtils(map.get(JobsNames.FARMER));

            sender.sendMessage("§eMétiers de §c" + target.getName() + "\n \n");
            sender.sendMessage("§6" + JobsNames.WOOD_CUTTER.toName() + "\n§eNiveau: §6" + wood_cutter.getLevels() + "\n§eXP: §6" + wood_cutter.getXpRemain() + "/" + JobsLevel.getFromLevel(wood_cutter.getLevels()).getObjective() + "\n \n");
            sender.sendMessage("§6" + JobsNames.BLACKSMITH.toName() + "\n§eNiveau: §6" + blacksmith.getLevels() + "\n§eXP: §6" + blacksmith.getXpRemain() + "/" + JobsLevel.getFromLevel(blacksmith.getLevels()).getObjective() + "\n \n");
            sender.sendMessage("§6" + JobsNames.MINER.toName() + "\n§eNiveau: §6" + miner.getLevels() + "\n§eXP: §6" + miner.getXpRemain() + "/" + JobsLevel.getFromLevel(miner.getLevels()).getObjective() + "\n \n");
            sender.sendMessage("§6" + JobsNames.ENCHANTER.toName() + "\n§eNiveau: §6" + enchanter.getLevels() + "\n§eXP: §6" + enchanter.getXpRemain() + "/" + JobsLevel.getFromLevel(enchanter.getLevels()).getObjective() + "\n \n");
            sender.sendMessage("§6" + JobsNames.FARMER.toName() + "\n§eNiveau: §6" + farmer.getLevels() + "\n§eXP: §6" + farmer.getXpRemain() + "/" + JobsLevel.getFromLevel(farmer.getLevels()).getObjective() + "\n \n");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        final List<String> list = new ArrayList<>();

        if(args.length == 1 && Permissions.JOB_VIEW_OTHER.hasPerm(sender)){
            for(Player p : Bukkit.getOnlinePlayers()){
                list.add(p.getName());
            }
        }
        return list;
    }
}

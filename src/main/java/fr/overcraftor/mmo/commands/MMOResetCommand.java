package fr.overcraftor.mmo.commands;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.*;
import fr.overcraftor.mmo.utils.Permissions;
import fr.overcraftor.mmo.utils.aptitude.PlayerAptitude;
import fr.overcraftor.mmo.utils.jobs.JobsNames;
import fr.overcraftor.mmo.utils.mana.PlayerMana;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MMOResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand( CommandSender sender,  Command cmd, @NotNull String label, String[] args) {

        if(!Permissions.ADMIN.hasPermMessage(sender))
            return true;

        if(args.length < 2){
            sender.sendMessage("§c/mmo reset §7[§ejoueur§7]");
            return true;
        }

        final OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

        if(!GeneralXpSQL.isInTable(target.getUniqueId())){
            sender.sendMessage("§cCe joueur n'est pas présent dans la base de donnée.");
            return true;
        }

        if(target.getName().equals("Azrotho") || target.getName().equals("Overcraftor")){
            sender.sendMessage("§c§lOn peut pas reset dieu.");
            return true;
        }

        final UUID uuid = target.getUniqueId();
        //RESET PLAYER
        if(target.isOnline())
            PlayerAptitude.getFromPlayer(target.getPlayer()).setAllApt(0, 0, 0, 0, 0);
        else
            AptSQL.setAllApt(uuid, 0, 0, 0, 0, 0);

        if(target.isOnline())
            Main.getInstance().generalXp.put(target.getPlayer(), 0);
        GeneralXpSQL.setXp(uuid, 0);

        GuildSQL.delete(uuid);
        GuildSQL.removePlayer(uuid);

        if(target.isOnline()){
            for(JobsNames job : JobsNames.values())
                Main.jobsXp.get(target.getPlayer()).put(job, 0);
        }
        JobsSQL.setAllXp(Main.jobsXp.get(target.getPlayer()), target.getUniqueId());

        if(target.isOnline())
            PlayerMana.getFromPlayer(target.getPlayer()).setMaxManaBonus(0);
        ManaSQL.setManaMaxBonus(target.getUniqueId(), 0);

        if(target.isOnline())
            Main.getInstance().getScoreboardManager().getScoreboard(target.getPlayer())
                    .refreshGeneralLevel().setGuild("Aucune guilde");

        sender.sendMessage("§aVous avez correctement reset §6" + target.getName());
        if(target.isOnline())
            target.getPlayer().sendMessage("§cUn modérateur a reset vos données mysql gérées par le plugin §7[§e" + Main.getInstance().getName() + " " + Main.getInstance().getDescription().getVersion() + "§7]");

        return true;
    }
}

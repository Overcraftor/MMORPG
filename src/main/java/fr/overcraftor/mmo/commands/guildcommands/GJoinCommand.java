package fr.overcraftor.mmo.commands.guildcommands;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.GuildSQL;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GJoinCommand {

    public static void onCommand(Player p, Command cmd, String label, String[] args){

        if(args.length != 2){
            p.sendMessage("§c/guild join §e[§6NOM§e]");
            return;
        }

        final String guildName = args[1];

        if(GuildSQL.hasGuild(p.getUniqueId())){
            p.sendMessage("§cVous êtes déjà dans une guild");
            return;
        }

        if(!GuildSQL.exists(guildName)){
            p.sendMessage("§cCette guild n'existe pas !");
            return;
        }

        GuildSQL.addPlayer(guildName, p.getUniqueId());
        p.sendMessage("§aVous avez bien rejoint la guild: §6" + guildName);
        Main.getInstance().getScoreboardManager().getScoreboard(p).setGuild(guildName);
    }
}

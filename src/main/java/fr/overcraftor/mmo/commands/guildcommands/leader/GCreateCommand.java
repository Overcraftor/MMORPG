package fr.overcraftor.mmo.commands.guildcommands.leader;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.GuildSQL;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GCreateCommand {

    public static void onCommand(Player p, Command cmd, String label, String[] args){
        if(args.length != 3){
            p.sendMessage("§c/guild create §e[§6guidName§e] [§6guildTag§e]");
            return;
        }

        if(args[1].length() > 10){
            p.sendMessage("§cLe nom de votre guild ne doit pas dépasser 10 caractères");
            return;
        }

        if(args[2].length() > 3){
            p.sendMessage("§cLe tag de votre guild ne doit pas dépasser 3 caractères");
            return;
        }

        if(GuildSQL.hasGuild(p.getUniqueId())){
            p.sendMessage("§cVous êtes déjà dans une guild !");
            return;
        }

        final String guildName = args[1], guildTag = args[2];

        if(GuildSQL.exists(guildName)){
            p.sendMessage("§cCette guild existe déjà !");
            return;
        }

        if(GuildSQL.tagExists(guildTag)){
            p.sendMessage("§cCe tag existe déjà !");
            return;
        }

        GuildSQL.addGuild(guildName, guildTag, p.getUniqueId());
        p.sendMessage("§aVous avez créé la guild §6" + guildName + " §aavec comme tag §e[§6" + guildTag + "§e]");
        Main.getInstance().getScoreboardManager().getScoreboard(p).setGuild(guildName);
    }
}

package fr.overcraftor.mmo.commands.guildcommands;

import fr.overcraftor.mmo.mysql.GuildSQL;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GSetTagCommand {

    public static void onCommand(Player p, Command cmd, String label, String[] args){

        if(args.length < 2){
            p.sendMessage("§c/g settag [TAG]");
            return;
        }

        if(!GuildSQL.isOwner(p.getUniqueId())){
            p.sendMessage("§cVous n'êtes le chef d'aucune guild !");
            return;
        }

        final String guildName = GuildSQL.getGuildName(p.getUniqueId()), tag = args[1];

        if(tag.length() > 3){
            p.sendMessage("§cLe tag ne doit pas dépasser 3 caractères");
            return;
        }

        GuildSQL.setTag(tag, guildName);
        p.sendMessage("§aLe nouveau tag de votre guild est: " + tag);
    }
}

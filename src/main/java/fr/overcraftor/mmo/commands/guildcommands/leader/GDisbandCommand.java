package fr.overcraftor.mmo.commands.guildcommands.leader;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.GuildSQL;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GDisbandCommand {

    public static void onCommand(Player p, Command cmd, String label, String[] args){

        if(GuildSQL.isOwner(p.getUniqueId())){
            if(args.length != 2 || !args[1].equals("CONFIRM")){
                p.sendMessage("§cVeuillez écrire §4/guild disband CONFIRM");
            }else{
                GuildSQL.delete(p.getUniqueId());
                p.sendMessage("§aVotre guild a bien été supprimée");
                Main.getInstance().getScoreboardManager().getScoreboard(p).setGuild("Aucune guilde");
            }
        }else{
            p.sendMessage("§cTu n'es le chef d'aucune guild !");
        }
    }
}

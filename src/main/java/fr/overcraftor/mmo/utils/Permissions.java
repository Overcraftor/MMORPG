package fr.overcraftor.mmo.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Permissions {


    ADMIN("mmo.admin"),

    BREAK_BLOCK("monstermmo.break."),
    PREMIUM("monstermmo.premium"),

    JOB_SET_XP("monstermmo.jobs.addxp"),
    JOB_ADD_XP("monstermmo.jobs.setxp"),
    JOB_VIEW_OTHER("monstermmo.jobs.viewother"),

    ADD_XP("monstermmo.addxp"),
    SET_XP("monstermmo.setxp"),

    ADD_MAX_MANA("monstermmo.addmaxmana"),
    SET_MAX_MANA("monstermmo.setmaxmana"),
    ADD_MANA("monstermmo.addmana"),
    SET_MANA("monstermmo.setmana");

    private final String permValue;

    Permissions(String permValue){
        this.permValue = permValue;
    }

    public String get() {
        return permValue;
    }

    public boolean hasPerm(Player p){
        return p.hasPermission(permValue);
    }

    public boolean hasPermMessage(Player p){
        boolean perm = hasPerm(p);
        if(!perm)
            p.sendMessage("§cVous n'avez pas la permission d'execute cette commande");

        return perm;
    }

    public boolean hasPerm(CommandSender sender){
        return sender.hasPermission(permValue);
    }

    public boolean hasPermMessage(CommandSender sender){
        boolean perm = hasPerm(sender);
        if(!perm)
            sender.sendMessage("§cVous n'avez pas la permission d'execute cette commande");

        return perm;
    }
}

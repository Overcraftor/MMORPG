package fr.overcraftor.mmo.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Permissions {

    BREAK_BLOCK("monstermmo.break."),
    PREMIUM("monstermmo.premium"),
    JOB_SET_XP("monstermmo.jobs.addxp"),
    JOB_ADD_XP("monstermmo.jobs.setxp"),
    JOB_VIEW_OTHER("monstermmo.jobs.viewother"),
    ADD_XP("monstermmo.addxp"),
    SET_XP("monstermmo.setxp");

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
    public boolean hasPerm(CommandSender sender){
        return sender.hasPermission(permValue);
    }
}

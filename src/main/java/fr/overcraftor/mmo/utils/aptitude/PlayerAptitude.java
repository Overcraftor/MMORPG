package fr.overcraftor.mmo.utils.aptitude;

import fr.overcraftor.mmo.mysql.AptSQL;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerAptitude {

    private static final ArrayList<PlayerAptitude> players = new ArrayList<>();

    private final Player p;
    private int apt;
    private int strength;
    private int health;
    private int mana;
    private int speed;

    public PlayerAptitude(Player p) {
        this.p = p;
        final int[] allApt = AptSQL.getAllApt(p.getUniqueId());
        this.apt = allApt[0];
        this.strength = allApt[1];
        this.health = allApt[2];
        this.mana = allApt[3];
        this.speed = allApt[4];
        players.add(this);
    }

    public boolean isSamePlayer(Player p){
        return this.p == p;
    }

    public static PlayerAptitude getFromPlayer(Player p){
        return PlayerAptitude.players.stream().filter(a -> a.isSamePlayer(p)).findFirst().get();
    }

    public int getApt() {
        return apt;
    }

    public int getStrength() {
        return strength;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getSpeed() {
        return speed;
    }

    public void setApt(int apt) {
        this.apt = apt;
        setSQL();
    }

    public void setStrength(int strength) {
        this.strength = strength;
        setSQL();
    }

    public void setHealth(int health) {
        this.health = health;
        setSQL();
    }

    public void setMana(int mana) {
        this.mana = mana;
        setSQL();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        setSQL();
    }

    public void setAllApt(int... allApt){
        this.apt = allApt[0];
        this.strength = allApt[1];
        this.health = allApt[2];
        this.mana = allApt[3];
        this.speed = allApt[4];
        setSQL();
    }

    private void setSQL(){
        AptSQL.setAllApt(p.getUniqueId(), apt, strength, health, mana, speed);
    }
}

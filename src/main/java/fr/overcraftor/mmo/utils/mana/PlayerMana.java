package fr.overcraftor.mmo.utils.mana;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.ManaSQL;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerMana extends BukkitRunnable {

    private int mana;
    private int maxManaBonus;
    private final int manaRegen;
    private final Player p;

    private PlayerMana(Player p) {
        this.maxManaBonus = ManaSQL.getMaxManaBonus(p.getUniqueId());

        this.mana = 20 + this.maxManaBonus;
        this.p = p;
        this.manaRegen = 3;
        updateBarXp();
        Main.getInstance().playerMana.put(p, this);
        super.runTaskTimer(Main.getInstance(), 20 * 60, 20 * 60);
    }

    @Override
    public void run() {
        addMana(this.manaRegen);
    }

    public void addMaxManaBonus(int maxMana){
        setMaxManaBonus(this.maxManaBonus + maxMana);
    }

    public void setMaxManaBonus(int maxManaBonus){
        this.maxManaBonus = maxManaBonus;
        if(this.mana > 20 + this.maxManaBonus)
            this.mana = 20 + this.maxManaBonus;

        updateBarXp();
    }

    public void setMana(int mana){
        this.mana = mana;
        if(this.mana > 20 + this.maxManaBonus)
            this.mana = 20 + this.maxManaBonus;
        else if(this.mana < 0){
            this.mana = 0;
        }
        updateBarXp();
    }

    public boolean removeMana(int mana){
        if(this.mana - mana < 0){
            return false;
        }
        setMana(this.mana - mana);
        return true;
    }

    public void addMana(int mana){
        setMana(this.mana + mana);
    }

    public void respawn(){
        setMana((20 + this.maxManaBonus) / 2);
    }

    private void updateBarXp(){
        p.setLevel(this.mana);
        p.setExp((float) this.mana / (float) (20 + this.maxManaBonus));
    }

    public static PlayerMana getFromPlayer(Player p){
        return Main.getInstance().playerMana.get(p);
    }

    public static void create(Player p){
        new PlayerMana(p);
    }
}

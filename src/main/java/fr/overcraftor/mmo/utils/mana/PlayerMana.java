package fr.overcraftor.mmo.utils.mana;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.ManaSQL;
import fr.overcraftor.mmo.utils.aptitude.PlayerAptitude;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerMana extends BukkitRunnable {

    private int mana;
    private int maxManaBonus;
    private double manaRegen;
    private final Player p;

    private PlayerMana(Player p) {
        final PlayerAptitude playerAptitude = PlayerAptitude.getFromPlayer(p);

        this.maxManaBonus = ManaSQL.getMaxManaBonus(p.getUniqueId());

        this.mana = 20 + this.maxManaBonus;
        this.p = p;
        this.manaRegen = 3 + (double) playerAptitude.getMana() / 2.0;
        updateBarXp();
        Main.getInstance().playerMana.put(p, this);
        super.runTaskTimer(Main.getInstance(), 20 * 60, 20 * 60);
    }

    @Override
    public void run() {
        addMana((int) this.manaRegen);
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
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§3§l[§b+ "+mana+"§3§l] §bMana"));
    }

    public void respawn(){
        setMana((20 + this.maxManaBonus) / 2);
    }

    private void updateBarXp(){
        p.setLevel(this.mana);
        p.setExp((float) this.mana / (float) (20 + this.maxManaBonus));
    }

    public void addManaRegen(double manaRegen){
        setManaRegen(this.manaRegen + manaRegen);
    }

    public void setManaRegen(double manaRegen){
        this.manaRegen = manaRegen;
    }

    public static PlayerMana getFromPlayer(Player p){
        return Main.getInstance().playerMana.get(p);
    }

    public static void create(Player p){
        new PlayerMana(p);
    }
}

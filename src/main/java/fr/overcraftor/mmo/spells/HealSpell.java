package fr.overcraftor.mmo.spells;

import fr.overcraftor.mmo.spells.managers.Spell;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HealSpell extends Spell {

    public HealSpell() {
        super(16, "heal", 20 * (60 + 30));
    }

    @Override
    protected void spell(Player p) {
        healPlayer(p);
        p.getNearbyEntities(8, 8, 8).stream().filter(e -> e instanceof Player).forEach(e ->{
            healPlayer((Player) e);
        });
    }

    private void healPlayer(Player p){
        if(p.hasPotionEffect(PotionEffectType.REGENERATION))
            p.removePotionEffect(PotionEffectType.REGENERATION);

        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 30, 2));
    }
}

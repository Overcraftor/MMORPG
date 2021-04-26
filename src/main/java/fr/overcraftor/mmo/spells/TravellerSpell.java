package fr.overcraftor.mmo.spells;

import fr.overcraftor.mmo.spells.managers.Spell;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TravellerSpell extends Spell {

    public TravellerSpell() {
        super(15, "traveller",  20 * 60 * 3);
    }

    @Override
    protected void spell(Player p) {
        if(p.hasPotionEffect(PotionEffectType.SPEED))
            p.removePotionEffect(PotionEffectType.SPEED);

        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 2, 2));
    }
}

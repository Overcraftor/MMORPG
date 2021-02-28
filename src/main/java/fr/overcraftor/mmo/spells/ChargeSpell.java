package fr.overcraftor.mmo.spells;

import fr.overcraftor.mmo.spells.managers.Spell;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class ChargeSpell extends Spell {

    public ChargeSpell() {
        super(10, "charge", 20 * 60 * 2);
    }

    @Override
    protected void spell(Player p) {
        if(p.hasPotionEffect(PotionEffectType.SPEED))
            p.removePotionEffect(PotionEffectType.SPEED);
        if(p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
            p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);

        p.addPotionEffects(Arrays.asList(
                new PotionEffect(PotionEffectType.INCREASE_DAMAGE,  20 * 2, 254),
                new PotionEffect(PotionEffectType.SPEED, 20 * 5, 9)));
    }
}

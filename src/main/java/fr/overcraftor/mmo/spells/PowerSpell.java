package fr.overcraftor.mmo.spells;

import fr.overcraftor.mmo.spells.managers.Spell;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PowerSpell extends Spell {

    public PowerSpell() {
        super(14, "bersek", 20 * 60 * 2);
    }

    @Override
    protected void spell(Player p) {
        if(p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
            p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);

        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 60, 1));
    }
}

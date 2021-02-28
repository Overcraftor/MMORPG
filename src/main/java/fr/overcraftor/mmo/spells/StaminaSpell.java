package fr.overcraftor.mmo.spells;

import fr.overcraftor.mmo.spells.managers.Spell;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StaminaSpell extends Spell {

    public StaminaSpell() {
        super(12, "stamina", 20 * 60 * 5);
    }

    @Override
    protected void spell(Player p) {
        if(p.hasPotionEffect(PotionEffectType.SATURATION))
            p.removePotionEffect(PotionEffectType.SATURATION);

        p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20 * 30, 4));
    }
}

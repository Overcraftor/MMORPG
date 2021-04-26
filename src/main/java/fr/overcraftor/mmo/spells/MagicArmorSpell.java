package fr.overcraftor.mmo.spells;

import fr.overcraftor.mmo.spells.managers.Spell;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MagicArmorSpell extends Spell {

    public MagicArmorSpell() {
        super(14, "magic-armor", 20 * 60 * 5);
    }

    @Override
    protected void spell(Player p) {
        if(p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
            p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);

        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60 * 3, 1));
    }
}

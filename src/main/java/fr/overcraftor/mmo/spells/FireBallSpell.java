package fr.overcraftor.mmo.spells;

import fr.overcraftor.mmo.spells.managers.Spell;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class FireBallSpell extends Spell {

    public FireBallSpell() {
        super(5, "fireball", 0);
    }

    @Override
    protected void spell(Player p) {
        getMain().getSpellManager().addFireBallLaunched(p.launchProjectile(Fireball.class));
    }
}

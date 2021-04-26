package fr.overcraftor.mmo.spells;

import fr.overcraftor.mmo.spells.managers.Spell;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class PushSpell extends Spell {

    public PushSpell() {
        super(8, "expulsion", 20 * 20);
    }

    @Override
    protected void spell(Player p) {
        final List<Entity> nearbyEntities = p.getNearbyEntities(6, 6, 6);
        nearbyEntities.stream().filter(entity -> !(entity instanceof Player)).forEach(entity -> {
            final Vector unitVector = entity.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(4);
            entity.setVelocity(unitVector);
        });
    }
}

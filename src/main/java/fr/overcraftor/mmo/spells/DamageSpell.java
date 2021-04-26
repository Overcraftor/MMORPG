package fr.overcraftor.mmo.spells;

import fr.overcraftor.mmo.spells.managers.Spell;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class DamageSpell extends Spell {

    private final Random random;

    public DamageSpell() {
        super(12, "damage", 20 * 20);
        this.random = new Random();
    }

    @Override
    protected void spell(Player p) {
        final List<Entity> entities = p.getNearbyEntities(8, 8, 8);
        entities.forEach(entity -> {
            if(entity instanceof LivingEntity && !(entity instanceof Player)){
                ((LivingEntity) entity).damage(random.nextInt(6) + 4, p);
            }
        });
    }
}

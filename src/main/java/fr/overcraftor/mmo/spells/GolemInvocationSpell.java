package fr.overcraftor.mmo.spells;

import fr.overcraftor.mmo.customentities.GolemSpellEntity;
import fr.overcraftor.mmo.spells.managers.Spell;
import net.minecraft.server.v1_14_R1.EntityGolem;
import net.minecraft.server.v1_14_R1.WorldServer;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GolemInvocationSpell extends Spell {

    public GolemInvocationSpell() {
        super(30, "golem-invocation", 20 * 60 * 10);
    }

    @Override
    protected void spell(Player p) {
        final GolemSpellEntity golem = new GolemSpellEntity(p);

        final WorldServer world = ((CraftWorld) p.getWorld()).getHandle();
        world.addEntity(golem);
        golem.followPlayer();

        final List<EntityGolem> golems = getMain().customGolems.get(p) != null ? getMain().customGolems.get(p) : new ArrayList<>();
        golems.add(golem);
        getMain().customGolems.put(p, golems);
    }
}

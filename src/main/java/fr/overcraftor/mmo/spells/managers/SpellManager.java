package fr.overcraftor.mmo.spells.managers;

import fr.overcraftor.mmo.spells.*;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

import java.util.*;

public class SpellManager {

    private final List<Spell> spells;
    private final Map<UUID, List<String>> cooldowns;
    private final List<Fireball> fireballsLaunched;

    public SpellManager() {
        this.spells = new ArrayList<>();
        this.cooldowns = new HashMap<>();
        this.fireballsLaunched = new ArrayList<>();

        this.spells.addAll(Arrays.asList(
                new TravellerSpell(),
                new ChargeSpell(),
                new DamageSpell(),
                new HealSpell(),
                new FireBallSpell(),
                new StaminaSpell(),
                new MagicArmorSpell(),
                new PowerSpell(),
                new GolemInvocationSpell(),
                new PushSpell()));
    }

    public void executeSpell(Player p, String spellItemName) {
        Optional<Spell> currentSpell = spells.stream().filter(spell -> spell.getItemName().equals(spellItemName)).findFirst();

        currentSpell.ifPresent(current ->{
            currentSpell.get().use(p);
        });
    }

    public void putCooldown(UUID uuid, String spellName){
        List<String> spellList = this.cooldowns.get(uuid);
        if(spellList == null)
            spellList = new ArrayList<>();

        spellList.add(spellName);
        this.cooldowns.put(uuid, spellList);
    }

    public void removeCooldown(UUID uuid, String itemName) {
        this.cooldowns.get(uuid).remove(itemName);
    }

    public boolean isInCooldown(Player p, String spellName){
        return this.cooldowns.containsKey(p.getUniqueId()) && this.cooldowns.get(p.getUniqueId()).contains(spellName);
    }

    public void addFireBallLaunched(Fireball fireball){
        this.fireballsLaunched.add(fireball);
    }

    public boolean onFireBallExplode(Fireball fireball){
        return this.fireballsLaunched.remove(fireball);
    }
}

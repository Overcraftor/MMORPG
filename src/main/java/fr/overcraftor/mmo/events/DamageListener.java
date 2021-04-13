package fr.overcraftor.mmo.events;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.GuildSQL;
import fr.overcraftor.mmo.utils.aptitude.PlayerAptitude;
import net.minecraft.server.v1_14_R1.EntityGolem;
import net.minecraft.server.v1_14_R1.EntityLiving;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftGolem;
import org.bukkit.entity.Golem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        // guilds
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            if(GuildSQL.areInSameGuild(e.getDamager().getUniqueId(), e.getEntity().getUniqueId()))
                e.setCancelled(true);
        }

        // aptitudes DAMAGE
        if(e.getDamager() instanceof Player){
            final double multiplicator = (PlayerAptitude.getFromPlayer((Player) e.getDamager()).getStrength() * 3.0 / 100.0) + 1;
            e.setDamage(e.getDamage() * multiplicator);
        }

        // ------------- CUSTOM GOLEM ------------ //
        //owner hit golem
        if(e.getEntity() instanceof Golem){
            final EntityGolem golem = ((CraftGolem) e.getEntity()).getHandle();
            if(!e.getEntity().getCustomName().equals("§c§lGardien"))
                return;

            if(e.getDamager() instanceof Player){
                final Player p = (Player) e.getDamager();
                if(Main.getInstance().customGolems.containsKey(p) && Main.getInstance().customGolems.get(p).contains(golem)){
                    e.setCancelled(true);
                    return;
                }
            }
        }

        //mob / player hit owner golem
        if(e.getEntity() instanceof Player){
            final Player p = (Player) e.getEntity();
            if(Main.getInstance().customGolems.containsKey(p)){
                Main.getInstance().customGolems.get(p).forEach(golem ->{
                    golem.setGoalTarget(
                            (EntityLiving) ((CraftEntity) e.getDamager()).getHandle(),
                            EntityTargetEvent.TargetReason.CUSTOM,
                            false
                    );
                });
            }
        }
    }
}
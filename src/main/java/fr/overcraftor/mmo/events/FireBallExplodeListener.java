package fr.overcraftor.mmo.events;

import fr.overcraftor.mmo.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class FireBallExplodeListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent e){
        final Entity entity = e.getEntity();

        if(entity instanceof Fireball){
            if(Main.getInstance().getSpellManager().onFireBallExplode((Fireball) entity))
                e.blockList().clear();
        }
    }

}

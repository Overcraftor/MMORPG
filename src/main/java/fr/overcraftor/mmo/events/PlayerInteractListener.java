package fr.overcraftor.mmo.events;

import fr.overcraftor.mmo.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        final ItemStack currentItem = e.getItem();

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName()){
            Main.getInstance().getSpellManager().executeSpell(p, currentItem.getItemMeta().getDisplayName());
        }
    }

}

package fr.overcraftor.mmo.events;

import fr.overcraftor.mmo.inventories.JobInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnInventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;

        if(e.getView().getTitle().equals(JobInventory.titleName))
            JobInventory.onClick(e);
    }

}

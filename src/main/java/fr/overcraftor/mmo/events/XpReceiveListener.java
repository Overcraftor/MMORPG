package fr.overcraftor.mmo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class XpReceiveListener implements Listener {

    @EventHandler
    public void onXpReceive(PlayerExpChangeEvent e){
        e.setAmount(e.getPlayer().getExpToLevel() - e.getAmount());
    }

}

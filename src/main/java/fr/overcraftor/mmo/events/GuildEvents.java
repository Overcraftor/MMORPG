package fr.overcraftor.mmo.events;

import fr.overcraftor.mmo.mysql.GuildSQL;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GuildEvents implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e){
        final String tag = GuildSQL.getGuildTag(e.getPlayer().getUniqueId());
        final String format = (tag == null ? "" : "§b[§f"+tag+"§b] ") + "%luckperms_prefix% " + e.getPlayer().getName() + " %luckperms_suffix% " + e.getMessage();
        final String formatWithPlaceHolders = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
        e.setFormat(formatWithPlaceHolders);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            if(GuildSQL.areInSameGuild(e.getDamager().getUniqueId(), e.getEntity().getUniqueId()))
                e.setCancelled(true);
        }
    }
}
package fr.overcraftor.mmo.events;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.GuildSQL;
import fr.overcraftor.mmo.utils.generalXp.XpLevel;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e){
        //GUILD_TAG + LEVEL + PREFIX_LUCKPERMS
        final String tag = GuildSQL.getGuildTag(e.getPlayer().getUniqueId());
        final int level = new XpLevel(Main.getInstance().generalXp.get(e.getPlayer())).getLevel();

        final String format = (tag == null ? "" : "§b[§f"+tag+"§b] ") + "&b[&a"+level+"&b] " + "%luckperms_prefix% " + e.getPlayer().getName() + " %luckperms_suffix% " + e.getMessage();

        final String formatWithPlaceHolders = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
        e.setFormat(formatWithPlaceHolders);
    }

}

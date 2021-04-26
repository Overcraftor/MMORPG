package fr.overcraftor.mmo.events;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.config.ConfigManager;
import fr.overcraftor.mmo.mysql.GuildSQL;
import fr.overcraftor.mmo.utils.generalXp.XpLevel;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e){
        //GUILD_TAG + LEVEL + PREFIX_LUCKPERMS
        final String tag = GuildSQL.getGuildTag(e.getPlayer().getUniqueId());
        final String guildName = GuildSQL.getGuildName(e.getPlayer().getUniqueId());
        final XpLevel level = new XpLevel(Main.getInstance().generalXp.get(e.getPlayer()));

        // MESSAGE FORMAT
        final String format = (tag == null ? "" : "§b[§f"+tag+"§b] ") + "&b[&a"+level.getLevel()+"&b] " + "%luckperms_prefix% " + e.getPlayer().getName() + " %luckperms_suffix% " + e.getMessage();
        final String formatWithPlaceHolders = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);

        if(ConfigManager.getConfig().getBoolean("chatWithTextComponent")){
            // TEXT COMPONENT
            final TextComponent msg = new TextComponent(formatWithPlaceHolders);
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("§eLevel: §7" + level.getLevel())
                            .append("\n§eXp: §7" + level.getXpRemain() + "§r / §7" + level.getXpNeed())
                            .append("\n§eGuild: §7" + guildName)
                            .create()));

            // SEND
            Bukkit.spigot().broadcast(msg);
            e.setCancelled(true);
        }else{
            e.setFormat(formatWithPlaceHolders);
        }
    }

}

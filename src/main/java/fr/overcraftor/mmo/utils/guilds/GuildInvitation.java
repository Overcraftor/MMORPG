package fr.overcraftor.mmo.utils.guilds;

import fr.overcraftor.mmo.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class GuildInvitation extends BukkitRunnable {

    private final UUID receiver;
    private final String guildName;

    public GuildInvitation(UUID receiver, String guildName) {
        this.receiver = receiver;
        this.guildName = guildName;
        super.runTaskLater(Main.getInstance(), 20 * 60 * 2); //2 minutes
        Main.getInstance().invitations.add(this);
    }

    public void sendMessage(){
        final TextComponent message = new TextComponent("§aVous êtes invité dans la guilde §2" + guildName + "§a. \nClick sur ce message pour la rejoindre");
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eClick ici si tu veux rejoindre cette guilde").create()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guild join " + guildName));

        Bukkit.getPlayer(receiver).spigot().sendMessage(message);
    }

    public boolean isInvited(UUID uuid, String guildName){
        return this.receiver.equals(uuid) && this.guildName.equals(guildName);
    }

    @Override
    public void run() {
        Main.getInstance().invitations.remove(this);
        final OfflinePlayer p = Bukkit.getOfflinePlayer(receiver);

        if(p.isOnline()){
            p.getPlayer().sendMessage("§cL'invitation pour la guild §6" + guildName + " §ca expirée");
        }
        cancel();
    }

    public static void remove(UUID receiver, String guildName){
        final GuildInvitation invitation = Main.getInstance().invitations.stream().filter(inv -> inv.isInvited(receiver, guildName)).findFirst().get();
        Main.getInstance().invitations.remove(invitation);
    }
}

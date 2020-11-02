package fr.overcraftor.mmo.events;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.utils.Permissions;
import fr.overcraftor.mmo.config.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        final String blockType = e.getBlock().getType().toString();
        if(ConfigManager.blockBreak.containsKey(blockType.toLowerCase())){

            if(e.getPlayer().hasPermission(Permissions.BREAK_BLOCK.get() + blockType.toLowerCase())){
                ConfigManager.blockBreak.get(blockType.toLowerCase()).forEach(command -> {
                    Main.getInstance().getServer().dispatchCommand(Main.getInstance().getServer().getConsoleSender(),
                            command.replace("{user}", e.getPlayer().getName()));
                });
            }else{
                e.setCancelled(true);
            }

        }
    }
}

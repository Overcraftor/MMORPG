package fr.overcraftor.mmo.inventories;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.utils.ItemBuilder;
import fr.overcraftor.mmo.utils.jobs.JobsNames;
import fr.overcraftor.mmo.utils.jobs.JobsLevel;
import fr.overcraftor.mmo.utils.jobs.JobsXpUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class JobInventory{

    public static final String titleName = ChatColor.GOLD + "Metiers";
    private static final Inventory inv = Bukkit.createInventory(null, 27, titleName);

    public static void openInv(Player p){
        final HashMap<JobsNames, Integer> map = Main.jobsXp.get(p);
        final Inventory inventory = inv;

        //JOBS XP
        final JobsXpUtils wood_cutter = new JobsXpUtils(map.get(JobsNames.WOOD_CUTTER));
        final JobsXpUtils blacksmith = new JobsXpUtils(map.get(JobsNames.BLACKSMITH));
        final JobsXpUtils miner = new JobsXpUtils(map.get(JobsNames.MINER));
        final JobsXpUtils enchanter = new JobsXpUtils(map.get(JobsNames.ENCHANTER));
        final JobsXpUtils farmer = new JobsXpUtils(map.get(JobsNames.FARMER));

        ItemBuilder wood_cutterBuilder =  new ItemBuilder(Material.DIAMOND_AXE, "§6" + JobsNames.WOOD_CUTTER.toName())
                .setLore("§eNiveau: §6" + wood_cutter.getLevels(), "", "§eXP: §6" + wood_cutter.getXpRemain() + "/" + JobsLevel.getFromLevel(wood_cutter.getLevels()).getObjective());

        ItemBuilder blacksmithBuilder =  new ItemBuilder(Material.ANVIL, "§6" + JobsNames.BLACKSMITH.toName())
                .setLore("§eNiveau: §6" + blacksmith.getLevels(), "", "§eXP: §6" + blacksmith.getXpRemain() + "/" + JobsLevel.getFromLevel(blacksmith.getLevels()).getObjective());

        ItemBuilder minerBuilder =  new ItemBuilder(Material.DIAMOND_PICKAXE, "§6" + JobsNames.MINER.toName())
                .setLore("§eNiveau: §6" + miner.getLevels(), "", "§eXP: §6" + miner.getXpRemain() + "/" + JobsLevel.getFromLevel(miner.getLevels()).getObjective());

        ItemBuilder enchanterBuilder =  new ItemBuilder(Material.ENCHANTING_TABLE, "§6" + JobsNames.ENCHANTER.toName())
                .setLore("§eNiveau: §6" + enchanter.getLevels(), "", "§eXP: §6" + enchanter.getXpRemain() + "/" + JobsLevel.getFromLevel(enchanter.getLevels()).getObjective());

        ItemBuilder farmerBuilder =  new ItemBuilder(Material.DIAMOND_HOE, "§6" + JobsNames.FARMER.toName())
                .setLore("§eNiveau: §6" + farmer.getLevels(), "", "§eXP: §6" + farmer.getXpRemain() + "/" + JobsLevel.getFromLevel(farmer.getLevels()).getObjective());

        if(wood_cutter.getLevels() == 10) wood_cutterBuilder = wood_cutterBuilder.setGlow();
        if(blacksmith.getLevels() == 10) blacksmithBuilder = blacksmithBuilder.setGlow();
        if(miner.getLevels() == 10) minerBuilder = minerBuilder.setGlow();
        if(enchanter.getLevels() == 10) enchanterBuilder = enchanterBuilder.setGlow();
        if(farmer.getLevels() == 10) farmerBuilder = farmerBuilder.setGlow();

        inventory.setItem(11, wood_cutterBuilder.toItemStack());

        inventory.setItem(12, blacksmithBuilder.toItemStack());

        inventory.setItem(13, minerBuilder.toItemStack());

        inventory.setItem(14, enchanterBuilder.toItemStack());

        inventory.setItem(15, farmerBuilder.toItemStack());

        p.openInventory(inv);
    }

    public static void onClick(InventoryClickEvent e){
        if(e.getView().getTitle().equals(titleName)){
            e.setCancelled(true);
        }
    }

}

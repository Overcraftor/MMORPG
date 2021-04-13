package fr.overcraftor.mmo.inventories;

import fr.overcraftor.mmo.mysql.ManaSQL;
import fr.overcraftor.mmo.utils.ItemBuilder;
import fr.overcraftor.mmo.utils.aptitude.PlayerAptitude;
import fr.overcraftor.mmo.utils.mana.PlayerMana;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class AptInventory {

    public static final String invName = "§c§lCompétence §7§l- §a§lPoints: ";

    public static void openInventory(Player p){
        final PlayerAptitude playerAptitude = PlayerAptitude.getFromPlayer(p);
        final Inventory inv = Bukkit.createInventory(null, 3 * 9, invName + playerAptitude.getApt());
        final ItemStack grayGlass = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, " ").toItemStack();

        for(int i = 0; i < 27; i++)
            inv.setItem(i, grayGlass);

        //TODO remplacer par les données dans sql (save dans un objet PlayerApitude)

        inv.setItem(10, new ItemBuilder(Material.DIAMOND_SWORD, "§c§lForce").setLore(
                "§cNiveau actuel: " + playerAptitude.getStrength(), " ",
                "§cForce supplémentaire: "+ playerAptitude.getStrength()*3 +"%", " ", "§7Améliorer cette compétence rajoutera 3%", "§7de dégâts supplémentaires.").addFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());

        inv.setItem(12, new ItemBuilder(Material.TOTEM_OF_UNDYING, "§d§lVitalité").setLore(
                "§dNiveau actuel: " + playerAptitude.getHealth(), " ",
                "§dCoeurs supplémentaires: " + playerAptitude.getHealth()*0.25, " ", "§7Améliorer cette compétence rajoutera 0.25", "§7coeurs supplémentaires.").toItemStack());

        inv.setItem(14, new ItemBuilder(Material.BLAZE_POWDER, "§b§lMana").setLore(
                "§bNiveau actuel: " + playerAptitude.getMana(), " ",
                "§bMana supplémentaire: " + playerAptitude.getMana() * 5, "§bRégénération de mana supplémentaire: " + (int) (playerAptitude.getMana() / 2.0), " ", "§7Améliorer cette compétence rajoutera 5 mana", "§7supplémentaire et 0.5 mana de régénération.").toItemStack());

        inv.setItem(16, new ItemBuilder(Material.LEATHER_BOOTS, "§a§lAgilité").setLore(
                "§aNiveau actuel: " + playerAptitude.getSpeed(), " ",
                "§aVitesse supplémentaire: "+ playerAptitude.getSpeed()*3 +"%", " ", "§7Améliorer cette compétence rajoutera 3%", "§7de vitesse supplémentaires.").addFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());
        p.openInventory(inv);
    }

    public static void onClick(InventoryClickEvent e){
        e.setCancelled(true);
        final Player p = (Player) e.getWhoClicked();
        final PlayerAptitude playerAptitude = PlayerAptitude.getFromPlayer(p);

        if(e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE)
            return;

        if(playerAptitude.getApt() > 0){
            p.closeInventory();

            switch (e.getCurrentItem().getType()){
                case DIAMOND_SWORD:
                    playerAptitude.setStrength(playerAptitude.getStrength() + 1);
                    p.sendMessage("§aVous avez augmenté votre §cForce§a, vous êtes désormais niveau §6" + playerAptitude.getStrength());
                    break;

                case TOTEM_OF_UNDYING:
                    playerAptitude.setHealth(playerAptitude.getHealth() + 1);
                    p.setHealthScale(p.getHealthScale() + 0.5);
                    p.setMaxHealth(p.getMaxHealth() + 0.5);
                    p.sendMessage("§aVous avez augmenté votre §dVitalité§a, vous êtes désormais niveau §6" + playerAptitude.getHealth());
                    break;

                case BLAZE_POWDER:
                    playerAptitude.setMana(playerAptitude.getMana() + 1);
                    ManaSQL.addManaMaxBonus(p.getUniqueId(), 5);
                    PlayerMana.getFromPlayer(p).addMaxManaBonus(5);
                    PlayerMana.getFromPlayer(p).addManaRegen(0.5);
                    p.sendMessage("§aVous avez augmenté votre §bMana§a, vous êtes désormais niveau §6" + playerAptitude.getMana());
                    break;

                case LEATHER_BOOTS:
                    final float multiplicator = (float) (playerAptitude.getSpeed() + 1) * 3.0f / 100.0f + 1.0f;
                    if(0.2f * multiplicator >= 1.0f){
                        p.sendMessage("§cVous avez atteind le niveau max de cette aptitude !");
                        return;
                    }
                    playerAptitude.setSpeed(playerAptitude.getSpeed() + 1);
                    p.setWalkSpeed(0.2f * multiplicator);
                    p.sendMessage("§aVous avez augmenté votre §2Vitesse§a, vous êtes désormais niveau §6" + playerAptitude.getSpeed());
                    break;

                default: break;
            }

            playerAptitude.setApt(playerAptitude.getApt() - 1);
            AptInventory.openInventory(p);
        }else{
            p.sendMessage("§cVous n'avez plus de points d'aptitudes !");
        }
    }

}

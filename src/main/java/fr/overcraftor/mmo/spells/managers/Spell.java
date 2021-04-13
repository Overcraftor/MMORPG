package fr.overcraftor.mmo.spells.managers;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.config.ConfigManager;
import fr.overcraftor.mmo.utils.mana.PlayerMana;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class Spell {

    private final int manaCost;
    private final String itemName;
    private final long cooldown;
    private final Main main;

    /**
     * Class abstract pour créer un sort, il faut en suite l'enregistrer dans fr.overcraftor.mmo.spells.managers.SpellManager;
     * @param manaCost Coût du mana pour lancer le sort
     * @param spellNameConfig Nom du sort dans la config !! obligatoire !!
     * @param cooldownInTicks Cooldown du sort en ticks
     */
    public Spell(int manaCost, String spellNameConfig, int cooldownInTicks) {
        this.manaCost = manaCost;
        this.itemName = ConfigManager.getConfig().getString(spellNameConfig);
        this.cooldown = cooldownInTicks;
        this.main = Main.getInstance();
    }

    /**
     * Method utiliser dans un enfant de cette classe pour définir les actions à effectuées lors de l'execution du sort
     * @param p Player who execute the spell
     *
     */
    protected abstract void spell(Player p);

    /**
     * Method utilisé seulement par SpellManager
     * @param p Player qui execute le sort
     */
    public void use(Player p){
        if(Main.getInstance().getSpellManager().isInCooldown(p, this.itemName)){
            final String cooldownString = this.cooldown / 20 >= 60 ? this.cooldown / 20 / 60 + " §cminutes." : this.cooldown / 20 + " §csecondes.";
            p.sendMessage("§cVous avez un cooldown de §4" + cooldownString);
            return;
        }

        if(PlayerMana.getFromPlayer(p).removeMana(this.manaCost)){
            this.spell(p);
            Main.getInstance().getSpellManager().putCooldown(p.getUniqueId(), this.itemName);
            run(p.getUniqueId(), this.itemName);

            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§4§l[§c-(" + this.manaCost + ")§4§l] §c(" + this.itemName + ")"));
        }else{
            p.sendMessage("§cVous n'avez pas assez de mana pour lancer ce sort.");
        }
    }

    /**
     * Method pour lancer le cooldown du sort
     * @param uuid Executeur du sort
     * @param itemName Nom du sort
     */
    private void run(UUID uuid, String itemName) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            Main.getInstance().getSpellManager().removeCooldown(uuid, itemName);
        }, this.cooldown);
    }

    /**
     * @return Le nom du sort stocké dans le fichier config.yml
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @return Plugin instance
     */
    protected Main getMain(){
        return this.main;
    }
}

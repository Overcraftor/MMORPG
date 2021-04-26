package fr.overcraftor.mmo.customentities;

import fr.overcraftor.mmo.Main;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class GolemSpellEntity extends EntityIronGolem {

    private int scheduler;
    private final Player owner;

    public GolemSpellEntity(Player owner) {
        super(EntityTypes.IRON_GOLEM, ((CraftWorld)owner.getLocation().getWorld()).getHandle());
        this.owner = owner;

        final Location loc = owner.getLocation();

        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        this.setCustomName(new ChatComponentText("§c§lGardien"));
        this.setCustomNameVisible(true);
        this.setHealth(200);

        this.goalSelector.a(1, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        final GolemSpellEntity e = this;
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if(e.isAlive())
                e.killEntity();
        }, 20 * 180);
    }

    public void followPlayer() {
        scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(),
                () -> {
                    final CraftEntity livingEntity = getBukkitEntity();
                    
                    if(owner == null || !super.isAlive()){
                        stopFollowingPlayer();
                        return;
                    }

                    if(this.getGoalTarget() == null || !this.getGoalTarget().isAlive())
                        this.setGoalTarget(null, EntityTargetEvent.TargetReason.CUSTOM, false);

            
                    double distance = livingEntity.getLocation().distance(
                            owner.getLocation());
                    if (distance < 11) {
                        float speed = 1;
                        if (distance < 3) {
                            speed = 0;
                        }
                        ((CraftCreature) livingEntity)
                                .getHandle()
                                .getNavigation()
                                .a(owner.getLocation().getX(),
                                        owner.getLocation().getY(),
                                        owner.getLocation().getZ(),
                                        speed);
                    } else {
                        if (owner.isOnGround())
                            livingEntity.teleport(owner);
                    }
                }, 0, 20);
    }

    public void stopFollowingPlayer() {
        Bukkit.getScheduler().cancelTask(scheduler);
    }
}

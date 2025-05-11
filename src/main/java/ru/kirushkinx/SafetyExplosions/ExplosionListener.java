package ru.kirushkinx.SafetyExplosions;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;

public class ExplosionListener implements Listener {

    private final SafetyExplosions plugin;

    public ExplosionListener(SafetyExplosions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        EntityType entityType = event.getEntityType();

        if (entityType == EntityType.END_CRYSTAL && plugin.isPreventBlockDamageEndCrystal()) {
            event.blockList().clear();
        } else if (entityType == EntityType.TNT && plugin.isPreventBlockDamageTnt()) {
            event.blockList().clear();
        } else if (entityType == EntityType.TNT_MINECART && plugin.isPreventBlockDamageTntMinecart()) {
            event.blockList().clear();
        } else if (entityType == EntityType.CREEPER && plugin.isPreventBlockDamageCreeper()) {
            event.blockList().clear();
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        Block block = event.getBlock();
        String blockType = block.getType().name();
        if ((plugin.isPreventBlockDamageRespawnAnchorOrBed() && blockType.contains("_BED")) ||
                (plugin.isPreventBlockDamageRespawnAnchorOrBed() && blockType.equals("RESPAWN_ANCHOR"))) {
            event.blockList().clear();
        } else if (plugin.isPreventBlockDamageRespawnAnchorOrBed()) {
            event.blockList().clear();
        }
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            return;
        }

        EntityType entityType = event.getEntityType();
        if ((plugin.isIgnoreItemFrame() && (entityType == EntityType.ITEM_FRAME || entityType == EntityType.GLOW_ITEM_FRAME)) ||
                (plugin.isIgnoreArmorStand() && entityType == EntityType.ARMOR_STAND)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            return;
        }

        EntityType entityType = event.getEntityType();
        if ((plugin.isIgnoreItemFrame() && (entityType == EntityType.ITEM_FRAME || entityType == EntityType.GLOW_ITEM_FRAME)) ||
                (plugin.isIgnoreArmorStand() && entityType == EntityType.ARMOR_STAND)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        EntityType entityType = event.getEntityType();
        if (plugin.isIgnoreArmorStand() && entityType == EntityType.ARMOR_STAND) {
            EntityDamageEvent.DamageCause cause = event.getCause();
            if (cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION ||
                    cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION ||
                    cause == EntityDamageEvent.DamageCause.CUSTOM) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        EntityType entityType = event.getEntityType();
        if (plugin.isIgnoreArmorStand() && entityType == EntityType.ARMOR_STAND) {
            EntityDamageEvent lastDamage = event.getEntity().getLastDamageCause();
            if (lastDamage != null && (lastDamage.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION ||
                    lastDamage.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION ||
                    lastDamage.getCause() == EntityDamageEvent.DamageCause.CUSTOM)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHangingBreak(HangingBreakEvent event) {
        if (event.getCause() != HangingBreakEvent.RemoveCause.EXPLOSION) {
            return;
        }

        EntityType entityType = event.getEntity().getType();
        if (plugin.isIgnoreItemFrame() && (entityType == EntityType.ITEM_FRAME || entityType == EntityType.GLOW_ITEM_FRAME)) {
            event.setCancelled(true);
        }
    }
}
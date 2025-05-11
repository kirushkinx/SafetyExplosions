package ru.kirushkinx.SafetyExplosions;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SafetyExplosions extends JavaPlugin {

    private boolean preventBlockDamageEndCrystal;
    private boolean preventBlockDamageRespawnAnchorOrBed;
    private boolean preventBlockDamageTnt;
    private boolean preventBlockDamageTntMinecart;
    private boolean preventBlockDamageCreeper;
    private boolean ignoreItemFrame;
    private boolean ignoreArmorStand;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();

        getServer().getPluginManager().registerEvents(new ExplosionListener(this), this);

        getCommand("safetyexplosions").setExecutor(new ReloadCommand(this));

        getLogger().info("SafetyExplosions plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SafetyExplosions plugin disabled!");
    }

    public void loadConfig() {
        FileConfiguration config = getConfig();
        preventBlockDamageEndCrystal = config.getBoolean("prevent-blocks-from-exploding.end_crystal", true);
        preventBlockDamageRespawnAnchorOrBed = config.getBoolean("prevent-blocks-from-exploding.respawn_anchor/bed", true);
        preventBlockDamageTnt = config.getBoolean("prevent-blocks-from-exploding.tnt", true);
        preventBlockDamageTntMinecart = config.getBoolean("prevent-blocks-from-exploding.tnt_minecart", true);
        preventBlockDamageCreeper = config.getBoolean("prevent-blocks-from-exploding.creeper", true);
        ignoreItemFrame = config.getBoolean("invulnerable-entities.item_frame", true);
        ignoreArmorStand = config.getBoolean("invulnerable-entities.armor_stand", true);
    }

    public void reloadPluginConfig() {
        reloadConfig();
        loadConfig();
    }

    public boolean isPreventBlockDamageEndCrystal() {
        return preventBlockDamageEndCrystal;
    }

    public boolean isPreventBlockDamageRespawnAnchorOrBed() {
        return preventBlockDamageRespawnAnchorOrBed;
    }

    public boolean isPreventBlockDamageTnt() {
        return preventBlockDamageTnt;
    }

    public boolean isPreventBlockDamageTntMinecart() {
        return preventBlockDamageTntMinecart;
    }

    public boolean isPreventBlockDamageCreeper() {
        return preventBlockDamageCreeper;
    }

    public boolean isIgnoreItemFrame() {
        return ignoreItemFrame;
    }

    public boolean isIgnoreArmorStand() {
        return ignoreArmorStand;
    }
}
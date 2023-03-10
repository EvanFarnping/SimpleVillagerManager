package me.evanf.removevillagers;

import me.evanf.removevillagers.manager.VillagerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class RemoveVillagers extends JavaPlugin {

    public static RemoveVillagers plugin;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = getConfig();

        new VillagerManager(this);

        getLogger().info("Enable RemoveVillagers Plugin " +
                getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled RemoveVillagers Plugin " +
                getDescription().getVersion());
    }
}

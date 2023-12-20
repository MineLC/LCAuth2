package lc.auth2;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LCConfig {
    private final Auth2 plugin;
    private FileConfiguration fileConfiguration = null;
    private File file = null;

    public LCConfig(Auth2 plugin){
        this.plugin = plugin;
    }

    public void registerConfig(){
        file = new File(plugin.getDataFolder(), "minelc.yml");


        if(!file.exists()){
            plugin.saveResource("minelc.yml", false);

        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getLogger().severe("Ha habido un IOExcepcion o InvalidConigurationExcepcion cargando el archivo minelc.yml");
        }
    }
    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Ha habido un IOExcepcion guardando el archivo minelc.yml");
        }
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            reloadConfig();
        }
        return fileConfiguration;
    }

    public void reloadConfig() {
        if (fileConfiguration == null) {
            file = new File(plugin.getDataFolder(), "minelc.yml");
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);

        if(file != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(file);
            fileConfiguration.setDefaults(defConfig);
        }
    }
}

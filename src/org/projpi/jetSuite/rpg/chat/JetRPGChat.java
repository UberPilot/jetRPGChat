package org.projpi.jetSuite.rpg.chat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.projpi.jetSuite.rpg.chat.chat.ChatListener;
import org.projpi.jetSuite.rpg.chat.commands.BaseCommand;
import org.projpi.jetSuite.rpg.chat.commands.OOC;
import org.projpi.jetSuite.rpg.chat.commands.PrivateMessage;
import org.projpi.jetSuite.rpg.chat.commands.SocialSpy;
import org.projpi.jetSuite.rpg.chat.settings.ChatType;
import org.projpi.jetSuite.rpg.chat.settings.Values;
import org.projpi.jetSuite.rpg.chat.util.LoadPlayer;
import org.projpi.jetSuite.rpg.chat.util.Resources;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;

public class JetRPGChat extends JavaPlugin
{
    private static File configFile, dataFile;
    private static YamlConfiguration config, data;
    //Which hooks will be enabled?
    private static boolean placeholders, rpg, playerslib;
    //Username, OOC toggled
    public static HashMap<String, Boolean> prefs;
    public static HashMap<String, String> lastFrom;
    public static HashSet<String> spies;
    public static HashSet<String> muted;
    private static JetRPGChat instance;
    private static int configVersion;

    public static File getConfigFile()
    {
        return configFile;
    }

    public static File getDataFile()
    {
        return dataFile;
    }

    public static boolean isPlaceholders()
    {
        return placeholders;
    }

    public static boolean isRpg()
    {
        return rpg;
    }

    public static boolean isPlayerslib()
    {
        return playerslib;
    }

    public static JetRPGChat getInstance()
    {
        return instance;
    }

    public static int getConfigVersion()
    {
        return configVersion;
    }

    @Override
    public void onEnable()
    {
        prefs = new HashMap<>();
        lastFrom = new HashMap<>();
        muted = new HashSet<>();
        spies = new HashSet<>();
        instance = this;
        //Check if placeholders are enabled.
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
        {

        } else
        {
            getLogger().log(Level.INFO, "Placeholder API not found. Placeholders will not be supported.");
        }
        placeholders = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        //Check if jetRPG is enabled.
        if (Bukkit.getPluginManager().isPluginEnabled("jetRPG"))
        {

        }
        else
        {
            getLogger().log(Level.INFO, "jetRPG not found. This plugin's capabilities are intended for that plugin.");
        }
        rpg = Bukkit.getPluginManager().isPluginEnabled("jetRPG");
        //Check if jetPlayersLib is enabled.
        if (Bukkit.getPluginManager().isPluginEnabled("jetPlayersLib"))
        {

        }
        else
        {
            getLogger().log(Level.INFO, "jetPlayersLib not found. Player data will be stored independently.");
        }
        playerslib = Bukkit.getPluginManager().isPluginEnabled("jetPlayersLib");
        //Create the config and such
        reload();
        getCommand("ooc").setExecutor(new OOC());
        getCommand("message").setExecutor(new PrivateMessage());
        getCommand("socialspy").setExecutor(new SocialSpy());
        getCommand("jetrpgchat").setExecutor(new BaseCommand());
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new LoadPlayer(), this);
        MetricsLite metricsLite = new MetricsLite(this);
    }

    @Override
    public void onDisable()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            LoadPlayer.savePlayer(p);
        }
    }

    private void createConfigs() {
        try
        {
            if (!getDataFolder().exists())
            {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists())
            {
                getLogger().info("config.yml not found, creating!");
                configFile = Resources.loadResource(this, "config.yml");
            }
            else
            {
                getLogger().info("config.yml found, loading!");
                configFile = new File(getDataFolder(), "config.yml");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        dataFile = new File(getDataFolder(), "data.yml");
        if(!dataFile.exists())
        {
            saveResource("data.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    @Override
    public YamlConfiguration getConfig()
    {
        return config;
    }

    public YamlConfiguration getData()
    {
        return data;
    }

    public void reload()
    {
        createConfigs();
        configVersion = config.getInt("config-version", 1);
        ChatType.load();
        Values.load();
    }
}



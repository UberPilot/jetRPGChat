package org.projpi.jetSuite.rpg.chat.settings;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.projpi.jetSuite.rpg.chat.JetRPGChat;
import java.util.List;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class Values
{
    public static boolean parseColor, permissionColor, deniedColor;

    public static String pmFormatTo, pmFormatFrom, pmFormatSpy;

    public static List<String> jsonHover;

    public static String jsonParameter, clickEvent, jsonMessage;

    public static String prefix, oocOn, oocOff, permissionDenied, command, color, invalidCommand, playerNotFound, socialSpy, on, off, firstJoin, join, leave;

    public static String mutedMessage;

    public static void load()
    {
        YamlConfiguration conf = JetRPGChat.getInstance().getConfig();

        //Color Feature Settings
        parseColor = conf.getBoolean("features.color.parse", true);
        permissionColor = conf.getBoolean("features.color.use-permission", true);
        deniedColor = conf.getBoolean("features.color.denied-message", true);

        //Formats
        pmFormatTo = ChatColor.translateAlternateColorCodes('&', conf.getString("formats.pm-format-to", "&6[Me -> /target/&6] &f/message/"));
        pmFormatFrom = ChatColor.translateAlternateColorCodes('&', conf.getString("formats.pm-format-from", "&6[/sender/ -> Me&6] &f/message/"));
        pmFormatSpy = ChatColor.translateAlternateColorCodes('&', conf.getString("formats.pm-format-spy", "&6[/sender/ -> /target/&6] &f/message/"));

        //JSON Hover Settings
        jsonHover = conf.getStringList("json.sender-json");
        StringBuilder builder = new StringBuilder();
        for(String s : jsonHover)
        {
            builder.append(s + "\n");
        }
        jsonMessage = ChatColor.translateAlternateColorCodes('&', builder.toString().trim());
        clickEvent = conf.getString("json.sender-click-event");
        jsonParameter = conf.getString("json.click-parameter");

        //Language
        prefix = ChatColor.translateAlternateColorCodes('&', conf.getString("language.plugin-prefix", "&8[&2Chat&8] &7&o"));
        oocOn = ChatColor.translateAlternateColorCodes('&', conf.getString("language.ooc-on", "Default chat is now Out Of Character"));
        oocOff = ChatColor.translateAlternateColorCodes('&', conf.getString("language.ooc-off", "Default chat is now Roleplay Chat"));
        permissionDenied = ChatColor.translateAlternateColorCodes('&', conf.getString("language.permission.message", "&cSorry, you do not have permission to use /denied/."));
        command = ChatColor.translateAlternateColorCodes('&', conf.getString("language.permission.command", "that command"));
        color = ChatColor.translateAlternateColorCodes('&', conf.getString("language.permission.color", "colored chat"));
        socialSpy = ChatColor.translateAlternateColorCodes('&', conf.getString("language.socialspy.message", "Socialspy has been toggled /mode/."));
        on = ChatColor.translateAlternateColorCodes('&', conf.getString("language.socialspy.on", "on"));
        off = ChatColor.translateAlternateColorCodes('&', conf.getString("language.socialspy.off", "off"));
        invalidCommand = ChatColor.translateAlternateColorCodes('&', conf.getString("language.invalid-command", "&cInvalid usage! &7The correct usage is /usage/"));
        playerNotFound = ChatColor.translateAlternateColorCodes('&', conf.getString("language.player-not-found", "&cThat player was not found."));
        firstJoin = ChatColor.translateAlternateColorCodes('&', conf.getString("messages.first-join", "&6Welcome &7/player/ &6to the server!"));
        join = ChatColor.translateAlternateColorCodes('&', conf.getString("messages.join", "&e/player/ joined the game"));
        leave = ChatColor.translateAlternateColorCodes('&', conf.getString("messages.leave", "&e/player/ left the game"));
        mutedMessage = ChatColor.translateAlternateColorCodes('&', conf.getString("language.muted-response", "&7Your message wasn't heard, as you are muted."));

    }
}

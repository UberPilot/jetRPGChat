package org.projpi.jetSuite.rpg.chat.settings;

import org.projpi.jetSuite.rpg.chat.JetRPGChat;

import java.util.logging.Level;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class ChatType
{
    public static ChatType CHAT;
    public static ChatType SHOUT;
    public static ChatType EMOTE;
    public static ChatType OOC;

    private String prefix, format;
    private boolean use, requireTail;
    private int range, farRange;

    private ChatType(){}

    public static void load()
    {
        CHAT = new ChatType();
        SHOUT = new ChatType();
        EMOTE = new ChatType();
        OOC = new ChatType();

        if(JetRPGChat.getConfigVersion() == 1)
        {
            //Load 'Chat' Settings
            CHAT.prefix = JetRPGChat.getInstance().getConfig().getString("type-settings.chat.prefix", "");
            CHAT.format = JetRPGChat.getInstance().getConfig().getString("type-settings.chat.format", "/sender/&7 says &f/message/");
            CHAT.use = JetRPGChat.getInstance().getConfig().getBoolean("type-settings.chat.use", true);
            CHAT.requireTail = JetRPGChat.getInstance().getConfig().getBoolean("type-settings.chat.requireTail", false);
            CHAT.range = JetRPGChat.getInstance().getConfig().getInt("type-settings.chat.range", 25);
            CHAT.farRange = JetRPGChat.getInstance().getConfig().getInt("type-settings.chat.far-range", 25);

            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Chat Settings Loaded: ");
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Prefix: " + CHAT.prefix);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Format: " + CHAT.format);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Using: " + CHAT.use);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Range: " + CHAT.range);

            //Load 'Shout' Settings
            SHOUT.prefix = JetRPGChat.getInstance().getConfig().getString("type-settings.shout.prefix", "!");
            SHOUT.format = JetRPGChat.getInstance().getConfig().getString("type-settings.shout.format", "/sender/&7 yells &f/message/");
            SHOUT.use = JetRPGChat.getInstance().getConfig().getBoolean("type-settings.shout.use", true);
            SHOUT.requireTail = JetRPGChat.getInstance().getConfig().getBoolean("type-settings.shout.requireTail", false);
            SHOUT.range = JetRPGChat.getInstance().getConfig().getInt("type-settings.shout.range", 50);
            SHOUT.farRange = JetRPGChat.getInstance().getConfig().getInt("type-settings.shout.far-range", 70);
            
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Shout Settings Loaded: ");
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Prefix: " + SHOUT.prefix);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Format: " + SHOUT.format);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Using: " + SHOUT.use);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Range: " + SHOUT.range);

            //Load 'Emote' Settings
            EMOTE.prefix = JetRPGChat.getInstance().getConfig().getString("type-settings.emote.prefix", "*");
            EMOTE.format = JetRPGChat.getInstance().getConfig().getString("type-settings.emote.format", "/sender/&7 yells &f/message/");
            EMOTE.use = JetRPGChat.getInstance().getConfig().getBoolean("type-settings.emote.use", true);
            EMOTE.requireTail = JetRPGChat.getInstance().getConfig().getBoolean("type-settings.emote.requireTail", false);
            EMOTE.range = JetRPGChat.getInstance().getConfig().getInt("type-settings.emote.range", 25);
            EMOTE.farRange = JetRPGChat.getInstance().getConfig().getInt("type-settings.emote.far-range", 25);
            
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Emote Settings Loaded: ");
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Prefix: " + EMOTE.prefix);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Format: " + EMOTE.format);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Using: " + EMOTE.use);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Range: " + EMOTE.range);

            //Load 'OOC' Settings
            OOC.prefix = JetRPGChat.getInstance().getConfig().getString("type-settings.ooc.prefix", "^");
            OOC.format = JetRPGChat.getInstance().getConfig().getString("type-settings.ooc.format", "&f</sender/&f> /message/");
            OOC.use = JetRPGChat.getInstance().getConfig().getBoolean("type-settings.ooc.use", true);
            OOC.requireTail = JetRPGChat.getInstance().getConfig().getBoolean("type-settings.ooc.requireTail", false);
            OOC.range = JetRPGChat.getInstance().getConfig().getInt("type-settings.ooc.range", 0);
            OOC.farRange = JetRPGChat.getInstance().getConfig().getInt("type-settings.ooc.far-range", 0);

            JetRPGChat.getInstance().getLogger().log(Level.INFO, "OOC Settings Loaded: ");
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Prefix: " + OOC.prefix);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Format: " + OOC.format);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Using: " + OOC.use);
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Range: " + OOC.range);
        }
    }



    public String getPrefix()
    {
        return prefix;
    }

    public String getFormat()
    {
        return format;
    }

    public boolean use()
    {
        return use;
    }

    public boolean requiresTail()
    {
        return requireTail;
    }

    public int getRange()
    {
        return range;
    }

    public int getFarRange()
    {
        return farRange;
    }
}

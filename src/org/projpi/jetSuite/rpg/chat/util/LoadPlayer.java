package org.projpi.jetSuite.rpg.chat.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.projpi.jetSuite.rpg.chat.JetRPGChat;
import org.projpi.jetSuite.rpg.chat.settings.Values;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class LoadPlayer implements Listener
{

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerJoin(PlayerJoinEvent e)
    {
        YamlConfiguration conf = JetRPGChat.getInstance().getData();
        if(conf.isSet(e.getPlayer().getUniqueId().toString()))
        {
            if(conf.getBoolean(e.getPlayer().getUniqueId().toString() + ".firstJoin"))
            {
                Bukkit.broadcastMessage(Values.firstJoin.replaceAll("/player/", e.getPlayer().getName()));
                conf.set(e.getPlayer().getUniqueId().toString() + ".firstJoin", false);
            }
            if(conf.getBoolean(e.getPlayer().getUniqueId().toString() + ".muted")) JetRPGChat.muted.add(e.getPlayer().getUniqueId().toString());
            if(conf.getBoolean(e.getPlayer().getUniqueId().toString() + ".spying")) JetRPGChat.spies.add(e.getPlayer().getUniqueId().toString());
            if(conf.getBoolean(e.getPlayer().getUniqueId().toString() + ".ooc")) JetRPGChat.prefs.put(e.getPlayer().getUniqueId().toString(), true);
            else JetRPGChat.prefs.put(e.getPlayer().getUniqueId().toString(), false);
        }
        else
        {
            JetRPGChat.prefs.put(e.getPlayer().getUniqueId().toString(), false);
            savePlayer(e.getPlayer());
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Values.firstJoin.replaceAll("/player/", e.getPlayer().getName())));
        }
        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', Values.join.replaceAll("/player/", e.getPlayer().getDisplayName())));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerLeave(PlayerQuitEvent e)
    {
        YamlConfiguration conf = JetRPGChat.getInstance().getData();
        String uid = e.getPlayer().getUniqueId().toString();
        conf.set(uid + ".lastName", e.getPlayer().getName());
        conf.set(uid + ".firstJoin", false);
        conf.set(uid + ".spy", JetRPGChat.spies.contains(uid));
        conf.set(uid + ".muted", JetRPGChat.muted.contains(uid));
        conf.set(uid + ".ooc", (JetRPGChat.prefs.get(uid)));
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', Values.leave.replaceAll("/player/", e.getPlayer().getDisplayName())));
        try
        {
            conf.save(JetRPGChat.getDataFile());
        }
        catch (IOException ex)
        {
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Failed to save playerdata for UUID " + e.getPlayer().getUniqueId().toString());
        }
    }

    public static void savePlayer(Player p)
    {

        YamlConfiguration conf = JetRPGChat.getInstance().getData();
        String uid = p.getUniqueId().toString();
        conf.set(uid + ".firstJoin", false);
        conf.set(uid + ".spy", JetRPGChat.spies.contains(uid));
        conf.set(uid + ".muted", JetRPGChat.muted.contains(uid));
        conf.set(uid + ".ooc", (JetRPGChat.prefs.get(uid)));
        try
        {
            conf.save(JetRPGChat.getDataFile());
        }
        catch (IOException ex)
        {
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "Failed to save playerdata for UUID " + p.getUniqueId().toString());
        }
    }
}

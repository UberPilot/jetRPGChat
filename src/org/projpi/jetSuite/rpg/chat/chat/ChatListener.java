package org.projpi.jetSuite.rpg.chat.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.projpi.jetSuite.rpg.chat.JetRPGChat;
import org.projpi.jetSuite.rpg.chat.settings.ChatType;
import org.projpi.jetSuite.rpg.chat.settings.Values;
import org.projpi.jetSuite.rpg.chat.util.MessageUtils;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class ChatListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatEvent(AsyncPlayerChatEvent event)
    {
        Player sender = event.getPlayer();
        String message = event.getMessage();

        if(JetRPGChat.muted.contains(event.getPlayer().getUniqueId().toString()))
        {
            event.getPlayer().sendMessage(Values.prefix + Values.mutedMessage);
            Bukkit.getConsoleSender().sendMessage("[Muted] " + sender.getName() + " " + message);
            return;
        }

        ChatType normal = (JetRPGChat.prefs.get(sender.getUniqueId().toString())) ? ChatType.OOC : ChatType.CHAT;
        if(message.startsWith(ChatType.EMOTE.getPrefix()) && !ChatType.EMOTE.getPrefix().equals("") && ChatType.EMOTE.use())
        {
            send(ChatType.EMOTE, sender, message.substring(1));
            Bukkit.getConsoleSender().sendMessage("[Emote] " + sender.getName() + " " + message.substring(1));
        }
        else if(message.startsWith(ChatType.SHOUT.getPrefix()) && !ChatType.SHOUT.getPrefix().equals("") && ChatType.SHOUT.use())
        {
            send(ChatType.SHOUT, sender, message.substring(1));
            Bukkit.getConsoleSender().sendMessage("[Shout] " + sender.getName() + " " + message.substring(1));
        }
        else if(message.startsWith(ChatType.OOC.getPrefix()) && !ChatType.OOC.getPrefix().equals("") && ChatType.OOC.use())
        {
            send(ChatType.OOC, sender, message.substring(1));
            Bukkit.getConsoleSender().sendMessage("[OOC] " + sender.getName() + " " + message.substring(1));
        }
        else if(message.startsWith(ChatType.CHAT.getPrefix()) && !ChatType.CHAT.getPrefix().equals("") && ChatType.CHAT.use())
        {
            send(ChatType.CHAT, sender, message.substring(1));
            Bukkit.getConsoleSender().sendMessage("[Chat] " + sender.getName() + " " + message.substring(1));
        }
        else
        {
            send(normal, sender, message);
            Bukkit.getConsoleSender().sendMessage((normal == ChatType.CHAT) ? "[Chat] " : "[OOC] " + sender.getName() + " " + message);
        }
        event.setCancelled(true);
    }

    //To Build Message:
    //Split into Prefix and Message
    //Parse Placeholders in Prefix
    //Parse Colors in Message
    //Build Text Components

    private void send(ChatType type, Player sender, String message)
    {
        TextComponent fullMessage = buildFullMessage(sender, message, type);
        if(type.getRange() == 0)
        {
            Bukkit.spigot().broadcast(fullMessage);
        }
        else
        {
            ArrayList<Player> closePlayers = new ArrayList<>();
            ArrayList<Player> farPlayers = new ArrayList<>();
            for (Entity e : sender.getNearbyEntities(type.getRange(), type.getRange(), type.getRange()))
            {
                if (e instanceof Player)
                {
                    closePlayers.add((Player) e);
                }
            }
            for(Player p : closePlayers)
            {
                p.spigot().sendMessage(fullMessage);
            }
            sender.spigot().sendMessage(fullMessage);
            if(type.getFarRange() > type.getRange())
            {
                for(Entity e : sender.getNearbyEntities(type.getFarRange(), type.getFarRange(), type.getFarRange()))
                {
                    if(e instanceof Player && !closePlayers.contains(e))
                    {
                        TextComponent distorted = buildFullMessage(sender, MessageUtils.distort(message, (int) e.getLocation().distance(sender.getLocation()), type.getFarRange()), type);
                        ((Player) e).spigot().sendMessage(distorted);
                    }
                }
            }
            closePlayers.clear();
            farPlayers.clear();
            closePlayers = null;
            farPlayers = null;
            for(String s : JetRPGChat.spies)
            {
                Bukkit.getPlayer(UUID.fromString(s)).spigot().sendMessage(fullMessage);

            }
            fullMessage = null;
        }
    }

    private TextComponent buildFullMessage(Player sender, String message, ChatType ct)
    {
        message = ChatColor.stripColor(message);
        String prefix = ct.getFormat().replaceAll("/message/", "").replaceAll("/dsender/", sender.getDisplayName()).replaceAll("/sender/", sender.getName());
        if(JetRPGChat.isPlaceholders())
        {
            prefix = PlaceholderAPI.setPlaceholders(sender, prefix);
        }
        TextComponent pre = new TextComponent(TextComponent.fromLegacyText(prefix));
        if(Values.jsonHover.size() > 0)
        {
            if(JetRPGChat.isPlaceholders())
            {
                pre.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(PlaceholderAPI.setPlaceholders(sender, Values.jsonMessage)).create()));
            }
            else
            {
                pre.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Values.jsonMessage).create()));
            }
        }
        if(Values.jsonParameter != null && Values.clickEvent != null)
        {
            pre.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(Values.clickEvent.toUpperCase()), Values.jsonParameter.replaceAll("/dsender/", sender.getDisplayName()).replaceAll("/sender/", sender.getName())));
        }
        if(Values.parseColor)
        {
            if(Values.permissionColor)
            {
                if(sender.hasPermission("jetRPG.chat.color"))
                {
                    message = ChatColor.translateAlternateColorCodes('&', message);
                }
                else
                {
                    if(Values.deniedColor)
                    {
                        sender.sendMessage(Values.permissionDenied.replaceAll("/denied/",  Values.color));
                    }
                }
            }
            else
            {
                message = ChatColor.translateAlternateColorCodes('&', message);
            }
        }

        TextComponent full = new TextComponent(pre.duplicate());
        full.addExtra(new TextComponent(TextComponent.fromLegacyText(message)));
        return full;
    }
}

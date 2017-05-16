package org.projpi.jetSuite.rpg.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.projpi.jetSuite.rpg.chat.JetRPGChat;
import org.projpi.jetSuite.rpg.chat.settings.Values;

import java.util.UUID;
import java.util.logging.Level;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class PrivateMessage implements CommandExecutor
{
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        if(args.length > 1)
        {
            if(commandSender.hasPermission("jetRPG.chat.message"))
            {
                if(Bukkit.getPlayer(args[0]) != null)
                {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < args.length; i++){sb.append(args[i]).append(" ");}
                    String message = sb.toString().trim();

                    Player target = Bukkit.getPlayer(args[0]);
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', Values.pmFormatFrom.replaceAll("/sender/", commandSender.getName()).replaceAll("/message/", message)));
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Values.pmFormatTo.replaceAll("/target/", target.getName()).replaceAll("/message/", message)));
                    for(String s : JetRPGChat.spies)
                    {
                        Bukkit.getPlayer(UUID.fromString(s)).sendMessage(Values.pmFormatSpy.replaceAll("/target/", target.getName()).replaceAll("/sender/", commandSender.getName()).replaceAll("/message/", message));
                    }
                    JetRPGChat.getInstance().getLogger().log(Level.INFO, commandSender.getName() + " messaged " + target.getName() + ": " + message);
                }
                else
                {
                    commandSender.sendMessage(Values.prefix + Values.playerNotFound);
                }
            }
            else
            {
                commandSender.sendMessage(Values.prefix + Values.permissionDenied.replaceAll("/denied/", Values.command));
            }
        }
        else
        {
            commandSender.sendMessage(Values.prefix + Values.invalidCommand.replaceAll("/usage/", "/" + label + " [player] [message]"));
        }
        return true;
    }
}

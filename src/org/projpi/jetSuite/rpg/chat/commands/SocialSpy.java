package org.projpi.jetSuite.rpg.chat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.projpi.jetSuite.rpg.chat.JetRPGChat;
import org.projpi.jetSuite.rpg.chat.settings.Values;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class SocialSpy implements CommandExecutor
{
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        if(commandSender instanceof Player)
        {

            if (commandSender.hasPermission("jetRPG.chat.spy"))
            {
                if(args.length < 1)
                {
                    if (JetRPGChat.spies.contains(((Player) commandSender).getUniqueId().toString()))
                    {
                        JetRPGChat.spies.remove(((Player) commandSender).getUniqueId().toString());
                        commandSender.sendMessage(Values.prefix + Values.socialSpy.replaceAll("/mode/", Values.off));
                    } else
                    {
                        JetRPGChat.spies.add(((Player) commandSender).getUniqueId().toString());
                        commandSender.sendMessage(Values.prefix + Values.socialSpy.replaceAll("/mode/", Values.on));
                    }
                }
                else
                {

                    if(args[0].equalsIgnoreCase("off"))
                    {
                        JetRPGChat.spies.remove(((Player) commandSender).getUniqueId().toString());
                        commandSender.sendMessage(Values.prefix + Values.socialSpy.replaceAll("/mode/", Values.off));
                    }
                    else if(args[0].equalsIgnoreCase("on"))
                    {
                        JetRPGChat.spies.add(((Player) commandSender).getUniqueId().toString());
                        commandSender.sendMessage(Values.prefix + Values.socialSpy.replaceAll("/mode/", Values.on));
                    }
                    else
                    {
                        commandSender.sendMessage(Values.prefix + Values.invalidCommand.replaceAll("/usage/", "/" + label + " <on | off>"));
                    }

                }
            }
            else
            {
                commandSender.sendMessage(Values.prefix + Values.permissionDenied.replaceAll("/denied/", Values.command));
            }

        }
        else
        {
            commandSender.sendMessage("Silly! You already have social spy in console!");
        }
        return true;
    }
}

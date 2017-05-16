package org.projpi.jetSuite.rpg.chat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class BaseCommand implements CommandExecutor
{
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        return false;
    }
}

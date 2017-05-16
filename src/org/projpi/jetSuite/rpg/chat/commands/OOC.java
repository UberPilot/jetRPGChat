package org.projpi.jetSuite.rpg.chat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.projpi.jetSuite.rpg.chat.JetRPGChat;
import org.projpi.jetSuite.rpg.chat.settings.Values;

import java.util.logging.Level;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class OOC implements CommandExecutor
{
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        if(commandSender instanceof Player)
        {
            Player p = (Player) commandSender;
            String uid = p.getUniqueId().toString();
            if(!JetRPGChat.prefs.containsKey(uid)) JetRPGChat.prefs.put(uid, false);
            boolean ooc = !JetRPGChat.prefs.get(uid);
            JetRPGChat.prefs.put(uid, ooc);
            p.sendMessage(Values.prefix + ((ooc) ? Values.oocOn : Values.oocOff));
            p = null;
            uid = null;
        }
        else
        {
            JetRPGChat.getInstance().getLogger().log(Level.INFO, "That command can't be used from the console.");
        }
        return true;
    }
}

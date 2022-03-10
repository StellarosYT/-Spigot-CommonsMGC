package net.mythigame.commonsMGC.Zeus.commands.Utiles;

import net.mythigame.commons.Account;
import net.mythigame.commons.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class broadcastCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)){
            return true;
        }

        Account account = new Account().getAccount(player.getUniqueId());
        if(account == null){
            player.sendMessage("§9[Zeus] Votre compte est introuvable !");
            return true;
        }

        if(account.getGrade().getPower() < RankUnit.MODERATEUR.getPower()){
            player.sendMessage("§9[Zeus] Vous n'avez pas la permission d'utiliser cette commande !");
            return true;
        }

        if(args.length == 0){
            player.sendMessage("§9[Zeus] Veuillez préciser un message.");
        }

        StringBuilder message = new StringBuilder();
        for(String part : args){
            if(!message.toString().equals(""))
                message.append(" ");

            message.append(part);
        }

        Bukkit.getServer().broadcastMessage("§9[MGC] " + message);

        return true;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§9[Zeus] Usage : /broadcast <message>");
    }

    List<String> arguments = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            arguments.clear();
            arguments.add("message");
        }else arguments.clear();
        return arguments;
    }
}

package net.mythigame.commonsMGC.Kronos.commands;

import net.mythigame.commons.Account;
import net.mythigame.commons.RankUnit;
import net.mythigame.commonsMGC.CommonsMGC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static net.mythigame.commonsMGC.Kronos.managers.ItemManager.createItem;

public class modCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player){
            Account account = new Account().getAccount(player.getUniqueId());

            if(account != null){
                if(account.getGrade().getPower() < RankUnit.MODERATEUR.getPower()){
                    player.sendMessage("§c[Kronos] Vous n'avez pas la permission d'utiliser cette commande.");
                    return true;
                }
                if(isModeration(player)){
                    account.setModeration(false);

                    giveInventory(player);

                    Modmoderations.remove(player);

                    player.setInvulnerable(false);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.closeInventory();
                    player.setCanPickupItems(true);
                    List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                    list.remove(player);
                    list.forEach(p -> p.showPlayer(CommonsMGC.getInstance(), player));
                    account.setVanished(false);

                    account.update();
                    player.sendMessage("§c[Kronos] Vous n'êtes plus en mode modération.");
                }else{
                    saveInventory(player);
                    getModInventory(player);
                    account.setModeration(true);

                    Modmoderations.add(player);

                    player.setInvulnerable(true);
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.closeInventory();
                    player.setCanPickupItems(false);
                    List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                    list.remove(player);
                    list.forEach(p -> p.hidePlayer(CommonsMGC.getInstance(), player));
                    account.setVanished(true);

                    account.update();
                    player.sendMessage("§c[Kronos] Vous êtes désormais en mode modération.");
                }
            }
        }

        return true;
    }

    public void leave(Player player){
        giveInventory(player);

        player.setInvulnerable(false);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.closeInventory();
        player.setCanPickupItems(true);

        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        list.remove(player);
        list.forEach(p -> p.showPlayer(CommonsMGC.getInstance(), player));
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§c[Kronos] Usage : /ban <player> <time> <timeKey> <reason>");
    }

    private final ItemStack[] items = new ItemStack[40];

    public ItemStack[] getItems(Player player) {
        return items;
    }

    public void saveInventory(Player player){
        for(int slot = 0; slot < 36; slot++){
            ItemStack itemStack = player.getInventory().getItem(slot);
            if(itemStack != null){
                items[slot] = itemStack;
            }
        }

        items[36] = player.getInventory().getHelmet();
        items[37] = player.getInventory().getChestplate();
        items[38] = player.getInventory().getLeggings();
        items[39] = player.getInventory().getBoots();

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public void giveInventory(Player player){
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        for(int slot = 0; slot < 36; slot++){
            ItemStack itemStack = getItems(player)[slot];
            if(itemStack != null){
                player.getInventory().setItem(slot, itemStack);
            }
        }

        player.getInventory().setHelmet(items[36]);
        player.getInventory().setChestplate(items[37]);
        player.getInventory().setLeggings(items[38]);
        player.getInventory().setBoots(items[39]);
    }

    private void getModInventory(Player player){
        ItemStack invsee = createItem(Material.PAPER, "§cVoir l'inventaire");
        ItemStack kbtester = createItem(Material.STICK, "§cKnockback", Enchantment.KNOCKBACK, 5, true);
        ItemStack reports = createItem(Material.COMPASS, "§cSignalements");
        ItemStack freeze = createItem(Material.PACKED_ICE, "§cFreeze");
        ItemStack reportPlayer = createItem(Material.BOOK, "§cVoir les signalements");
        ItemStack ck = createItem(Material.BLAZE_ROD, "§cCK");
        ItemStack randomTp = createItem(Material.ARROW, "§cTP Aléatoire");
        ItemStack vanish = createItem(Material.MAGMA_CREAM, "§cVanish");
        ItemStack applyMod = createItem(Material.ENDER_CHEST, "§cMenu de modération");

        player.getInventory().setItem(0, invsee);
        player.getInventory().setItem(1, kbtester);
        player.getInventory().setItem(2, reports);
        player.getInventory().setItem(3, freeze);
        player.getInventory().setItem(4, reportPlayer);
        player.getInventory().setItem(5, ck);
        player.getInventory().setItem(6, randomTp);
        player.getInventory().setItem(7, vanish);
        player.getInventory().setItem(8, applyMod);
    }

    public static List<Player> Modmoderations = new ArrayList<>();

    public static boolean isModeration(Player player){
        return Modmoderations.contains(player);
    }

    List<String> arguments = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            arguments.clear();
            Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
            Bukkit.getOnlinePlayers().toArray(players);
            for (Player player : players) {
                arguments.add(player.getName());
            }
        }else arguments.clear();
        return arguments;
    }
}

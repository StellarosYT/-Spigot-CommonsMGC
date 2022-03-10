package net.mythigame.commonsMGC.Kronos.listeners;

import net.mythigame.commons.Account;
import net.mythigame.commons.AccountCase;
import net.mythigame.commonsMGC.CommonsMGC;
import net.mythigame.commonsMGC.Kronos.commands.modCommand;
import net.mythigame.commonsMGC.Kronos.managers.ReportManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.mythigame.commonsMGC.Kronos.commands.modCommand.Modmoderations;
import static net.mythigame.commonsMGC.Kronos.commands.modCommand.isModeration;
import static net.mythigame.commonsMGC.Kronos.managers.ItemManager.createItem;

public class ModItemsInteract implements Listener {

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        if(!isModeration(player)) return;
        if(!event.getHand().equals(EquipmentSlot.HAND)) return;
        if(!(event.getRightClicked() instanceof Player target)) return;

        event.setCancelled(true);

        switch (player.getInventory().getItemInMainHand().getType()){
            case PAPER -> {
                Inventory inventory = Bukkit.createInventory(null, 5*9, "§cInventaire >> " + ChatColor.RED + target.getName());

                for(int i = 0; i < 36; i++){
                    if(target.getInventory().getItem(i) != null){
                        inventory.setItem(i, target.getInventory().getItem(i));
                    }
                }

                inventory.setItem(36, createItem(target, target.getName()));
                inventory.setItem(37, createItem(Material.APPLE, "§cVie : " + target.getHealth() + "/20"));
                inventory.setItem(38, createItem(Material.BREAD, "§cNourriture : " + target.getFoodLevel() + "/20"));
                List<String> lore = new ArrayList<>();
                for (PotionEffect effect : target.getActivePotionEffects()){
                    lore.add(effect.getType().getName() + (effect.getAmplifier() != 0 ? " " + effect.getAmplifier() + " : " : ": ") + effect.getDuration());
                }
                ItemStack effects = createItem(Material.BLAZE_POWDER, "§cEffets : ", lore);
                inventory.setItem(39, effects);

                inventory.setItem(41, target.getInventory().getHelmet());
                inventory.setItem(42, target.getInventory().getChestplate());
                inventory.setItem(43, target.getInventory().getLeggings());
                inventory.setItem(44, target.getInventory().getBoots());

                player.openInventory(inventory);
                break;
            }
            case PACKED_ICE -> {
                Account targetAccount = new Account().getAccount(target.getUniqueId());
                if(targetAccount == null) return;
                if(targetAccount.isFreeze()){
                    target.setCanPickupItems(true);
                    targetAccount.setFreeze(false);
                    targetAccount.update();
                    player.sendMessage("§c[Kronos] Vous avez bien unfreeze " + target.getName());
                    target.sendMessage("§c[Kronos] Vous avez été unfreeze.");
                }else{
                    target.setCanPickupItems(false);
                    targetAccount.setFreeze(true);
                    targetAccount.update();
                    player.sendMessage("§c[Kronos] Vous avez bien freeze " + target.getName());
                    target.sendMessage("§c[Kronos] Vous avez été freeze ! Veuillez vous rendre sur le discord : https://discord.gg/Txe9z6T");
                }
            }
            case BOOK -> {
                Inventory inventory = Bukkit.createInventory(null, 1*9, "§cSignalements >> " + ChatColor.RED + target.getName());
                Account targetAccount = new Account().getAccount(target.getUniqueId());
                if(targetAccount == null) return;
                List<AccountCase> reports = new ReportManager().getReports(targetAccount);
                if(reports.isEmpty()){
                    player.sendMessage("§c[Kronos] Ce joueur n'a aucun signalement.");
                    return;
                }
                inventory.setItem(0, createItem(target, ChatColor.RED + target.getName()));
                inventory.setItem(8, createItem(Material.BARRIER, "§cX"));
                for (AccountCase report : reports){
                    List<String> lore = new ArrayList<>();
                    inventory.addItem(createItem(Material.PAPER, report.getId(), lore));
                }
                player.openInventory(inventory);
                break;
            }
            case BLAZE_ROD -> {
                target.setHealth(0);
                Bukkit.getWorld(target.getWorld().getUID()).strikeLightningEffect(target.getLocation());
                Bukkit.getWorld(target.getWorld().getUID()).playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 0);
                break;
            }
            case ENDER_CHEST -> {
                // TODO: Menu modération
                player.sendMessage("Todo menu modération.");
                break;
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Account account = new Account().getAccount(player.getUniqueId());
        if(account == null) return;
        if(!isModeration(player)) return;

        switch (player.getInventory().getItemInMainHand().getType()){
            case COMPASS -> {
                Inventory inventory = Bukkit.createInventory(null, 5*9, "§cListe des Signalements");
                List<AccountCase> reports = new ReportManager().getReports();
                if(reports.isEmpty()){
                    player.sendMessage("§c[Kronos] Il n'y a aucun signalement.");
                    return;
                }
                inventory.setItem(44, createItem(Material.BARRIER, "§cX"));
                for (AccountCase report : reports){
                    List<String> lore = new ArrayList<>();
                    inventory.addItem(createItem(Material.PAPER, report.getId(), lore));
                }
                player.openInventory(inventory);
                break;
            }
            case ARROW -> {
                List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                list.remove(player);

                if(list.size() == 0){
                    player.sendMessage("§c[Kronos] Il n'y a aucun joueur sur lequel vous téléporter");
                }else{
                    Player target = list.get(new Random().nextInt(list.size()));
                    player.teleport(target.getLocation());
                    player.sendMessage("§c[Kronos] Vous avez été téléporté à " + target.getName());
                }
                break;
            }
            case SLIME_BALL -> {
                if(!account.isVanished()){
                    List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                    list.remove(player);
                    account.setVanished(true);
                    account.update();
                    list.forEach(p -> p.hidePlayer(CommonsMGC.getInstance(), player));
                    player.sendMessage("§c[Kronos] Vous êtes désormais invisible.");
                    if(event.getItem() != null){
                        ItemMeta itemMeta = event.getItem().getItemMeta();
                        assert itemMeta != null;
                        itemMeta.setDisplayName("§cVanish [§2Actif§c]");
                        event.getItem().setItemMeta(itemMeta);
                        event.getItem().setType(Material.MAGMA_CREAM);
                    }
                }
                break;
            }
            case MAGMA_CREAM -> {
                if(account.isVanished()){
                    List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                    list.remove(player);
                    account.setVanished(false);
                    account.update();
                    list.forEach(p -> p.showPlayer(CommonsMGC.getInstance(), player));
                    player.sendMessage("§c[Kronos] Vous êtes désormais visible.");
                    if(event.getItem() != null){
                        ItemMeta itemMeta = event.getItem().getItemMeta();
                        assert itemMeta != null;
                        itemMeta.setDisplayName("§cVanish [§4Inactif§c]");
                        event.getItem().setItemMeta(itemMeta);
                        event.getItem().setType(Material.SLIME_BALL);
                    }
                }
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        for(Player players : Bukkit.getOnlinePlayers()){
            Account account = new Account().getAccount(players.getUniqueId());
            if(account.isVanished()){
                player.hidePlayer(CommonsMGC.getInstance(), players);
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(Modmoderations.contains(player)){
            event.setQuitMessage(null);
            modCommand modCommand = new modCommand();
            modCommand.leave(player);
        }
    }

}

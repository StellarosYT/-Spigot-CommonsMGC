package net.mythigame.commonsMGC.Kronos.listeners;

import net.mythigame.commons.Account;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static net.mythigame.commonsMGC.Kronos.commands.modCommand.isModeration;

public class ModCancels implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        Account targetAccount = new Account().getAccount(event.getPlayer().getUniqueId());
        if(isModeration(event.getPlayer()) || targetAccount.isFreeze()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Account targetAccount = new Account().getAccount(event.getPlayer().getUniqueId());
        if(isModeration(event.getPlayer()) || targetAccount.isFreeze()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBloackBreak(BlockBreakEvent event){
        Account targetAccount = new Account().getAccount(event.getPlayer().getUniqueId());
        if(isModeration(event.getPlayer()) || targetAccount.isFreeze()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Account targetAccount = new Account().getAccount(event.getEntity().getUniqueId());
        if(isModeration((Player) event.getEntity()) || targetAccount.isFreeze()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Account targetAccount = new Account().getAccount(event.getPlayer().getUniqueId());
        if(isModeration(event.getPlayer()) || targetAccount.isFreeze()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Account targetAccount = new Account().getAccount(event.getWhoClicked().getUniqueId());
        if(isModeration((Player) event.getWhoClicked()) || targetAccount.isFreeze()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getDamager() instanceof Player)) return;
        if(isModeration((Player) event.getDamager())){
            event.setCancelled(((Player) event.getDamager()).getInventory().getItemInMainHand().getType() != Material.STICK);
        }
        Account entityAccount = new Account().getAccount(event.getEntity().getUniqueId());
        Account damagerAccount = new Account().getAccount(event.getDamager().getUniqueId());
        if(damagerAccount.isFreeze() || entityAccount.isFreeze()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Account targetAccount = new Account().getAccount(event.getPlayer().getUniqueId());
        if(targetAccount.isFreeze()){
            event.setTo(event.getFrom());
        }
    }
}

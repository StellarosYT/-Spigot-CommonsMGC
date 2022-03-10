package net.mythigame.commonsMGC.Kronos.listeners;

import net.mythigame.commons.Account;
import net.mythigame.commons.AccountCase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static net.mythigame.commons.Utils.TimeUnit.getTimeLeft;

public class MuteListener implements Listener {

    @EventHandler
    public void onPlayerChatAsync(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        Account account = new Account().getAccount(player.getUniqueId());
        AccountCase accountCase = account.getCaseById(account.getMute_id());
        if(account.isMuted()){
            checkDuration(account, accountCase);
            if(account.isMuted()){
                event.setCancelled(true);
                if(getTimeLeft(accountCase).equalsIgnoreCase("permanent")){
                    player.sendMessage("§c[Kronos] Vous êtes réduit au silence pour " + accountCase.getReason() + " ! (Permanent)");
                }else{
                    player.sendMessage("§c[Kronos] Vous êtes réduit au silence pour " + accountCase.getReason() + " ! (Temps restant : " + getTimeLeft(accountCase) + ")");
                }
            }
        }
    }

    private void checkDuration(Account account, AccountCase accountCase){
        if(!accountCase.getType().equals("mute")) return;
        long endTime = accountCase.getEnd_time();
        if(endTime == -1 || endTime == 0) return;
        if(endTime < System.currentTimeMillis()){
            account.setMuted(false);
            account.setMute_id(null);
            account.update();
        }
    }
}

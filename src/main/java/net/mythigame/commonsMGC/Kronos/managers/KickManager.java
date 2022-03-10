package net.mythigame.commonsMGC.Kronos.managers;

import net.mythigame.commons.Account;
import net.mythigame.commons.AccountCase;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class KickManager {

    private final Account account;
    private final Player moderator;
    private final String kick_reason;

    public KickManager(Account account, Player moderator, String kick_reason) {
        this.account = account;
        this.moderator = moderator;
        this.kick_reason = kick_reason;
    }

    @SuppressWarnings("ConstantConditions")
    public void kick(){
        if(account != null){
            Player target = Bukkit.getPlayer(account.getUuid());
            final String id = account.getUuid() + ":" + moderator.getUniqueId() + ":" + System.currentTimeMillis();
            AccountCase accountCase = new AccountCase(id, account.getUuid(), "kick", kick_reason, -1, moderator.getUniqueId(), null, null);
            if(target.isDead()) target.spigot().respawn();
            Bukkit.getWorld(target.getWorld().getUID()).strikeLightningEffect(target.getLocation());
            Bukkit.getWorld(target.getWorld().getUID()).playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 0);
            account.getCases().add(accountCase);
            account.update();
            target.kickPlayer("§c[Kronos] Vous avez été expulsé !\n " +
                    "\n " +
                    "§6Raison : §f" + kick_reason
            );
            moderator.sendMessage("§c[Kronos] " + account.getUsername() + " a bien été expulsé pour : " + kick_reason);
        }else{
            moderator.sendMessage("§c[Kronos] Ce joueur ne s'est jamais connecté ou n'existe pas.");
        }
    }

}

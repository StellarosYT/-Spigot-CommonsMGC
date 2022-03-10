package net.mythigame.commonsMGC.Kronos.listeners;

import net.mythigame.commons.Account;
import net.mythigame.commons.AccountCase;
import net.mythigame.commons.RankUnit;
import net.mythigame.commonsMGC.Kronos.managers.ReportManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

import static net.mythigame.commonsMGC.Kronos.managers.ItemManager.createItem;

public class ReportInventoryClick implements Listener {

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getCurrentItem() == null) return;

        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().startsWith("§c[Kronos] Report >> ")){
            switch (event.getCurrentItem().getType()){
                case PLAYER_HEAD -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals(event.getView().getTitle().replace("§c[Kronos] Report >> ", "§c"))) return;
                    event.setCancelled(true);
                    break;
                }
                case WHITE_STAINED_GLASS_PANE -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§c>>")) return;
                    event.setCancelled(true);
                    break;
                }
                case JUNGLE_SIGN -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cAbus chat / Scam")) return;
                    event.setCancelled(true);
                    player.openInventory(getChatReportInventory(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Report >> ", ""))));
                    break;
                }
                case FEATHER -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cCheat")) return;
                    event.setCancelled(true);
                    player.openInventory(getCheatReportInventory(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Report >> ", ""))));
                    break;
                }
                case NAME_TAG -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cPseudonyme abusif")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Report >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case TOTEM_OF_UNDYING -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cNom/Tag de Guild abusif")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Report >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case EMERALD -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cBoosting")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Report >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case PAPER -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cNom d'item abusif")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Report >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case RED_STAINED_GLASS_PANE -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cX")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    break;
                }
                default -> {
                    event.setCancelled(true);
                    player.closeInventory();
                    break;
                }
            }
        }else if(event.getView().getTitle().startsWith("§c[Kronos] Chat >> ")){
            switch (event.getCurrentItem().getType()){
                case PLAYER_HEAD -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals(event.getView().getTitle().replace("§c[Kronos] Chat >> ", "§c"))) return;
                    event.setCancelled(true);
                    break;
                }
                case WHITE_STAINED_GLASS_PANE -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§c>>") || !event.getCurrentItem().getItemMeta().getDisplayName().equals("§c<<")) return;
                    event.setCancelled(true);
                    break;
                }
                case TRIDENT -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cInsultes")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Chat >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case SPECTRAL_ARROW -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cSpam")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Chat >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case MOJANG_BANNER_PATTERN -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cPublicité / Lien")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Chat >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case IRON_SWORD -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cHarcèlement")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Chat >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case BOOK -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cAutre")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Chat >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case RED_STAINED_GLASS_PANE -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cX")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    break;
                }
                default -> {
                    event.setCancelled(true);
                    player.closeInventory();
                    break;
                }
            }
        }else if(event.getView().getTitle().startsWith("§c[Kronos] Cheat >> ")){
            switch (event.getCurrentItem().getType()){
                case PLAYER_HEAD -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals(event.getView().getTitle().replace("§c[Kronos] Cheat >> ", "§c"))) return;
                    event.setCancelled(true);
                    break;
                }
                case WHITE_STAINED_GLASS_PANE -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§c>>") || !event.getCurrentItem().getItemMeta().getDisplayName().equals("§c<<")) return;
                    event.setCancelled(true);
                    break;
                }
                case IRON_SWORD -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cForceField")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Cheat >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case FEATHER -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cFly")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Cheat >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case ARROW -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cSpeedBow")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Cheat >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case IRON_SHOVEL -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cGrief")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Cheat >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case BOOK -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cAutre")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    sendToStaff(player, Bukkit.getPlayer(event.getView().getTitle().replace("§c[Kronos] Cheat >> ", "")), event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                }
                case RED_STAINED_GLASS_PANE -> {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§cX")) return;
                    event.setCancelled(true);
                    player.closeInventory();
                    break;
                }
                default -> {
                    event.setCancelled(true);
                    player.closeInventory();
                    break;
                }
            }
        }
    }

    public static Inventory getReportInventory(Player reporter, Player reported){
        Inventory inventory = Bukkit.createInventory(null, 9 * 1, "§c[Kronos] Report >> " + reported.getDisplayName());

        inventory.setItem(0, createItem(reported, "§c" + reported.getDisplayName()));
        inventory.setItem(1, createItem(Material.WHITE_STAINED_GLASS_PANE, "§c>>"));
        inventory.setItem(2, createItem(Material.JUNGLE_SIGN, "§cAbus chat / Scam"));
        inventory.setItem(3, createItem(Material.FEATHER, "§cCheat"));
        inventory.setItem(4, createItem(Material.NAME_TAG, "§cPseudonyme abusif"));
        inventory.setItem(5, createItem(Material.TOTEM_OF_UNDYING, "§cNom/Tag de Guild abusif"));
        inventory.setItem(6, createItem(Material.EMERALD, "§cBoosting"));
        inventory.setItem(7, createItem(Material.PAPER, "§cNom d'item abusif"));
        inventory.setItem(8, createItem(Material.RED_STAINED_GLASS_PANE, "§cX"));

        return inventory;
    }

    public Inventory getChatReportInventory(Player reporter, Player reported){
        Inventory inventory = Bukkit.createInventory(null, 9 * 1, "§c[Kronos] Chat >> " + reported.getDisplayName());

        inventory.setItem(0, createItem(reported, "§c" + reported.getDisplayName()));
        inventory.setItem(1, createItem(Material.WHITE_STAINED_GLASS_PANE, "§c>>"));
        inventory.setItem(2, createItem(Material.TRIDENT, "§cInsultes"));
        inventory.setItem(3, createItem(Material.SPECTRAL_ARROW, "§cSpam"));
        inventory.setItem(4, createItem(Material.MOJANG_BANNER_PATTERN, "§cPublicité / Lien"));
        inventory.setItem(5, createItem(Material.IRON_SWORD, "§cHarcèlement"));
        inventory.setItem(6, createItem(Material.BOOK, "§cAutre"));
        inventory.setItem(7, createItem(Material.WHITE_STAINED_GLASS_PANE, "§c<<"));
        inventory.setItem(8, createItem(Material.RED_STAINED_GLASS_PANE, "§cX"));

        return inventory;
    }

    public Inventory getCheatReportInventory(Player reporter, Player reported){
        Inventory inventory = Bukkit.createInventory(null, 9 * 1, "§c[Kronos] Cheat >> " + reported.getDisplayName());

        inventory.setItem(0, createItem(reported, "§c" + reported.getDisplayName()));
        inventory.setItem(1, createItem(Material.WHITE_STAINED_GLASS_PANE, "§c>>"));
        inventory.setItem(2, createItem(Material.IRON_SWORD, "§cForceField"));
        inventory.setItem(3, createItem(Material.FEATHER, "§cFly"));
        inventory.setItem(4, createItem(Material.ARROW, "§cSpeedBow"));
        inventory.setItem(5, createItem(Material.IRON_SHOVEL, "§cGrief"));
        inventory.setItem(6, createItem(Material.BOOK, "§cAutre"));
        inventory.setItem(7, createItem(Material.WHITE_STAINED_GLASS_PANE, "§c<<"));
        inventory.setItem(8, createItem(Material.RED_STAINED_GLASS_PANE, "§cX"));

        return inventory;
    }

    private void sendToStaff(Player reporter, Player reported, String reason) {
        Account reporterAccount = new Account().getAccount(reporter.getUniqueId());
        Account reportedAccount = new Account().getAccount(reported.getUniqueId());

        if(reporterAccount == null || reportedAccount == null){
            reporter.sendMessage("§c[Kronos] Une erreur est survenue. Veuillez contacter un modérateur directement.");
            return;
        }

        reporter.sendMessage("§c[Kronos] Vous avez bien signaler " + reported.getDisplayName() + " pour : " + reason);

        AccountCase accountCase = new AccountCase();
        accountCase.setId(UUID.randomUUID().toString());
        accountCase.setUuid(reported.getUniqueId());
        accountCase.setType("report");
        accountCase.setReason(reason);
        accountCase.setUuid_moderator(reporter.getUniqueId());
        accountCase.setEnd_time(-1);
        accountCase.setCancel_moderator(null);
        accountCase.setCancel_reason(null);

        ReportManager reportManager = new ReportManager();
        reportManager.addReport(accountCase);

        Bukkit.getOnlinePlayers().forEach(e -> {
            Account account = new Account().getAccount(e.getUniqueId());
            if(account.isModeration() || account.getGrade().getPower() >= RankUnit.DEVELOPPEUR.getPower()){
                e.sendMessage("§c[Kronos] " + reporter.getName() + " viens de signaler " + reported.getName() + " pour : " + reason);
            }
        });
    }

}

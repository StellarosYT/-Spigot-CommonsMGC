package net.mythigame.commonsMGC.Kronos.managers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemManager {

    public static ItemStack createItem(Material material, String name){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItem(Material material, String name, List<String> lore){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItem(Player player, String name){
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        assert itemMeta != null;
        try {
            itemMeta.setOwningPlayer(player);
        }catch (IllegalArgumentException e){
            itemMeta.setOwningPlayer(null);
        }
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItem(Material material, String name, Enchantment enchantment, int level, boolean restrict){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(name);
        itemMeta.addEnchant(enchantment, level, restrict);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}

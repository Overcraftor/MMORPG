package fr.overcraftor.mmo.utils;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material, int amount, String displayName) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
    }

    public ItemBuilder(Material material, String displayName) {
        this(material, 1, displayName);
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, "");
    }

    public ItemBuilder(Material material) {
        this(material, 1, "");
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean state) {
        itemMeta.spigot().setUnbreakable(state);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder setGlow() {
        if (itemMeta.getEnchants().isEmpty()) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addEnchant(Enchantment.OXYGEN, 0, true);
        }
        return this;
    }

    public ItemBuilder setSkullOwner(String skullOwner) {
        if (itemStack.getType().equals(Material.PLAYER_HEAD)) {
            SkullMeta skullMeta = ((SkullMeta) itemMeta);
            skullMeta.setOwner(skullOwner);
        }
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean unsafe) {
        itemMeta.addEnchant(enchantment, level, unsafe);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments, boolean unsafe) {
        for (Enchantment current : enchantments.keySet())
            itemMeta.addEnchant(current, enchantments.get(current), unsafe);
        return this;
    }

    public ItemBuilder setBannerColor(DyeColor color) {
        try {
            BannerMeta bannerMeta = ((BannerMeta) itemMeta);
            bannerMeta.setBaseColor(color);
        } catch (Exception exception) {
            System.out.println("Can't change banner color" + exception);
        }
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        try {
            LeatherArmorMeta leatherArmorMeta = ((LeatherArmorMeta) itemMeta);
            leatherArmorMeta.setColor(color);
        } catch (Exception exception) {
            System.out.println("Can't change leather color" + exception);
        }
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag itemFlag) {
        itemMeta.addItemFlags(itemFlag);
        return this;
    }

    public ItemBuilder setItemMeta(ItemMeta itemMeta) {
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack toItemStack() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
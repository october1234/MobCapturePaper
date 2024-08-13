package org.octsrv.mobcapture;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import static org.octsrv.mobcapture.Constants.*;

public class MobCapturerItem {
    public static void Capture(final Entity entity, final Player p, final ItemStack item) {
        if (!isAllowedCapture(entity.getType())) {
            return;
        }

        assert item != null;
        item.setAmount(item.getAmount() - 1);
        p.getInventory().addItem(getCapturerItemWithData(entity));

        entity.remove();
    }

    private static ItemStack getCapturerItemWithData(final Entity entity) {
        final var item = new ItemStack(Material.LEAD);
        final var meta = item.getItemMeta();
        assert meta != null;
        meta.getPersistentDataContainer().set(new NamespacedKey(MobCapture.plugin, CUSTOM_ITEM_KEY), PersistentDataType.STRING, MOB_CAPTURER_TYPE);
        meta.getPersistentDataContainer().set(new NamespacedKey(MobCapture.plugin, MOB_DATA_KEY), PersistentDataType.STRING, Utils.serialize(entity));
        meta.getPersistentDataContainer().set(new NamespacedKey(MobCapture.plugin, MOB_TYPE_KEY), PersistentDataType.STRING, entity.getType().toString());
        meta.setDisplayName("抓補器 - " + entity.getType());
        meta.setEnchantmentGlintOverride(true);
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isAllowedCapture(final EntityType entityType) {
        return true;
    }

    public static void releaseEntity(final ItemStack item, final Player p, final Location location) {
        final var meta = item.getItemMeta();
        assert meta != null;
        final var dataString = meta.getPersistentDataContainer().get(new NamespacedKey(MobCapture.plugin, MOB_DATA_KEY), PersistentDataType.STRING);
        final var entityTypeString = meta.getPersistentDataContainer().get(new NamespacedKey(MobCapture.plugin, MOB_TYPE_KEY), PersistentDataType.STRING);

        item.setAmount(item.getAmount() - 1);
        Entity entity = Utils.deserialize(dataString, p.getWorld().spawnEntity(location, EntityType.valueOf(entityTypeString)));
        entity.teleport(location);
    }

    static boolean isCapturerItem(final ItemStack item) {
        final var itemMeta = item.getItemMeta();
        if (itemMeta == null) {
          return false;
        }
        final var itemType = itemMeta.getPersistentDataContainer().get(new NamespacedKey(MobCapture.plugin, CUSTOM_ITEM_KEY), PersistentDataType.STRING);
        if (itemType == null) {
          return false;
        }
        if (itemType.isEmpty()) {
          return false;
        }
        return itemType.equals(MOB_CAPTURER_TYPE);
    }

  static boolean alreadyHasData(final ItemStack item) {
      final var itemMeta = item.getItemMeta();
      return itemMeta.getPersistentDataContainer().has(new NamespacedKey(MobCapture.plugin, MOB_DATA_KEY), PersistentDataType.STRING);
  }
}

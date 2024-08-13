package org.octsrv.mobcapture;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.Objects;

import static org.octsrv.mobcapture.Constants.CUSTOM_ITEM_KEY;

@DefaultQualifier(NonNull.class)
public final class MobCapture extends JavaPlugin {

  public static MobCapture plugin;

  @Override
  public void onEnable() {
    plugin = this;
    getServer().getPluginManager().registerEvents(new ClickListener(), this);

    Objects.requireNonNull(getCommand("getcapturer")).setExecutor((sender, c, l, a) -> {
      if (!(sender instanceof Player p)) return false;

      final var item = new ItemStack(Material.LEAD);
      final var meta = item.getItemMeta();
      assert meta != null;
      meta.getPersistentDataContainer().set(new NamespacedKey(MobCapture.plugin, CUSTOM_ITEM_KEY), PersistentDataType.STRING, Constants.MOB_CAPTURER_TYPE);
      meta.setDisplayName("抓補器 - 無生物");
      item.setItemMeta(meta);
      p.getInventory().addItem(item);
      return true;
    });
  }
  @Override
  public void onDisable() {
  }
}

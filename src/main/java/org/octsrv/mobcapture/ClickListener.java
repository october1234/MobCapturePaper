package org.octsrv.mobcapture;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ClickListener implements Listener {
    @EventHandler
    public void onRightClickOnEntity(final PlayerInteractEntityEvent e) {
        MobCapture.plugin.getLogger().info("PlayerInteractEntityEvent");

        final var p = e.getPlayer();
        final var item = e.getHand() == EquipmentSlot.HAND
            ? p.getInventory().getItemInMainHand()
            : p.getInventory().getItemInOffHand();

        if (!MobCapturerItem.isCapturerItem(item)) {
            MobCapture.plugin.getLogger().info("PlayerInteractEntityEvent, !isCapturerItem(item)");
            return;
        }
        e.setCancelled(true);
        if (MobCapturerItem.alreadyHasData(item)) {
            MobCapture.plugin.getLogger().info("PlayerInteractEntityEvent, alreadyHasData(item)");
            return;
        }
        MobCapturerItem.Capture(e.getRightClicked(), p, item);

        MobCapture.plugin.getLogger().info("PlayerInteractEntityEvent DONE!");
    }

  @EventHandler
    public void onRightClickOnBlock(final PlayerInteractEvent e) {
        final var p = e.getPlayer();
        final var item = p.getInventory().getItemInMainHand();

        if (e.getInteractionPoint() == null) {
          return;
        }
        if (!MobCapturerItem.isCapturerItem(item)) {
          return;
        }
        e.setCancelled(true);
        if (!MobCapturerItem.alreadyHasData(item)) {
          return;
        }
        MobCapturerItem.releaseEntity(item, p, e.getInteractionPoint());
    }
}


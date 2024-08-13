package org.octsrv.mobcapture;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class Utils {
  public static String serialize(final Entity entity) {
    final var nmsEntity = ((CraftEntity) entity).getHandle();
    return nmsEntity.saveWithoutId(new CompoundTag()).toString();
  }

  static Entity deserialize(String string, Entity entity) {
    final var nmsEntity = ((CraftEntity) entity).getHandle();
    try {
      nmsEntity.load(TagParser.parseTag(string));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return entity;
  }
}

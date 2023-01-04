package io.github.LavenderPerry.minecraft_battle_pass;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MinecraftBattlePass implements ModInitializer {
  public static final Item BATTLE_PASS =
    Registry.register(
      Registries.ITEM,
      new Identifier("minecraft_battle_pass", "battle_pass"),
      new Item(new FabricItemSettings())
    );

  @Override
  public void onInitialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
      content.add(BATTLE_PASS);
    });
  }
}

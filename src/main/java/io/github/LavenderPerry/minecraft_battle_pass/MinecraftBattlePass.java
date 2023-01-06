package io.github.LavenderPerry.minecraft_battle_pass;

import io.github.LavenderPerry.minecraft_battle_pass.items.BattlePassItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MinecraftBattlePass implements ModInitializer {
  public static final String MOD_ID = "minecraft_battle_pass";

  public static final Item BATTLE_PASS = Registry.register(Registries.ITEM,
      new Identifier(MOD_ID, "battle_pass"),
      new BattlePassItem(new FabricItemSettings()));

  @Override
  public void onInitialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
      content.add(BATTLE_PASS);
    });
  }
}

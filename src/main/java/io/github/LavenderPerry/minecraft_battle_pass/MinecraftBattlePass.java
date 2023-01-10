package io.github.LavenderPerry.minecraft_battle_pass;

import io.github.LavenderPerry.minecraft_battle_pass.items.BattlePassItem;
import io.github.LavenderPerry.minecraft_battle_pass.items.MomsCreditCardItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MinecraftBattlePass implements ModInitializer {
  public static final String MOD_ID = "minecraft_battle_pass";

  public static BattlePassItem BATTLE_PASS =
    new BattlePassItem(new FabricItemSettings());
  public static MomsCreditCardItem MOMS_CREDIT_CARD =
    new MomsCreditCardItem(new FabricItemSettings());

  public static Text translatableText(String keyPart) {
    return Text.translatable(translationKeyPartToKey(keyPart));
  }

  public static Text translatableText(String keyPart, Object... args) {
    return Text.translatable(translationKeyPartToKey(keyPart), args);
  }

  @Override
  public void onInitialize() {
    registerItem("battle_pass", BATTLE_PASS);
    registerItem("moms_credit_card", MOMS_CREDIT_CARD);
    // TODO: make a custom item group & add the items to that instead
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
      content.add(BATTLE_PASS);
      content.add(MOMS_CREDIT_CARD);
    });
  }

  private static void registerItem(String identifierPath, Item item) {
    Registry.register(Registries.ITEM, new Identifier(MOD_ID, identifierPath), item);
  }

  private static String translationKeyPartToKey(String keyPart) {
    return MOD_ID + "." + keyPart;
  }
}

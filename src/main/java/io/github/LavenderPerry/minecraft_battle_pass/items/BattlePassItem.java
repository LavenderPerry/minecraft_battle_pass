package io.github.LavenderPerry.minecraft_battle_pass.items;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BattlePassItem extends Item {
  private static final Item[] DROPS = {
    // TODO: Add future custom items that are made, or replace existing items with them
    Items.PAPER,
    Items.IRON_NUGGET, Items.IRON_INGOT, Items.IRON_BLOCK,
    Items.EMERALD,
    Items.GOLD_NUGGET, Items.GOLD_INGOT, Items.GOLD_BLOCK,
    Items.EMERALD, Items.SLIME_BLOCK, Items.EMERALD, Items.DIAMOND,
    Items.EMERALD, Items.NAME_TAG, Items.NETHERITE_SCRAP, Items.SADDLE,
    Items.EMERALD, Items.LEAD, Items.NETHERITE_SCRAP, Items.GOAT_HORN,
    Items.EMERALD, Items.SHULKER_SHELL, Items.NETHERITE_SCRAP, Items.END_CRYSTAL,
    Items.EMERALD, Items.ENCHANTED_GOLDEN_APPLE, Items.MOJANG_BANNER_PATTERN,
    Items.EMERALD, Items.ELYTRA, Items.NETHERITE_SCRAP, Items.NETHERITE_HOE
  };

  public BattlePassItem(Settings settings) {
    super(settings);
  }

  @Override
  public void appendTooltip(ItemStack stack,
      World world,
      List<Text> tooltip,
      TooltipContext tooltipContext) {
    NbtCompound stackNbt = stack.getOrCreateNbt();

    int level = stackNbt.getInt("level");
    int unclaimedLevels = level - stackNbt.getInt("claimedLevels");

    String translationKey = getTranslationKey();

    if (unclaimedLevels > 0) {
      tooltip.add(Text.translatable(translationKey + ".tooltip_new", unclaimedLevels)
          .formatted(Formatting.AQUA));
    }
    tooltip.add(Text.translatable(translationKey + ".tooltip_0", level));
    tooltip.add(Text.translatable(translationKey + ".tooltip_1",
          stackNbt.getInt("levelProgress"),
          getXpToNextLevel(level)));
  }

  // Claim all unclaimed levels on use
  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    ItemStack stack = user.getStackInHand(hand);

    if (world.isClient()) {
      NbtCompound stackNbt = stack.getOrCreateNbt();
      int level = stackNbt.getInt("level");
      PlayerInventory playerInventory = user.getInventory();

      for (int claimL = stackNbt.getInt("claimedLevels"); claimL < level; claimL++) {
        playerInventory.offerOrDrop(new ItemStack(
              claimL < DROPS.length ? DROPS[claimL] : Items.PAPER));
      }

      stackNbt.putInt("claimedLevels", level);
    }

    return TypedActionResult.pass(stack);
  }

  // Returns the total XP needed to level up from the current level provided
  // This is the same as Minecraft leveling
  public static int getXpToNextLevel(int currentLevel) {
    if (currentLevel < 16) {
      return 2 * currentLevel + 7;
    }
    if (currentLevel < 31) {
      return 5 * currentLevel - 38;
    }
    return 9 * currentLevel - 158;
  }
}

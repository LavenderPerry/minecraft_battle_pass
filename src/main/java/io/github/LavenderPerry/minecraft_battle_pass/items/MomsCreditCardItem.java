package io.github.LavenderPerry.minecraft_battle_pass.items;

import java.util.List;
import java.util.Random;

import io.github.LavenderPerry.minecraft_battle_pass.MinecraftBattlePass;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MomsCreditCardItem extends Item {
  private static final Item[] PURCHASE_POSSIBILITIES = {
    // TODO: add more custom items
    MinecraftBattlePass.BATTLE_PASS,
    Items.EMERALD,
    Items.PAPER
  };
  private final Random random = new Random();
  private int random_bound = 10;

  public MomsCreditCardItem(Settings settings) {
    super(settings);
  }

  @Override
  public void appendTooltip(ItemStack stack,
      World world,
      List<Text> tooltip,
      TooltipContext tooltipContext) {
    tooltip.add(Text.translatable(getTranslationKey() + ".tooltip"));
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    if (!world.isClient()) {
      switch (random.nextInt(random_bound)) {
        case 0: // Mom found out
                // TODO: summon Your Mom
          user.sendMessage(MinecraftBattlePass.translatableText("mom_found_out"));
          return TypedActionResult.pass(ItemStack.EMPTY);
        case 1: // Credit card denied
          if (random_bound > 1) {
            random_bound--;
          }
          user.sendMessage(MinecraftBattlePass.translatableText("moms_card_denied"));
          break;
        default:
          ItemStack purchasedItem = new ItemStack(
              PURCHASE_POSSIBILITIES[random.nextInt(PURCHASE_POSSIBILITIES.length)]);
          user.sendMessage(MinecraftBattlePass.translatableText("item_purchased_card",
                purchasedItem.getName()));
          user.getInventory().offerOrDrop(purchasedItem);
          break;
      }
    }

    return TypedActionResult.pass(user.getStackInHand(hand));
  }
}

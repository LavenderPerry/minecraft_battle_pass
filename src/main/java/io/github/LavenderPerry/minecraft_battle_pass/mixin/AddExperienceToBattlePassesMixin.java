package io.github.LavenderPerry.minecraft_battle_pass.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.LavenderPerry.minecraft_battle_pass.MinecraftBattlePass;
import io.github.LavenderPerry.minecraft_battle_pass.items.BattlePassItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

@Mixin(PlayerEntity.class)
public abstract class AddExperienceToBattlePassesMixin {
  @Shadow
  @Final
  private PlayerInventory inventory;

  @Inject(method = "addExperience", at = @At("HEAD"))
  protected void onAddExperience(int experience, CallbackInfo ci) {
    int battlePassesLeveledUp = 0;

    for (int i = 0; i < inventory.size(); i++) {
      ItemStack stack = inventory.getStack(i);
      if (stack.getItem().equals(MinecraftBattlePass.BATTLE_PASS)) {
        NbtCompound stackNbt = stack.getOrCreateNbt();

        int oldLevel = stackNbt.getInt("level");
        int level = oldLevel;
        int levelProgress = stackNbt.getInt("levelProgress") + experience;
        int xpToNextLevel = BattlePassItem.getXpToNextLevel(level);
        while (levelProgress >= xpToNextLevel) {
          level++;
          levelProgress -= xpToNextLevel;
          xpToNextLevel = BattlePassItem.getXpToNextLevel(level);
        }

        if (level > oldLevel) {
          stackNbt.putInt("level", level);
          battlePassesLeveledUp++;
        }
        stackNbt.putInt("levelProgress", levelProgress);
      }
    }

    if (battlePassesLeveledUp > 0) {
      ((PlayerEntity)(Object)this)
        .sendMessage(MinecraftBattlePass
            .translatableText("battle_passes_level_up", battlePassesLeveledUp));
    }
  }
}

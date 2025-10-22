package astrazoey.mattock.mixin;

import astrazoey.mattock.registry.MattockItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MattockMixin {

    @Redirect(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE", target ="Lnet/minecraft/item/ItemStack;getMiningSpeedMultiplier(Lnet/minecraft/block/BlockState;)F"))
    public float constantMiningSpeed(ItemStack instance, BlockState state) {

        float speed = instance.getMiningSpeedMultiplier(state);
        float hardness = state.getHardness(null, null);

        if(instance.isOf(MattockItems.MATTOCK)) {
            float minMiningSpeed = 4.0f;
            float weakBlockHardness = 3.0f;
            float regularSpeedModifier = 2.0f;
            float toughSpeedModifier = 6.0f;
            float toughBlockHardness = 10.0f;

            if(hardness < weakBlockHardness) {
                speed = minMiningSpeed;
            }
            else if(hardness < toughBlockHardness) {
                speed = speed * regularSpeedModifier;
            } else {
                speed = speed * toughSpeedModifier;
            }
        }

        return speed;
    }
}
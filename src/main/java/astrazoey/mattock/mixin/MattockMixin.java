package astrazoey.mattock.mixin;

import astrazoey.mattock.registry.MattockItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class MattockMixin {

    @Redirect(method = "getDestroySpeed", at = @At(value = "INVOKE", target ="Lnet/minecraft/world/item/ItemStack;getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F"))
    public float constantMiningSpeed(ItemStack instance, BlockState state) {

        float speed = instance.getDestroySpeed(state);
        float hardness = state.getDestroySpeed(null, null);

        if(instance.is(MattockItems.MATTOCK)) {
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
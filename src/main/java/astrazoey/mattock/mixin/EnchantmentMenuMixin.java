package astrazoey.mattock.mixin;

import astrazoey.mattock.registry.MattockItems;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentMenu.class)
public class EnchantmentMenuMixin {
    @Inject(method = "slotsChanged", at = @At("TAIL"))
    private void mattock$hideEmptyOffers(Container container, CallbackInfo ci) {
        EnchantmentMenu menu = (EnchantmentMenu) (Object) this;
        if (!menu.getSlot(0).getItem().is(MattockItems.MATTOCK)) {
            return;
        }

        boolean changed = false;
        for (int slot = 0; slot < menu.costs.length; slot++) {
            if (menu.costs[slot] > 0 && menu.enchantClue[slot] < 0) {
                menu.costs[slot] = 0;
                changed = true;
            }
        }
        if (changed) {
            menu.broadcastChanges();
        }
    }
}

package astrazoey.mattock.registry;

import astrazoey.mattock.Mattock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import java.util.function.Function;

public class MattockItems implements ModInitializer {

    public static final TagKey<Item> REPAIRS_MATTOCK = TagKey.of(Registries.ITEM.getKey(), Identifier.of("minecraft", "obsidian"));

    public static final ToolMaterial MATTOCK_MATERIAL = new ToolMaterial(
            BlockTags.AIR, // incorrect blocks for drops
            1775, // durability
            4.0F, // speed
            0.0F, // attack bonus
            10, // enchantability
            REPAIRS_MATTOCK //repair item
    );

    public static Item register(String name, Function<Item.Settings, Item> itemFactory,Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Mattock.MOD_ID, name));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static TagKey<Block> MATTOCK_MINEABLE = TagKey.of(RegistryKeys.BLOCK, Identifier.of("mattock", "mineable/all"));

    public static final Item MATTOCK = register(
            "mattock",
            Item::new,
            new Item.Settings().tool(
                    MATTOCK_MATERIAL, // material
                    MATTOCK_MINEABLE,
                    5.0f, // damage
                    -2.4f, // attack speed
                    0.0f //disabled blocking
                    )
    );


    // Calling a method on a class statically initializes it, meaning all static fields are evaluated
    // that's why this can be empty
    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(MattockItems.MATTOCK));
    }


    @Override
    public void onInitialize() {

    }
}
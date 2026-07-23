package astrazoey.mattock.registry;

import astrazoey.mattock.Mattock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import java.util.function.Function;

public class MattockItems implements ModInitializer {

    public static final TagKey<Item> REPAIRS_MATTOCK = TagKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath("minecraft", "obsidian"));

    public static final ToolMaterial MATTOCK_MATERIAL = new ToolMaterial(
            BlockTags.AIR, // incorrect blocks for drops
            1775, // durability
            4.0F, // speed
            0.0F, // attack bonus
            10, // enchantability
            REPAIRS_MATTOCK //repair item
    );

    public static Item register(String name, Function<Item.Properties, Item> itemFactory,Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Mattock.MOD_ID, name));

        Item item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static TagKey<Block> MATTOCK_MINEABLE = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("mattock", "mineable/all"));

    public static final Item MATTOCK = register(
            "mattock",
            Item::new,
            new Item.Properties().tool(
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
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(entries -> entries.accept(MattockItems.MATTOCK));
    }


    @Override
    public void onInitialize() {

    }
}

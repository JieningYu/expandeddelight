package com.ianm1647.expandeddelight.util;

import com.ianm1647.expandeddelight.item.ItemList;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class LootTableUtil {
    private static final Identifier DESERT_HOUSE = table(villageHouse("desert_house"));
    private static final Identifier PLAINS_HOUSE = table(villageHouse("plains_house"));
    private static final Identifier SAVANNA_HOUSE = table(villageHouse("savanna_house"));
    private static final Identifier SNOWY_HOUSE = table(villageHouse("snowy_house"));
    private static final Identifier TAIGA_HOUSE = table(villageHouse("taiga_house"));

    public static void modifyLootTables() {
        lootTable(PLAINS_HOUSE, ItemList.ASPARAGUS_SEEDS, 1f, 1.0f, 3.0f); //0.5f
        lootTable(PLAINS_HOUSE, ItemList.ASPARAGUS, 1f, 1.0f, 2.0f); //0.25f
    }

    private static void lootTable(Identifier identifier, Item item, float chance, float min, float max) {
        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
            if (identifier.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(chance))
                        .with(ItemEntry.builder(item))
                        .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)).build());
                supplier.withPool(poolBuilder.build());
            }
        }));
    }

    private static Identifier table(String file) {
        return new Identifier("minecraft", file);
    }

    private static String block(String type) {
        return "blocks/" + type;
    }

    private static String chest(String type) {
        return "chests/" + type;
    }

    private static String villageHouse(String type) {
        return "chests/village/village_" + type;
    }

    private static String entity(String type) {
        return "entities/" + type;
    }

    private static String fishing(String type) {
        return "gameplay/fishing/" + type;
    }
}

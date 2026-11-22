package com.fengxing.moreore;

import com.mojang.logging.LogUtils;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

/**
 * 更多矿石：锡 / 银 / 秘银，各带石头与深板岩两种矿脉，外加粗矿和锭。
 */
@Mod(MoreOre.MODID)
public class MoreOre {

    public static final String MODID = "moreore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // ================= 锭与粗矿 =================
    public static final DeferredItem<Item> TIN_INGOT = ITEMS.registerSimpleItem("tin_ingot", p -> p);
    public static final DeferredItem<Item> RAW_TIN = ITEMS.registerSimpleItem("raw_tin", p -> p);
    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.registerSimpleItem("silver_ingot", p -> p);
    public static final DeferredItem<Item> RAW_SILVER = ITEMS.registerSimpleItem("raw_silver", p -> p);
    public static final DeferredItem<Item> MITHRIL_INGOT = ITEMS.registerSimpleItem("mithril_ingot", p -> p);
    public static final DeferredItem<Item> RAW_MITHRIL = ITEMS.registerSimpleItem("raw_mithril", p -> p);

    // ================= 矿石方块 =================
    public static final DeferredHolder<net.minecraft.world.level.block.Block, OreBlock> TIN_ORE =
            ore("tin_ore", 3.0F, false, () -> RAW_TIN.get(), 1, 3);
    public static final DeferredHolder<net.minecraft.world.level.block.Block, OreBlock> DEEPSLATE_TIN_ORE =
            ore("deepslate_tin_ore", 4.5F, true, () -> RAW_TIN.get(), 1, 4);
    public static final DeferredHolder<net.minecraft.world.level.block.Block, OreBlock> SILVER_ORE =
            ore("silver_ore", 3.0F, false, () -> RAW_SILVER.get(), 2, 5);
    public static final DeferredHolder<net.minecraft.world.level.block.Block, OreBlock> DEEPSLATE_SILVER_ORE =
            ore("deepslate_silver_ore", 4.5F, true, () -> RAW_SILVER.get(), 2, 6);
    public static final DeferredHolder<net.minecraft.world.level.block.Block, OreBlock> MITHRIL_ORE =
            ore("mithril_ore", 4.0F, false, () -> RAW_MITHRIL.get(), 3, 7);
    public static final DeferredHolder<net.minecraft.world.level.block.Block, OreBlock> DEEPSLATE_MITHRIL_ORE =
            ore("deepslate_mithril_ore", 5.5F, true, () -> RAW_MITHRIL.get(), 3, 8);

    private static DeferredHolder<net.minecraft.world.level.block.Block, OreBlock> ore(
            String name, float hardness, boolean deepslate, Supplier<Item> drop, int xpMin, int xpMax) {
        BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                .mapColor(deepslate ? MapColor.DEEPSLATE : MapColor.STONE)
                .strength(hardness, 3.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE);
        return BLOCKS.register(name, () -> new OreBlock(props, drop, xpMin, xpMax));
    }

    // ================= 方块对应物品（手动注册，避开辅助方法的签名差异） =================
    public static final DeferredItem<BlockItem> TIN_ORE_ITEM = blockItem("tin_ore", TIN_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_TIN_ORE_ITEM = blockItem("deepslate_tin_ore", DEEPSLATE_TIN_ORE);
    public static final DeferredItem<BlockItem> SILVER_ORE_ITEM = blockItem("silver_ore", SILVER_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_SILVER_ORE_ITEM = blockItem("deepslate_silver_ore", DEEPSLATE_SILVER_ORE);
    public static final DeferredItem<BlockItem> MITHRIL_ORE_ITEM = blockItem("mithril_ore", MITHRIL_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_MITHRIL_ORE_ITEM = blockItem("deepslate_mithril_ore", DEEPSLATE_MITHRIL_ORE);

    private static DeferredItem<BlockItem> blockItem(
            String name, DeferredHolder<net.minecraft.world.level.block.Block, ? extends net.minecraft.world.level.block.Block> block) {
        return ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // ================= 创造页 =================
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ORE_TAB =
            TABS.register("ores", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.moreore.ores"))
                    .withTabsBefore(CreativeModeTabs.BUILDING_BLOCKS)
                    .icon(() -> new ItemStack(TIN_ORE_ITEM.get()))
                    .displayItems((params, output) -> {
                        output.accept(TIN_ORE_ITEM.get());
                        output.accept(DEEPSLATE_TIN_ORE_ITEM.get());
                        output.accept(RAW_TIN.get());
                        output.accept(TIN_INGOT.get());
                        output.accept(SILVER_ORE_ITEM.get());
                        output.accept(DEEPSLATE_SILVER_ORE_ITEM.get());
                        output.accept(RAW_SILVER.get());
                        output.accept(SILVER_INGOT.get());
                        output.accept(MITHRIL_ORE_ITEM.get());
                        output.accept(DEEPSLATE_MITHRIL_ORE_ITEM.get());
                        output.accept(RAW_MITHRIL.get());
                        output.accept(MITHRIL_INGOT.get());
                    })
                    .build());

    public MoreOre(IEventBus modEventBus, ModContainer modContainer) {
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        TABS.register(modEventBus);
        LOGGER.info("[moreore] 矿石与物品已注册");
    }
}

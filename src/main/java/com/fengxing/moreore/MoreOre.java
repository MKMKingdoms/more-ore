package com.fengxing.moreore;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

/**
 * 更多矿石：锡 / 银 / 秘银，各带石头与深板岩两种矿脉，外加粗矿和锭。
 *
 * 掉落走 OreDrops 里的破坏事件，不依赖方块方法重写。
 */
@Mod(MoreOre.MODID)
public class MoreOre {

    public static final String MODID = "moreore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // ================= 矿石方块 =================
    public static final DeferredBlock<Block> TIN_ORE = ore("tin_ore", 3.0F, false);
    public static final DeferredBlock<Block> DEEPSLATE_TIN_ORE = ore("deepslate_tin_ore", 4.5F, true);
    public static final DeferredBlock<Block> SILVER_ORE = ore("silver_ore", 3.0F, false);
    public static final DeferredBlock<Block> DEEPSLATE_SILVER_ORE = ore("deepslate_silver_ore", 4.5F, true);
    public static final DeferredBlock<Block> MITHRIL_ORE = ore("mithril_ore", 4.0F, false);
    public static final DeferredBlock<Block> DEEPSLATE_MITHRIL_ORE = ore("deepslate_mithril_ore", 5.5F, true);

    private static DeferredBlock<Block> ore(String name, float hardness, boolean deepslate) {
        return BLOCKS.registerSimpleBlock(name, p -> p
                .mapColor(deepslate ? MapColor.DEEPSLATE : MapColor.STONE)
                .strength(hardness, 3.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE));
    }

    // ================= 方块对应物品 =================
    public static final DeferredItem<BlockItem> TIN_ORE_ITEM = ITEMS.registerSimpleBlockItem("tin_ore", TIN_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_TIN_ORE_ITEM = ITEMS.registerSimpleBlockItem("deepslate_tin_ore", DEEPSLATE_TIN_ORE);
    public static final DeferredItem<BlockItem> SILVER_ORE_ITEM = ITEMS.registerSimpleBlockItem("silver_ore", SILVER_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_SILVER_ORE_ITEM = ITEMS.registerSimpleBlockItem("deepslate_silver_ore", DEEPSLATE_SILVER_ORE);
    public static final DeferredItem<BlockItem> MITHRIL_ORE_ITEM = ITEMS.registerSimpleBlockItem("mithril_ore", MITHRIL_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_MITHRIL_ORE_ITEM = ITEMS.registerSimpleBlockItem("deepslate_mithril_ore", DEEPSLATE_MITHRIL_ORE);

    // ================= 锭与粗矿 =================
    public static final DeferredItem<Item> TIN_INGOT = ITEMS.registerSimpleItem("tin_ingot", p -> p);
    public static final DeferredItem<Item> RAW_TIN = ITEMS.registerSimpleItem("raw_tin", p -> p);
    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.registerSimpleItem("silver_ingot", p -> p);
    public static final DeferredItem<Item> RAW_SILVER = ITEMS.registerSimpleItem("raw_silver", p -> p);
    public static final DeferredItem<Item> MITHRIL_INGOT = ITEMS.registerSimpleItem("mithril_ingot", p -> p);
    public static final DeferredItem<Item> RAW_MITHRIL = ITEMS.registerSimpleItem("raw_mithril", p -> p);

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
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
        LOGGER.info("[moreore] 矿石与物品已注册");
    }
}

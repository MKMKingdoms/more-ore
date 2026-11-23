package com.fengxing.moreore;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
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
 * 更多矿石：锡 / 银 / 秘银，各带石头与深板岩两种矿脉，外加粗矿、锭与工具。
 *
 * 工具用 ToolMaterial + Item.Properties#pickaxe 等辅助方法创建（1.21.2+ 的写法）。
 * 贴图暂时指向原版铁制工具（见 assets/moreore/items/*.json）。
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

    // ================= 工具材质 =================
    // 修复材料标签（自己定义，对应 data/moreore/tags/item/*.json）
    public static final TagKey<Item> TIN_INGOTS_TAG =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, "tin_ingots"));
    public static final TagKey<Item> SILVER_INGOTS_TAG =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, "silver_ingots"));
    public static final TagKey<Item> MITHRIL_INGOTS_TAG =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, "mithril_ingots"));

    // ToolMaterial(不能挖的方块标签, 耐久, 挖掘速度, 攻击力加成, 附魔适性, 可修复物品标签)
    public static final ToolMaterial TIN_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_STONE_TOOL, 180, 5.0F, 1.0F, 12, TIN_INGOTS_TAG);
    public static final ToolMaterial SILVER_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL, 320, 7.0F, 1.5F, 18, SILVER_INGOTS_TAG);
    public static final ToolMaterial MITHRIL_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 800, 9.0F, 2.5F, 22, MITHRIL_INGOTS_TAG);

    // ================= 锡制工具 =================
    public static final DeferredItem<Item> TIN_PICKAXE = ITEMS.registerItem("tin_pickaxe", p -> new Item(p.pickaxe(TIN_MATERIAL, 1.0F, -2.8F)));
    public static final DeferredItem<Item> TIN_AXE = ITEMS.registerItem("tin_axe", p -> new Item(p.axe(TIN_MATERIAL, 1.0F, -3.0F)));
    public static final DeferredItem<Item> TIN_SHOVEL = ITEMS.registerItem("tin_shovel", p -> new Item(p.shovel(TIN_MATERIAL, 1.5F, -3.0F)));
    public static final DeferredItem<Item> TIN_HOE = ITEMS.registerItem("tin_hoe", p -> new Item(p.hoe(TIN_MATERIAL, -2, -2.0F)));
    public static final DeferredItem<Item> TIN_SWORD = ITEMS.registerItem("tin_sword", p -> new Item(p.sword(TIN_MATERIAL, 3.0F, -2.4F)));

    // ================= 银制工具 =================
    public static final DeferredItem<Item> SILVER_PICKAXE = ITEMS.registerItem("silver_pickaxe", p -> new Item(p.pickaxe(SILVER_MATERIAL, 1.0F, -2.8F)));
    public static final DeferredItem<Item> SILVER_AXE = ITEMS.registerItem("silver_axe", p -> new Item(p.axe(SILVER_MATERIAL, 1.0F, -3.0F)));
    public static final DeferredItem<Item> SILVER_SHOVEL = ITEMS.registerItem("silver_shovel", p -> new Item(p.shovel(SILVER_MATERIAL, 1.5F, -3.0F)));
    public static final DeferredItem<Item> SILVER_HOE = ITEMS.registerItem("silver_hoe", p -> new Item(p.hoe(SILVER_MATERIAL, -2, -2.0F)));
    public static final DeferredItem<Item> SILVER_SWORD = ITEMS.registerItem("silver_sword", p -> new Item(p.sword(SILVER_MATERIAL, 3.0F, -2.4F)));

    // ================= 秘银制工具 =================
    public static final DeferredItem<Item> MITHRIL_PICKAXE = ITEMS.registerItem("mithril_pickaxe", p -> new Item(p.pickaxe(MITHRIL_MATERIAL, 1.0F, -2.8F)));
    public static final DeferredItem<Item> MITHRIL_AXE = ITEMS.registerItem("mithril_axe", p -> new Item(p.axe(MITHRIL_MATERIAL, 1.0F, -3.0F)));
    public static final DeferredItem<Item> MITHRIL_SHOVEL = ITEMS.registerItem("mithril_shovel", p -> new Item(p.shovel(MITHRIL_MATERIAL, 1.5F, -3.0F)));
    public static final DeferredItem<Item> MITHRIL_HOE = ITEMS.registerItem("mithril_hoe", p -> new Item(p.hoe(MITHRIL_MATERIAL, -2, -2.0F)));
    public static final DeferredItem<Item> MITHRIL_SWORD = ITEMS.registerItem("mithril_sword", p -> new Item(p.sword(MITHRIL_MATERIAL, 3.0F, -2.4F)));

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

                        output.accept(TIN_PICKAXE.get());
                        output.accept(TIN_AXE.get());
                        output.accept(TIN_SHOVEL.get());
                        output.accept(TIN_HOE.get());
                        output.accept(TIN_SWORD.get());

                        output.accept(SILVER_PICKAXE.get());
                        output.accept(SILVER_AXE.get());
                        output.accept(SILVER_SHOVEL.get());
                        output.accept(SILVER_HOE.get());
                        output.accept(SILVER_SWORD.get());

                        output.accept(MITHRIL_PICKAXE.get());
                        output.accept(MITHRIL_AXE.get());
                        output.accept(MITHRIL_SHOVEL.get());
                        output.accept(MITHRIL_HOE.get());
                        output.accept(MITHRIL_SWORD.get());
                    })
                    .build());

    public MoreOre(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
        LOGGER.info("[moreore] 矿石、物品与工具已注册");
    }
}

package com.fengxing.moreore;

import com.mojang.logging.LogUtils;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
 * 更多矿石：锡 / 银 / 秘银，各带石头与深板岩两种矿脉，外加粗矿、锭、工具与装备。
 *
 * 工具与装备暂用铁制的外观和手感（复制其组件），仅耐久按金属不同；
 * 后续做 Tier 分化时，改这里的 durability 参数即可。
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

    // ================= 锡制工具与装备 =================
    public static final DeferredItem<Item> TIN_PICKAXE = tool("tin_pickaxe", Items.IRON_PICKAXE, 180);
    public static final DeferredItem<Item> TIN_AXE = tool("tin_axe", Items.IRON_AXE, 180);
    public static final DeferredItem<Item> TIN_SHOVEL = tool("tin_shovel", Items.IRON_SHOVEL, 180);
    public static final DeferredItem<Item> TIN_HOE = tool("tin_hoe", Items.IRON_HOE, 180);
    public static final DeferredItem<Item> TIN_SWORD = tool("tin_sword", Items.IRON_SWORD, 180);
    public static final DeferredItem<Item> TIN_HELMET = tool("tin_helmet", Items.IRON_HELMET, 190);
    public static final DeferredItem<Item> TIN_CHESTPLATE = tool("tin_chestplate", Items.IRON_CHESTPLATE, 190);
    public static final DeferredItem<Item> TIN_LEGGINGS = tool("tin_leggings", Items.IRON_LEGGINGS, 190);
    public static final DeferredItem<Item> TIN_BOOTS = tool("tin_boots", Items.IRON_BOOTS, 190);

    // ================= 银制工具与装备 =================
    public static final DeferredItem<Item> SILVER_PICKAXE = tool("silver_pickaxe", Items.IRON_PICKAXE, 320);
    public static final DeferredItem<Item> SILVER_AXE = tool("silver_axe", Items.IRON_AXE, 320);
    public static final DeferredItem<Item> SILVER_SHOVEL = tool("silver_shovel", Items.IRON_SHOVEL, 320);
    public static final DeferredItem<Item> SILVER_HOE = tool("silver_hoe", Items.IRON_HOE, 320);
    public static final DeferredItem<Item> SILVER_SWORD = tool("silver_sword", Items.IRON_SWORD, 320);
    public static final DeferredItem<Item> SILVER_HELMET = tool("silver_helmet", Items.IRON_HELMET, 380);
    public static final DeferredItem<Item> SILVER_CHESTPLATE = tool("silver_chestplate", Items.IRON_CHESTPLATE, 380);
    public static final DeferredItem<Item> SILVER_LEGGINGS = tool("silver_leggings", Items.IRON_LEGGINGS, 380);
    public static final DeferredItem<Item> SILVER_BOOTS = tool("silver_boots", Items.IRON_BOOTS, 380);

    // ================= 秘银制工具与装备 =================
    public static final DeferredItem<Item> MITHRIL_PICKAXE = tool("mithril_pickaxe", Items.IRON_PICKAXE, 800);
    public static final DeferredItem<Item> MITHRIL_AXE = tool("mithril_axe", Items.IRON_AXE, 800);
    public static final DeferredItem<Item> MITHRIL_SHOVEL = tool("mithril_shovel", Items.IRON_SHOVEL, 800);
    public static final DeferredItem<Item> MITHRIL_HOE = tool("mithril_hoe", Items.IRON_HOE, 800);
    public static final DeferredItem<Item> MITHRIL_SWORD = tool("mithril_sword", Items.IRON_SWORD, 800);
    public static final DeferredItem<Item> MITHRIL_HELMET = tool("mithril_helmet", Items.IRON_HELMET, 950);
    public static final DeferredItem<Item> MITHRIL_CHESTPLATE = tool("mithril_chestplate", Items.IRON_CHESTPLATE, 950);
    public static final DeferredItem<Item> MITHRIL_LEGGINGS = tool("mithril_leggings", Items.IRON_LEGGINGS, 950);
    public static final DeferredItem<Item> MITHRIL_BOOTS = tool("mithril_boots", Items.IRON_BOOTS, 950);

    /**
     * 以原版物品为模板造一个新物品：外观、挖掘能力、攻击力、穿戴槽位等组件全部沿用，
     * 只把耐久换成我们自己的数值。
     */
    private static DeferredItem<Item> tool(String name, Item template, int durability) {
        return ITEMS.register(name, () -> new Item(copyOf(template).durability(durability)));
    }

    private static Item.Properties copyOf(Item template) {
        Item.Properties props = new Item.Properties();
        copyComponent(props, template, DataComponents.MAX_STACK_SIZE);
        copyComponent(props, template, DataComponents.MAX_DAMAGE);
        copyComponent(props, template, DataComponents.ATTRIBUTE_MODIFIERS);
        copyComponent(props, template, DataComponents.TOOL);
        copyComponent(props, template, DataComponents.EQUIPPABLE);
        copyComponent(props, template, DataComponents.REPAIRABLE);
        copyComponent(props, template, DataComponents.ENCHANTABLE);
        return props;
    }

    private static <T> void copyComponent(Item.Properties props, Item template, DataComponentType<T> type) {
        T value = template.components().get(type);
        if (value != null) {
            props.component(type, value);
        }
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

                        output.accept(TIN_PICKAXE.get());
                        output.accept(TIN_AXE.get());
                        output.accept(TIN_SHOVEL.get());
                        output.accept(TIN_HOE.get());
                        output.accept(TIN_SWORD.get());
                        output.accept(TIN_HELMET.get());
                        output.accept(TIN_CHESTPLATE.get());
                        output.accept(TIN_LEGGINGS.get());
                        output.accept(TIN_BOOTS.get());

                        output.accept(SILVER_PICKAXE.get());
                        output.accept(SILVER_AXE.get());
                        output.accept(SILVER_SHOVEL.get());
                        output.accept(SILVER_HOE.get());
                        output.accept(SILVER_SWORD.get());
                        output.accept(SILVER_HELMET.get());
                        output.accept(SILVER_CHESTPLATE.get());
                        output.accept(SILVER_LEGGINGS.get());
                        output.accept(SILVER_BOOTS.get());

                        output.accept(MITHRIL_PICKAXE.get());
                        output.accept(MITHRIL_AXE.get());
                        output.accept(MITHRIL_SHOVEL.get());
                        output.accept(MITHRIL_HOE.get());
                        output.accept(MITHRIL_SWORD.get());
                        output.accept(MITHRIL_HELMET.get());
                        output.accept(MITHRIL_CHESTPLATE.get());
                        output.accept(MITHRIL_LEGGINGS.get());
                        output.accept(MITHRIL_BOOTS.get());
                    })
                    .build());

    public MoreOre(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
        LOGGER.info("[moreore] 矿石、物品、工具与装备已注册");
    }
}

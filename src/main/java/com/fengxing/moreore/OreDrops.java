package com.fengxing.moreore;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

/**
 * 矿石掉落：监听玩家破坏方块，直接给掉落物 + 经验。
 *
 * 26.x 的事件名是 BreakBlockEvent（旧版叫 BlockEvent.BreakEvent）。
 */
@EventBusSubscriber(modid = MoreOre.MODID)
public final class OreDrops {

    private OreDrops() {
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        BlockState state = event.getState();
        Block broken = state.getBlock();

        ItemStack drop = dropFor(broken);
        if (drop.isEmpty()) {
            return;
        }

        Player player = event.getPlayer();
        if (player == null || player.isCreative()) {
            return;
        }

        Level level = event.getLevel();
        if (level == null || level.isClientSide()) {
            return;
        }

        Block.popResource(level, event.getPos(), drop);

        int xp = Mth.nextInt(level.getRandom(), xpFor(broken, true), xpFor(broken, false));
        if (xp > 0 && level instanceof ServerLevel serverLevel) {
            ExperienceOrb.award(serverLevel, Vec3.atCenterOf(event.getPos()), xp);
        }
    }

    private static ItemStack dropFor(Block block) {
        if (block == MoreOre.TIN_ORE.get() || block == MoreOre.DEEPSLATE_TIN_ORE.get()) {
            return new ItemStack(MoreOre.RAW_TIN.get());
        }
        if (block == MoreOre.SILVER_ORE.get() || block == MoreOre.DEEPSLATE_SILVER_ORE.get()) {
            return new ItemStack(MoreOre.RAW_SILVER.get());
        }
        if (block == MoreOre.MITHRIL_ORE.get() || block == MoreOre.DEEPSLATE_MITHRIL_ORE.get()) {
            return new ItemStack(MoreOre.RAW_MITHRIL.get());
        }
        return ItemStack.EMPTY;
    }

    private static int xpFor(Block block, boolean min) {
        if (block == MoreOre.TIN_ORE.get()) return min ? 1 : 3;
        if (block == MoreOre.DEEPSLATE_TIN_ORE.get()) return min ? 1 : 4;
        if (block == MoreOre.SILVER_ORE.get()) return min ? 2 : 5;
        if (block == MoreOre.DEEPSLATE_SILVER_ORE.get()) return min ? 2 : 6;
        if (block == MoreOre.MITHRIL_ORE.get()) return min ? 3 : 7;
        if (block == MoreOre.DEEPSLATE_MITHRIL_ORE.get()) return min ? 3 : 8;
        return 0;
    }
}

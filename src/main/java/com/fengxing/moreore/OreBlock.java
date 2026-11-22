package com.fengxing.moreore;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * 矿石方块：破坏时直接掉落指定物品，并给经验。
 *
 * 不依赖战利品表 —— 数据包里的表能不能被方块正确引用，
 * 受版本 / 加载顺序影响太大，直接写死更稳。
 */
public class OreBlock extends Block {

    private final Supplier<Item> dropItem;
    private final int xpMin;
    private final int xpMax;

    public OreBlock(Properties properties, Supplier<Item> dropItem, int xpMin, int xpMax) {
        super(properties);
        this.dropItem = dropItem;
        this.xpMin = xpMin;
        this.xpMax = xpMax;
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                              BlockEntity blockEntity, ItemStack tool) {
        if (!level.isClientSide()) {
            Block.popResource(level, pos, new ItemStack(this.dropItem.get()));

            int xp = Mth.nextInt(level.getRandom(), this.xpMin, this.xpMax);
            if (xp > 0 && level instanceof ServerLevel serverLevel) {
                ExperienceOrb.award(serverLevel, Vec3.atCenterOf(pos), xp);
            }
        }
    }
}

package com.fengxing.moreore;

import net.minecraft.world.level.block.Block;

/**
 * 已废弃：26.x 的 Block.playerDestroy 签名有变化，掉落改由 OreDrops 处理。
 * 保留这个类只是为了避免删文件，实际未被注册，可以安全删除。
 */
@Deprecated
public class OreBlock extends Block {

    public OreBlock(Properties properties) {
        super(properties);
    }
}

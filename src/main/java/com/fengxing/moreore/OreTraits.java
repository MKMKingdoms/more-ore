package com.fengxing.moreore;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * 矿石装备的特性。
 *
 * - 银：对亡灵额外 +4 伤害；穿银甲被亡灵攻击时减伤 15%
 * - 秘银：每件护甲 +2.5% 移动速度（全套 +10%）
 */
@EventBusSubscriber(modid = MoreOre.MODID)
public final class OreTraits {

    private static final Identifier MITHRIL_SPEED_ID =
            Identifier.fromNamespaceAndPath(MoreOre.MODID, "mithril_speed");

    private static final float SILVER_BONUS_DAMAGE = 4.0F;
    private static final float SILVER_ARMOR_REDUCTION = 0.85F;
    private static final double MITHRIL_SPEED_PER_PIECE = 0.025D;

    private OreTraits() {
    }

    // ================= 银：对亡灵的攻防 =================
    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        if (!target.getType().builtInRegistryHolder().is(EntityTypeTags.UNDEAD)) {
            return;
        }

        Entity attacker = event.getSource().getEntity();

        // 银武器打亡灵：额外伤害
        if (attacker instanceof Player player && isSilverWeapon(player.getMainHandItem())) {
            event.setAmount(event.getAmount() + SILVER_BONUS_DAMAGE);
        }

        // 穿银甲被亡灵打：减伤
        if (target instanceof Player player && silverPieces(player) > 0) {
            event.setAmount(event.getAmount() * SILVER_ARMOR_REDUCTION);
        }
    }

    // ================= 秘银：轻量移速 =================
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide() || player.tickCount % 20 != 0) {
            return;
        }

        AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null) {
            return;
        }

        speed.removeModifier(MITHRIL_SPEED_ID);

        int pieces = mithrilPieces(player);
        if (pieces > 0) {
            speed.addTransientModifier(new AttributeModifier(
                    MITHRIL_SPEED_ID,
                    MITHRIL_SPEED_PER_PIECE * pieces,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    }

    // ================= 判断工具 =================
    private static boolean isSilverWeapon(ItemStack stack) {
        Item item = stack.getItem();
        return item == MoreOre.SILVER_SWORD.get()
                || item == MoreOre.SILVER_AXE.get()
                || item == MoreOre.SILVER_PICKAXE.get()
                || item == MoreOre.SILVER_SHOVEL.get()
                || item == MoreOre.SILVER_HOE.get();
    }

    private static int silverPieces(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(MoreOre.SILVER_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(MoreOre.SILVER_CHESTPLATE.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(MoreOre.SILVER_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(MoreOre.SILVER_BOOTS.get())) count++;
        return count;
    }

    private static int mithrilPieces(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(MoreOre.MITHRIL_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(MoreOre.MITHRIL_CHESTPLATE.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(MoreOre.MITHRIL_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(MoreOre.MITHRIL_BOOTS.get())) count++;
        return count;
    }
}

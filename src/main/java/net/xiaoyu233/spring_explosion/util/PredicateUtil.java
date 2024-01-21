package net.xiaoyu233.spring_explosion.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class PredicateUtil {
    @NotNull
    public static Predicate<Entity> getVisibleRangeAttackPredicate(World world, @NotNull Entity attacker, @Nullable Entity source) {
        return entity -> {
            if (!(entity instanceof LivingEntity)) return false;
            HitResult raycast = attacker.raycast(entity.distanceTo(attacker), 0, false);
            boolean raycastResult = raycast.getType() == HitResult.Type.MISS || (raycast instanceof BlockHitResult blockHitResult && !world.getBlockState(
                            blockHitResult.getBlockPos())
                    .isSideSolidFullSquare(world, blockHitResult.getBlockPos(), blockHitResult.getSide()));
            if (!raycastResult) return false;
            AbstractTeam targetTeam = entity.getScoreboardTeam();
            if (source != null) {
                return entity != source && (targetTeam == null || targetTeam != source.getScoreboardTeam());
            } else return targetTeam == null || targetTeam != attacker.getScoreboardTeam();
        };
    }


    @NotNull
    public static Predicate<Entity> getNotSameTeamAsOwnerPredicate(Entity owner) {
        return entity -> {
            if (owner != null) {
                return entity != owner && (owner.getScoreboardTeam() == null || owner.getScoreboardTeam() != entity.getScoreboardTeam());
            } else {
                return true;
            }
        };
    }

    public static Predicate<Entity> inDistance(Entity source, double distance){
        return target -> target.squaredDistanceTo(source) <= distance * distance;
    }

    public static Predicate<LivingEntity> asLivingEntity(Predicate<Entity> predicate){
        return predicate::test;
    }
}

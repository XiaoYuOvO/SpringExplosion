package net.xiaoyu233.spring_explosion.util;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.DataCommandStorage;
import net.minecraft.command.argument.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Unique;

public class ItemCommandUtil {
    @Unique
    public static int spawnItemStack(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        return spawnItemWithContext(ctx).getStack().getCount();
    }

    @Unique
    private static ItemEntity spawnItemWithContext(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ItemStackArgument item = ItemStackArgumentType.getItemStackArgument(ctx, "item");
        int count = IntegerArgumentType.getInteger(ctx, "count");
        Vec3d position = ctx.getSource().getPosition();
        ItemStack stack = item.createStack(count, true);
        return spawnItem(ctx.getSource().getWorld(), position, stack);
    }

    @Unique
    private static @NotNull ItemEntity spawnItem(World world, Vec3d position, ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(world, position.x, position.y, position.z, stack);
        itemEntity.setVelocity(0,0.2,0);
        world.spawnEntity(itemEntity);
        itemEntity.setToDefaultPickupDelay();
        return itemEntity;
    }

    @Unique
    public static int spawnItemWithNbt(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        NbtCompound compound = NbtCompoundArgumentType.getNbtCompound(ctx,  "nbt");
        ItemEntity itemEntity = spawnItemWithContext(ctx);
        itemEntity.readCustomDataFromNbt(compound);
        return itemEntity.getStack().getCount();
    }

    @Unique
    public static int spawnItemFromStorage(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        DataCommandStorage dataCommandStorage = ctx.getSource().getWorld().getServer().getDataCommandStorage();
        NbtCompound nbtCompound = dataCommandStorage.get(IdentifierArgumentType.getIdentifier(ctx, "storage"));
        int count = 0;
        for (NbtElement nbtElement : NbtPathArgumentType.getNbtPath(ctx, "path").get(nbtCompound)) {
            if (nbtElement instanceof NbtCompound compound){
                ItemStack itemStack = ItemStack.fromNbt(compound);
                count += spawnItem(ctx.getSource().getWorld(), ctx.getSource().getPosition(), itemStack).getStack().getCount();
            }
        }
        return count;
    }
}

package net.xiaoyu233.spring_explosion.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.command.argument.NbtPathArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ItemCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.xiaoyu233.spring_explosion.util.ItemCommandUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemCommand.class)
public abstract class ItemCommandMixin {

    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;register(Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;)Lcom/mojang/brigadier/tree/LiteralCommandNode;"))
    private static LiteralCommandNode<ServerCommandSource> injectSpawnCommand(CommandDispatcher<ServerCommandSource> instance, LiteralArgumentBuilder<ServerCommandSource> command, CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess){
        return instance.register(command.then(CommandManager.literal("spawn")
                .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(commandRegistryAccess))
                        .then(CommandManager.argument("count", IntegerArgumentType.integer(1))
                                .executes(ItemCommandUtil::spawnItemStack)
                                .then(CommandManager.argument("nbt", NbtCompoundArgumentType.nbtCompound())
                                        .executes(ItemCommandUtil::spawnItemWithNbt))))
                .then(CommandManager.literal("from")
                        .then(CommandManager.argument("storage", IdentifierArgumentType.identifier())
                                .then(CommandManager.argument("path", NbtPathArgumentType.nbtPath()).executes(ItemCommandUtil::spawnItemFromStorage))))));
    }
}

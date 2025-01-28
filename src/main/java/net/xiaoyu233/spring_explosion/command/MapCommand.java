package net.xiaoyu233.spring_explosion.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.network.packet.s2c.play.MapUpdateS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class MapCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("map").then(CommandManager.literal("sendIcon").then(CommandManager.argument("target", EntityArgumentType.player()).then(
                        CommandManager.argument("map_id", IntegerArgumentType.integer(0))
                                .then(CommandManager.argument("icon_id", StringArgumentType.string()).then(CommandManager.argument("x_mul", DoubleArgumentType.doubleArg(0)).then(CommandManager.argument("z_mul",DoubleArgumentType.doubleArg(0)).executes(
                                        ctx -> {
                                            int mapId = IntegerArgumentType.getInteger(ctx,"map_id");
                                            ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "target");
                                            ServerWorld world = ctx.getSource().getWorld();
                                            double xMul = DoubleArgumentType.getDouble(ctx,"x_mul");
                                            double zMul = DoubleArgumentType.getDouble(ctx,"z_mul");
                                            MapState mapState = world.getMapState("map_" + mapId);
                                            try {
                                                if (mapState != null) {
                                                    Vec3d position = ctx.getSource().getPosition();
                                                    ArrayList<MapIcon> mapIcons = Lists.newArrayList(mapState.icons.values());
                                                    mapIcons.add(createMapIcon(mapState, position.x * xMul, position.z * zMul, ctx.getSource().getRotation().y, MapIcon.Type.PLAYER, null));
                                                    target.networkHandler.sendPacket(new MapUpdateS2CPacket(mapId, mapState.scale, mapState.locked, mapIcons, null));
                                                    return 1;
                                                }
                                            }catch (NumberFormatException e){
                                                ctx.getSource().sendFeedback(()->Text.of("Invalid map id"), false);
                                            }
                                            return 0;
                                        }
                                ))))))));
    }

    private static MapIcon createMapIcon(MapState map, double x, double z, double rotation, MapIcon.Type type, @Nullable Text text) {
        byte d = 0;
        int i = 1 << map.scale;
        float f = (float)(x - (double)map.centerX) / (float)i;
        float g = (float)(z - (double)map.centerZ) / (float)i;
        byte b = (byte)((double)(f * 2.0f) + 0.5);
        byte c = (byte)((double)(g * 2.0f) + 0.5);
        if (f >= -63.0f && g >= -63.0f && f <= 63.0f && g <= 63.0f) {
            d = (byte)((rotation + (rotation < 0.0 ? -8.0 : 8.0)) * 16.0 / 360.0);
        } else if (type == MapIcon.Type.PLAYER) {
            if (Math.abs(f) < 320.0f && Math.abs(g) < 320.0f) {
                type = MapIcon.Type.PLAYER_OFF_MAP;
            }
            d = 0;
            if (f <= -63.0f) {
                b = -128;
            }
            if (g <= -63.0f) {
                c = -128;
            }
            if (f >= 63.0f) {
                b = 127;
            }
            if (g >= 63.0f) {
                c = 127;
            }
        }

        return new MapIcon(type,b,c,d,text);
    }
}

package net.xiaoyu233.spring_explosion.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TeamCommand;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TeamCommand.class)
public class TeamCommandMixin {
    @Redirect(method = "executeModifySuffix", at = @At(value = "INVOKE", target = "Lnet/minecraft/scoreboard/Team;setSuffix(Lnet/minecraft/text/Text;)V"))
    private static void redirectSetSuffix(Team instance, Text suffix, ServerCommandSource source, Team team, Text s){
        try {
            instance.setSuffix(Texts.parse(source,suffix,null, 100));
        } catch (CommandSyntaxException e) {
            instance.setSuffix(suffix);
        }
    }
}

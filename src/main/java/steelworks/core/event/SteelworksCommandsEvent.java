package steelworks.core.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.EnumArgument;
import steelworks.SteelworksWeapons;
import steelworks.core.item.ItemEngraveManager;

@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class SteelworksCommandsEvent {

    private static final SimpleCommandExceptionType UNKNOWN_ENGRAVE =
            new SimpleCommandExceptionType(Component.literal("Неизвестная гравировка"));


    @SubscribeEvent
    public static void onRegisterCommand(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("engrave")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.entity())
                        .then(Commands.argument("engrave", StringArgumentType.word())
                                .executes(SteelworksCommandsEvent::execute))));
    }

    private static int execute(CommandContext<CommandSourceStack> context) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        Entity target = EntityArgument.getEntity(context, "target");
        String engraveName = StringArgumentType.getString(context, "engrave");

        ItemEngraveManager.Engraves engrave;
        try {
            engrave = ItemEngraveManager.Engraves.valueOf(engraveName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw UNKNOWN_ENGRAVE.create();
        }

        if (!(target instanceof LivingEntity living)) {
            context.getSource().sendFailure(Component.literal("Цель должна быть живым существом"));
            return 0;
        }

        ItemStack mainHand = living.getMainHandItem();
        if (mainHand.isEmpty()) {
            context.getSource().sendFailure(Component.literal("У цели ничего не надето в основной руке"));
            return 0;
        }

        ItemEngraveManager manager = new ItemEngraveManager();
        manager.addEngrave(mainHand, engrave);

        context.getSource().sendSuccess(() ->
                Component.literal("Гравировка " + engrave.name() + " применена к предмету в руке у " + target.getName().getString()), true);

        return 1;
    }

}

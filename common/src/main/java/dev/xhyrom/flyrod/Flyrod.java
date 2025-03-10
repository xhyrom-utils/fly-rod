package dev.xhyrom.flyrod;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.xhyrom.flyrod.command.FlyCommand;
import dev.xhyrom.flyrod.item.FlyingRodItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public final class Flyrod {
    public static final String MOD_ID = "flyrod";

    // Registries
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);

    // Items
    public static final RegistrySupplier<Item> FLYING_ROD = ITEMS.register("flying_rod", FlyingRodItem::new);


    public static void init() {
        ITEMS.register();

        CommandRegistrationEvent.EVENT.register((dispatcher, context, selection) -> {
            FlyCommand.register(dispatcher);
        });
    }
}

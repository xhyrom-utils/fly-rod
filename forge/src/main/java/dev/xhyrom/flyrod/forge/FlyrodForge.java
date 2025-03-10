package dev.xhyrom.flyrod.forge;

import dev.xhyrom.flyrod.Flyrod;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Flyrod.MOD_ID)
public final class FlyrodForge {
    public FlyrodForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Flyrod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        Flyrod.init();
    }
}

package tallestred.more_player_hurt_sound_effects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.SkinCustomizationScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tallestred.more_player_hurt_sound_effects.client.screen.HurtSoundConfigScreen;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MorePlayerHurtSoundEffects.MODID)
public class MorePlayerHurtSoundEffects {
    public static final String MODID = "more_player_hurt_sound_effects";
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final RegistryObject<SoundEvent> ALTERNATIVE_ONE = SOUNDS.register("player.hurt.alternative.one", () -> new SoundEvent(new ResourceLocation(MODID, "player.hurt.alternative.one")));
    public static final RegistryObject<SoundEvent> ALTERNATIVE_TWO = SOUNDS.register("player.hurt.alternative.two", () -> new SoundEvent(new ResourceLocation(MODID, "player.hurt.alternative.two")));
    public static final RegistryObject<SoundEvent> ALTERNATIVE_THREE = SOUNDS.register("player.hurt.alternative.three", () -> new SoundEvent(new ResourceLocation(MODID, "player.hurt.alternative.three")));

    public MorePlayerHurtSoundEffects(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        SOUNDS.register(bus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.COMMON_SPEC);
        Config.loadConfig(Config.COMMON_SPEC, FMLPaths.CONFIGDIR.get().resolve(MODID + "-client.toml").toString());
    }

    @SubscribeEvent
    public void onScreenLoad(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof OptionsScreen screen) {
            int width = screen.width;
            int height = screen.height;
            Minecraft minecraft = event.getScreen().getMinecraft();;
            event.addListener(new Button(width / 2 - 155, height / 6 + 48 - 34, 150, 20, Component.translatable("options.hurt_sound_effects.title"), (button) -> {
                minecraft.setScreen(new HurtSoundConfigScreen());
            }));
        }
    }
}

@Mod.EventBusSubscriber(modid = MorePlayerHurtSoundEffects.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
class MorePlayerHurtSoundEffectsClientSide {
    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new HurtSoundConfigScreen()));
    }
}

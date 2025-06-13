package tallestred.more_player_hurt_sound_effects;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;

import static tallestred.more_player_hurt_sound_effects.MorePlayerHurtSoundEffects.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;

    static {
        {
            final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
            COMMON = specPair.getLeft();
            COMMON_SPEC = specPair.getRight();
        }
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave()
                .writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }


    public static class CommonConfig {
        public final ForgeConfigSpec.DoubleValue pitch;
        public final ForgeConfigSpec.ConfigValue<HurtSounds> voiceType;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            this.pitch = builder.defineInRange("How high or low of a pitch you want your hurt sound", 1.0D, -5.0D, 5.0D);
            this.voiceType = builder.defineEnum("Type of hurt sound", HurtSounds.DEFAULT);
        }
    }

    public enum HurtSounds {
        DEFAULT("default", SoundEvents.PLAYER_HURT.getLocation()),
        CLASSIC("classic", new ResourceLocation(MODID, "player.hurt.alternative.one")),
        CLASSIC_TWO("variant_two",  new ResourceLocation(MODID, "player.hurt.alternative.two")),
        CLASSIC_THREE("variant_two",  new ResourceLocation(MODID, "player.hurt.alternative.three"));

        private final ResourceLocation sound;
        private static final HurtSounds[] values = values();
        private final String name;

        HurtSounds(String name, ResourceLocation sound) {
            this.name = name;
            this.sound = sound;
        }

        public ResourceLocation getSound() {
            return sound;
        }

        public HurtSounds next() {
            return values[(this.ordinal() + 1) % values.length];
        }
    }
}

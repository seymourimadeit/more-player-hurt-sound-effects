package tallestred.more_player_hurt_sound_effects.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.ModListScreen;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.minecraftforge.registries.ForgeRegistries;
import tallestred.more_player_hurt_sound_effects.Config;
import tallestred.more_player_hurt_sound_effects.ICustomHurtSound;
import tallestred.more_player_hurt_sound_effects.MorePlayerHurtSoundEffects;

public class HurtSoundConfigScreen extends Screen {
    public HurtSoundConfigScreen() {
        super(Component.translatable("options.hurt_sound_effects.title"));
    }

    protected void init() {
        int i = 0;
        this.addRenderableWidget(new ExtendedButton(this.width / 2 - 200, this.height / 6 + 24 * (i >> 1), 200, 20, Component.translatable("options.hurt_sound_effects").append(Config.COMMON.voiceType.get().name()), (button) -> {
            Config.COMMON.voiceType.set(Config.COMMON.voiceType.get().next());
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(ForgeRegistries.SOUND_EVENTS.getValue(Config.COMMON.voiceType.get().getSound()), 1.0F));
            button.setMessage(Component.translatable("options.hurt_sound_effects").append(Config.COMMON.voiceType.get().name()));
            if (this.minecraft.player == null)
                return;
            ((ICustomHurtSound) this.minecraft.player).setHurtSound();
        }));
        ++i;
        this.addRenderableWidget(new ForgeSlider(this.width / 2 + 5, this.height / 6 + 24 * (i >> 1), 200, 20, Component.translatable("options.hurt_sound_effects.pitch"), Component.translatable(""), -5, 5, Config.COMMON.pitch.get(), 0.05D, 0, true) {
            @Override
            protected void applyValue() {
                Config.COMMON.pitch.set(this.getValue());
                if (HurtSoundConfigScreen.this.minecraft.player == null)
                    return;
                ((ICustomHurtSound) HurtSoundConfigScreen.this.minecraft.player).setHurtPitch();
            }

            @Override
            public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
                HurtSoundConfigScreen.this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(ForgeRegistries.SOUND_EVENTS.getValue(Config.COMMON.voiceType.get().getSound()), (float) this.getValue()));
                return super.mouseReleased(pMouseX, pMouseY, pButton);
            }
        });
        ++i;
        if (i % 2 == 1) {
            ++i;
        }
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), 200, 20, CommonComponents.GUI_DONE, (p_96700_) -> {
            this.minecraft.setScreen(new TitleScreen(false));
        }));
    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        drawCenteredString(pPoseStack, this.font, this.title, this.width / 2, 20, 16777215);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }
}

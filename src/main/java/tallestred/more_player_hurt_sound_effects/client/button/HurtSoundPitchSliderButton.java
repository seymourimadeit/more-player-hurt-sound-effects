package tallestred.more_player_hurt_sound_effects.client.button;

import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class HurtSoundPitchSliderButton extends ForgeSlider {

    public HurtSoundPitchSliderButton(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double currentValue, boolean drawString) {
        super(x, y, width, height, prefix, suffix, minValue, maxValue, currentValue, 0.5, 0, drawString);
    }
}

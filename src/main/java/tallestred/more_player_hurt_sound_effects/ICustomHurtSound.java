package tallestred.more_player_hurt_sound_effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public interface ICustomHurtSound {
    float getHurtPitch();

    SoundEvent getNewHurtSound();

    void setHurtPitch();

    void setHurtSound();

}

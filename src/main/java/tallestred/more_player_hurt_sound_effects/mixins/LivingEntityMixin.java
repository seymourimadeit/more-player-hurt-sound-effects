package tallestred.more_player_hurt_sound_effects.mixins;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tallestred.more_player_hurt_sound_effects.ICustomHurtSound;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyVariable(at = @At(value = "STORE"), method = "handleEntityEvent")
    public SoundEvent handleEntityEvent(SoundEvent value) {
        if (this instanceof ICustomHurtSound && ((ICustomHurtSound) this).getNewHurtSound() != SoundEvents.PLAYER_HURT) {
            return ((ICustomHurtSound) this).getNewHurtSound();
        } else {
            return value;
        }
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"), method = "handleEntityEvent", index = 2)
    public float handleEntityEventPitch(float pitch) {
        if (this instanceof ICustomHurtSound) {
            return ((ICustomHurtSound) this).getHurtPitch();
        } else {
            return pitch;
        }
    }
}

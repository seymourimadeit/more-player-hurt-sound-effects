package tallestred.more_player_hurt_sound_effects.mixins;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tallestred.more_player_hurt_sound_effects.Config;
import tallestred.more_player_hurt_sound_effects.ICustomHurtSound;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements ICustomHurtSound {
    private static final EntityDataAccessor<String> CUSTOM_HURT_SOUND = SynchedEntityData.defineId(Player.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Float> CUSTOM_HURT_PITCH = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);

    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public float getHurtPitch() {
        return this.entityData.get(CUSTOM_HURT_PITCH);
    }

    @Override
    public void setHurtPitch() {
        this.entityData.set(CUSTOM_HURT_PITCH, Config.COMMON.pitch.get().floatValue());
    }
    @Override
    public void setHurtSound() {
        this.entityData.set(CUSTOM_HURT_SOUND, Config.COMMON.voiceType.get().getSound().toString());
    }


    @Override
    public SoundEvent getNewHurtSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(this.entityData.get(CUSTOM_HURT_SOUND)));
    }

    @Inject(at = @At(value = "TAIL"), method = "defineSynchedData", cancellable = true)
    public void defineSynchedData(CallbackInfo ci) {
        this.entityData.define(CUSTOM_HURT_SOUND, Config.COMMON.voiceType.get().getSound().toString());
        this.entityData.define(CUSTOM_HURT_PITCH, Config.COMMON.pitch.get().floatValue());
    }
}

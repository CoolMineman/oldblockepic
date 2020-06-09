package net.fabricmc.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;

import com.mojang.blaze3d.platform.GlStateManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class THEEPICMIXIN {
	@Shadow
	private MinecraftClient field_4396;
	@Shadow
	private static final Identifier field_4394 = new Identifier("textures/map/map_background.png");
	@Shadow
	private static final Identifier field_4395 = new Identifier("textures/misc/underwater.png");
	@Shadow
	private ItemStack field_4397;
	@Shadow
	private float field_4398;
	@Shadow
	private float field_4399;
	@Shadow
	private int field_4402 = -1;

	@Shadow
	abstract void method_3472(float f, float g);

	@Shadow
	abstract void method_3476(AbstractClientPlayerEntity abstractClientPlayerEntity);

	@Shadow
	abstract void method_3480(ClientPlayerEntity clientPlayerEntity, float f);

	@Shadow
	abstract void method_3479(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h);

	@Shadow
	abstract void method_3477(AbstractClientPlayerEntity abstractClientPlayerEntity, float f);

	@Shadow
	abstract void method_3485(float f, float g);

	@Shadow
	abstract void method_3490();

	@Shadow
	abstract void method_3473(float f, AbstractClientPlayerEntity abstractClientPlayerEntity);

	@Shadow
	abstract void method_3491(float f);

	@Shadow
	abstract void method_3478(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g);

	@Overwrite
	public void method_3471(float f) {
		float g = 1.0F - (this.field_4399 + (this.field_4398 - this.field_4399) * f);
		AbstractClientPlayerEntity abstractClientPlayerEntity = this.field_4396.player;
		float h = abstractClientPlayerEntity.method_7167(f);
		float i = abstractClientPlayerEntity.field_7333
				+ (abstractClientPlayerEntity.pitch - abstractClientPlayerEntity.field_7333) * f;
		float j = abstractClientPlayerEntity.field_7332
				+ (abstractClientPlayerEntity.yaw - abstractClientPlayerEntity.field_7332) * f;
		this.method_3472(i, j);
		this.method_3476(abstractClientPlayerEntity);
		this.method_3480((ClientPlayerEntity) abstractClientPlayerEntity, f);
		GlStateManager.method_3394();
		GlStateManager.pushMatrix();
		if (this.field_4397 != null) {
			if (this.field_4397.getItem() == Items.FILLED_MAP) {
				this.method_3479(abstractClientPlayerEntity, i, g, h);
			} else if (abstractClientPlayerEntity.method_8009() > 0) {
				UseAction useAction = this.field_4397.getUseAction();
				switch (useAction) {
					case NONE:
						this.method_3485(g, 0.0F);
						break;
					case EAT:
					case DRINK:
						this.method_3477(abstractClientPlayerEntity, f);
						this.method_3485(g, 0.0F);
						break;
					case BLOCK:
						//The Only Diffrent Part of this entire thing lol
						this.method_3485(g, h);
						this.method_3490();
						GlStateManager.scalef(0.83f, 0.88f, 0.85f);
                        GlStateManager.translatef(-0.3f, 0.1f, 0.0f);
						break;
					case BOW:
						this.method_3485(g, 0.0F);
						this.method_3473(f, abstractClientPlayerEntity);
				}
			} else {
				this.method_3491(h);
				this.method_3485(g, h);
			}

			((HeldItemRenderer)(Object)this).method_3482(abstractClientPlayerEntity, this.field_4397, ModelTransformation.Mode.FIRST_PERSON);
		} else if (!abstractClientPlayerEntity.isInvisible()) {
			this.method_3478(abstractClientPlayerEntity, g, h);
		}

		GlStateManager.popMatrix();
		GlStateManager.method_3395();
		GuiLighting.method_2210();
	}
}

package com.milamber_brass.brass_armory.client.render;

import com.milamber_brass.brass_armory.entity.projectile.BombEntity;
import com.milamber_brass.brass_armory.item.BombItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class BombEntityRenderer extends EntityRenderer<BombEntity> {
    private final ItemRenderer itemRenderer;

    public BombEntityRenderer(Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    @ParametersAreNonnullByDefault
    public void render(BombEntity bombEntity, float v, float v1, PoseStack stack, MultiBufferSource bufferSource, int light) {
        if (bombEntity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(bombEntity) < 12.25D)) {
            stack.pushPose();
            ItemStack bombStack = bombEntity.getItem();
            stack.translate(0D, 0.1D, 0D);
            stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            stack.mulPose(Vector3f.ZP.rotationDegrees(bombEntity.getRotation()));
            stack.translate(0D, -0.1D, 0D);
            BakedModel bakedmodel = this.itemRenderer.getModel(bombStack, bombEntity.level, null, bombEntity.getId());
            this.itemRenderer.render(bombStack, ItemTransforms.TransformType.GROUND, false, stack, bufferSource, light, OverlayTexture.NO_OVERLAY, bakedmodel);
            stack.popPose();
            super.render(bombEntity, v, v1, stack, bufferSource, light);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    @ParametersAreNonnullByDefault
    @Nonnull
    public ResourceLocation getTextureLocation(BombEntity bombEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}

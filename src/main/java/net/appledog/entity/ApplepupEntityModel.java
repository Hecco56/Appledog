package net.appledog.entity;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ApplepupEntityModel<T extends ApplepupEntity> extends SinglePartEntityModel<T> {

    private final ModelPart body;

    public ApplepupEntityModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData snout = body.addChild("snout", ModelPartBuilder.create().uv(0, 12).cuboid(-2.0F, -3.0F, -3.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -1.0F));

        ModelPartData stem = body.addChild("stem", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(0.0F, -3.0F, -1.0F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.updateAnimation(entity.joyAnimationState, ADAnimations.JOY, animationProgress, 1f);
    }

    @Override
    public ModelPart getPart() {
        return body;
    }
}
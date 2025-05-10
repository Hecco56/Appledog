package net.appledog.entity;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class AppledogEntityModel<T extends AppledogEntity> extends AnimalModel<T> {
    private final ModelPart head;
    private final ModelPart torso;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart tail;
    private final ModelPart neck;
    private final ModelPart root;
    public AppledogEntityModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.torso = root.getChild("body");
        this.neck = root.getChild("upper_body");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.tail = root.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 13.5F, -7.0F));
        modelPartData2.addChild("real_head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F,  new Dilation(0.0F)).uv(16, 14).cuboid(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F,  new Dilation(0.0F)).uv(22, 14).cuboid(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F,  new Dilation(0.0F)).uv(0, 10).cuboid(-0.5F, -0.001F, -5.0F, 3.0F, 3.0F, 4.0F,  new Dilation(0.0F)), ModelTransform.NONE);
        ModelPartData stem = modelPartData2.addChild("stem", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, -4.0F, -1.0F));

        stem.addChild("stem_r1", ModelPartBuilder.create().uv(0, 10).cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        stem.addChild("stem_r2", ModelPartBuilder.create().uv(0, 10).cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        modelPartData.addChild("body", ModelPartBuilder.create().uv(18, 14).cuboid(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F,  new Dilation(0.0F)), ModelTransform.of(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        modelPartData.addChild("upper_body", ModelPartBuilder.create().uv(21, 0).cuboid(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F,  new Dilation(0.0F)), ModelTransform.of(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 18).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F,  new Dilation(0.0F));
        ModelPartBuilder modelPartBuilder1 = ModelPartBuilder.create().uv(43, 18).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F,  new Dilation(0.0F));
        modelPartData.addChild("right_hind_leg", modelPartBuilder1, ModelTransform.pivot(-2.5F, 16.0F, 7.0F));
        modelPartData.addChild("left_hind_leg", modelPartBuilder, ModelTransform.pivot(0.5F, 16.0F, 7.0F));
        modelPartData.addChild("right_front_leg", modelPartBuilder, ModelTransform.pivot(-2.5F, 16.0F, -4.0F));
        modelPartData.addChild("left_front_leg", modelPartBuilder1, ModelTransform.pivot(0.5F, 16.0F, -4.0F));
        ModelPartData modelPartData3 = modelPartData.addChild("tail", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 12.0F, 8.0F, ((float)Math.PI / 3f), 0.0F, 0.0F));
        modelPartData3.addChild("real_tail", ModelPartBuilder.create().uv(9, 18).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F,  new Dilation(0.0F)), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 32);
    }

    public ModelPart getPart() {
        return this.root;
    }

    public void animateModel(T wolfEntity, float f, float g, float h) {
        this.tail.yaw = MathHelper.cos(f * 0.6662F) * 1.4F * g;

        if (wolfEntity.isInSittingPose()) {
            this.neck.setPivot(-1.0F, 16.0F, -3.0F);
            this.neck.pitch = 1.2566371F;
            this.neck.yaw = 0.0F;
            this.torso.setPivot(0.0F, 18.0F, 0.0F);
            this.torso.pitch = ((float)Math.PI / 4F);
            this.tail.setPivot(-1.0F, 21.0F, 6.0F);
            this.rightHindLeg.setPivot(-2.5F, 22.7F, 2.0F);
            this.rightHindLeg.pitch = ((float)Math.PI * 1.5F);
            this.leftHindLeg.setPivot(0.5F, 22.7F, 2.0F);
            this.leftHindLeg.pitch = ((float)Math.PI * 1.5F);
            this.rightFrontLeg.pitch = 5.811947F;
            this.rightFrontLeg.setPivot(-2.49F, 17.0F, -4.0F);
            this.leftFrontLeg.pitch = 5.811947F;
            this.leftFrontLeg.setPivot(0.51F, 17.0F, -4.0F);
        } else {
            this.torso.setPivot(0.0F, 14.0F, 2.0F);
            this.torso.pitch = 1.5707964F;
            this.neck.setPivot(-1.0F, 14.0F, -3.0F);
            this.neck.pitch = this.torso.pitch;
            this.tail.setPivot(-1.0F, 12.0F, 8.0F);
            this.rightHindLeg.setPivot(-2.5F, 16.0F, 7.0F);
            this.leftHindLeg.setPivot(0.5F, 16.0F, 7.0F);
            this.rightFrontLeg.setPivot(-2.5F, 16.0F, -4.0F);
            this.leftFrontLeg.setPivot(0.5F, 16.0F, -4.0F);
            this.rightHindLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
            this.leftHindLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
            this.rightFrontLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
            this.leftFrontLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
        }
    }

    public void setAngles(T wolfEntity, float f, float g, float h, float i, float j) {
        this.head.pitch = j * 0.017453292F;
        this.head.yaw = i * 0.017453292F;
    }

    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.torso, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.neck);
    }
}
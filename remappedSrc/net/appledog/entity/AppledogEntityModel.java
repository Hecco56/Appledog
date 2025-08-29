package net.appledog.entity;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class AppledogEntityModel<T extends AppledogEntity> extends AgeableListModel<T> {
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

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData2 = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));
        modelPartData2.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F,  new CubeDeformation(0.0F)).texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F,  new CubeDeformation(0.0F)).texOffs(22, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F,  new CubeDeformation(0.0F)).texOffs(0, 10).addBox(-0.5F, -0.001F, -5.0F, 3.0F, 3.0F, 4.0F,  new CubeDeformation(0.0F)), PartPose.ZERO);
        PartDefinition stem = modelPartData2.addOrReplaceChild("stem", CubeListBuilder.create(), PartPose.offset(1.0F, -4.0F, -1.0F));

        stem.addOrReplaceChild("stem_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        stem.addOrReplaceChild("stem_r2", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F,  new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F,  new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        CubeListBuilder modelPartBuilder = CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F,  new CubeDeformation(0.0F));
        CubeListBuilder modelPartBuilder1 = CubeListBuilder.create().texOffs(43, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F,  new CubeDeformation(0.0F));
        modelPartData.addOrReplaceChild("right_hind_leg", modelPartBuilder1, PartPose.offset(-2.5F, 16.0F, 7.0F));
        modelPartData.addOrReplaceChild("left_hind_leg", modelPartBuilder, PartPose.offset(0.5F, 16.0F, 7.0F));
        modelPartData.addOrReplaceChild("right_front_leg", modelPartBuilder, PartPose.offset(-2.5F, 16.0F, -4.0F));
        modelPartData.addOrReplaceChild("left_front_leg", modelPartBuilder1, PartPose.offset(0.5F, 16.0F, -4.0F));
        PartDefinition modelPartData3 = modelPartData.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 0, 0.0F, 0.0F));
        modelPartData3.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F,  new CubeDeformation(0.0F)), PartPose.ZERO);
        return LayerDefinition.create(modelData, 64, 32);
    }

    public ModelPart getPart() {
        return this.root;
    }

    public void animateModel(T wolfEntity, float f, float g, float h) {
        this.tail.yRot = Mth.cos(f * 0.6662F) * 1.4F * g;

        if (wolfEntity.isInSittingPose()) {
            this.neck.setPos(-1.0F, 16.0F, -3.0F);
            this.neck.xRot = 1.2566371F;
            this.neck.yRot = 0.0F;
            this.torso.setPos(0.0F, 18.0F, 0.0F);
            this.torso.xRot = ((float)Math.PI / 4F);
            this.tail.setPos(-1.0F, 21.0F, 6.0F);
            this.rightHindLeg.setPos(-2.5F, 22.7F, 2.0F);
            this.rightHindLeg.xRot = ((float)Math.PI * 1.5F);
            this.leftHindLeg.setPos(0.5F, 22.7F, 2.0F);
            this.leftHindLeg.xRot = ((float)Math.PI * 1.5F);
            this.rightFrontLeg.xRot = 5.811947F;
            this.rightFrontLeg.setPos(-2.49F, 17.0F, -4.0F);
            this.leftFrontLeg.xRot = 5.811947F;
            this.leftFrontLeg.setPos(0.51F, 17.0F, -4.0F);
            this.tail.xRot = 1.9f;
        } else {
            this.torso.setPos(0.0F, 14.0F, 2.0F);
            this.torso.xRot = 1.5707964F;
            this.neck.setPos(-1.0F, 14.0F, -3.0F);
            this.neck.xRot = this.torso.xRot;
            this.tail.setPos(-1.0F, 12.0F, 8.0F);
            this.rightHindLeg.setPos(-2.5F, 16.0F, 7.0F);
            this.leftHindLeg.setPos(0.5F, 16.0F, 7.0F);
            this.rightFrontLeg.setPos(-2.5F, 16.0F, -4.0F);
            this.leftFrontLeg.setPos(0.5F, 16.0F, -4.0F);
            this.rightHindLeg.xRot = Mth.cos(f * 0.6662F) * 1.4F * g;
            this.leftHindLeg.xRot = Mth.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
            this.rightFrontLeg.xRot = Mth.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
            this.leftFrontLeg.xRot = Mth.cos(f * 0.6662F) * 1.4F * g;
            this.tail.xRot = (float)Math.PI / 1.7f;
        }
    }

    public void setAngles(T wolfEntity, float f, float g, float h, float i, float j) {
        this.head.xRot = j * 0.017453292F;
        this.head.yRot = i * 0.017453292F;
    }

    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.torso, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.neck);
    }
}
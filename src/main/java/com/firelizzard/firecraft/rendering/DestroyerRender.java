package com.firelizzard.firecraft.rendering;

import org.lwjgl.opengl.GL11;

import com.firelizzard.firecraft.item.DestroyerTool;
import com.firelizzard.firecraft.item.DestroyerTool.Modes;

import net.machinemuse.numina.geometry.Colour;
import net.machinemuse.utils.render.MuseRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class DestroyerRender extends ItemRenderBase {
	// get timer from Minecraft.getMinecraft() via reflection

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if (!(item.getItem() instanceof DestroyerTool))
			return false;

		DestroyerTool tool = (DestroyerTool)item.getItem();
		if (tool.getModeEnum(item) != DestroyerTool.Modes.WEAPON)
			return false;

		switch (type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
			return true;

		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
//		return handleRenderType(item, type) & helper.ordinal() < ItemRendererHelper.EQUIPPED_BLOCK.ordinal();
		return false;
	}

	@Override
	protected void renderItemAsEquipped(ItemRenderType type, ItemStack item, RenderBlocks renderBlocks, EntityLivingBase entity, boolean firstPerson) {
		DestroyerTool tool = (DestroyerTool)item.getItem();
		IIcon iicon = entity.getItemIcon(item, 0);

		TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
		textureManager.bindTexture(textureManager.getResourceLocation(item.getItemSpriteNumber()));

		EntityPlayer player = null;
		if (entity instanceof EntityPlayer)
			player = (EntityPlayer)entity;

		float chargeLevel = Float.NaN;
		if (player != null && player.isUsingItem() && player.getItemInUse() == item)
			chargeLevel = tool.getChargeLevel(item, player.getItemInUseDuration());
//		else if (player != null && player.inventory.getCurrentItem() == item && Minecraft.getMinecraft().gameSettings.keyBindAttack.getIsKeyPressed()) {
//			GL11.glPopMatrix();
//			GL11.glPopMatrix();
//			GL11.glPushMatrix();
//			GL11.glPushMatrix();
//        }

        Tessellator tessellator = Tessellator.instance;
        float f = iicon.getMinU();
        float f1 = iicon.getMaxU();
        float f2 = iicon.getMinV();
        float f3 = iicon.getMaxV();
        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);

        if (!Float.isNaN(chargeLevel)) {
            GL11.glPushMatrix();
            GL11.glTranslated(1, 1, -0.04);
            PlasmaBoltRender.doRender(chargeLevel);
            GL11.glPopMatrix();
        }

        if (tool.getModeEnum(item) == Modes.WEAPON && !player.isUsingItem()) {
        	GL11.glPushMatrix();
        	GL11.glTranslated(0, 0, -0.075);
        	GL11.glScaled(1, 1, 0.1);
        	GL11.glRotated(45, 0, 0, 1);
        	GL11.glScaled(1, 0.6, 1);
        	GL11.glRotated(-45, 0, 0, 1);
        	GL11.glScaled(0.7, 0.7, 1);
        	GL11.glTranslated(0.2, 0.2, 0);

        	Colour c = new Colour(1, 0.8, 0.9, 0.9);
        	MuseRenderer.drawLightning(0, 0, 0, 1, 1, 1, c);
//            for (int i = 0; i < 3; i++) {
//                double angle1 = (Math.random() * 2 * Math.PI);
//                double angle2 = (Math.random() * 2 * Math.PI);
//                MuseRenderer.drawLightning(Math.cos(angle1) * 0.25, Math.sin(angle1) * 0.5, 0, Math.cos(angle2) * 0.25, Math.sin(angle2) * 0.5, 1, new Colour(1, 1, 1, 0.9));
//            }
            GL11.glPopMatrix();
        }
//        else if (player != null && player.inventory.getCurrentItem() == item && Minecraft.getMinecraft().gameSettings.keyBindAttack.getIsKeyPressed()) {
//            GL11.glTranslated(2, 2, -0.04);
//            GL11.glPushMatrix();
//            PlasmaBoltRender.doRender(1);
//            GL11.glPopMatrix();
//        }
	}
}

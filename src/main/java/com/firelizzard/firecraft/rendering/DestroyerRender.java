package com.firelizzard.firecraft.rendering;

import org.lwjgl.opengl.GL11;

import com.firelizzard.firecraft.item.DestroyerTool;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class DestroyerRender extends RenderBase {
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
		
		float chargeLevel = Float.NaN;
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			if (player.isUsingItem() && player.getItemInUse() == item)
				chargeLevel = tool.getChargeLevel(item, player.getItemInUseDuration());
		}
		
		IIcon iicon = entity.getItemIcon(item, 0);
        if (iicon == null)
        {
            GL11.glPopMatrix();
            return;
        }

        Tessellator tessellator = Tessellator.instance;
        float f = iicon.getMinU();
        float f1 = iicon.getMaxU();
        float f2 = iicon.getMinV();
        float f3 = iicon.getMaxV();
        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
        
        if (!Float.isNaN(chargeLevel)) {
            GL11.glTranslated(1, 1, -0.04);
            GL11.glPushMatrix();
            PlasmaBoltRender.doRender(chargeLevel);
            GL11.glPopMatrix();
        } else if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			if (player.inventory.getCurrentItem() == item && Minecraft.getMinecraft().gameSettings.keyBindAttack.getIsKeyPressed()) {
	            GL11.glTranslated(2, 2, -0.04);
	            GL11.glPushMatrix();
	            PlasmaBoltRender.doRender(1);
	            GL11.glPopMatrix();
			}
        }
	}
}

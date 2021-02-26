//package com.firelizzard.firecraft.rendering;
//
//import org.lwjgl.opengl.GL11;
//
//import net.minecraft.block.Block;
//import net.minecraft.client.renderer.RenderBlocks;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.world.IBlockAccess;
//
//public class SilmarilliumBlockRender extends SimpleBlockRenderBase {
//	@Override
//	public boolean shouldRender3DInInventory(int modelId) {
//		return true;
//	}
//
//	@Override
//	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
//        Tessellator tessellator = Tessellator.instance;
//
//        block.setBlockBoundsForItemRender();
//        renderer.setRenderBoundsFromBlock(block);
//
//        GL11.glPushMatrix();
//
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//
//        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
//        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(0.0F, -1.0F, 0.0F);
//        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
//        tessellator.draw();
//
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(0.0F, 1.0F, 0.0F);
//        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
//        tessellator.draw();
//
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(0.0F, 0.0F, -1.0F);
//        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
//        tessellator.draw();
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(0.0F, 0.0F, 1.0F);
//        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
//        tessellator.draw();
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
//        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
//        tessellator.draw();
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(1.0F, 0.0F, 0.0F);
//        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
//        tessellator.draw();
//        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
//
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glPopMatrix();
//	}
//
//	@Override
//	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
//        GL11.glPushMatrix();
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//
//		boolean ret = renderer.renderStandardBlock(block, x, y, z);
//
////        GL11.glDisable(GL11.GL_BLEND);
//		GL11.glPopMatrix();
//		return ret;
//	}
//}

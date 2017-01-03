//package com.firelizzard.firecraft.rendering;
//
//import com.firelizzard.firecraft.block.SilmarilBlock;
//
//import net.minecraft.block.Block;
//import net.minecraft.client.renderer.RenderBlocks;
//import net.minecraft.world.IBlockAccess;
//
//public class SimlarilBlockRender extends SimpleBlockRenderBase {
//	@Override
//    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks helper) {
//	}
//
//	@Override
//    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks helper) {
//		return renderSilmarilBlock((SilmarilBlock)block, x, y, z, helper);
//	}
//
//	@Override
//    public boolean shouldRender3DInInventory(int modelId) {
//		return true;
//	}
//
//    boolean renderSilmarilBlock(SilmarilBlock silmaril, int x, int y, int z, RenderBlocks helper)
//    {
//        boolean flag = false;
//        int l = 0;
//
//        for (int i1 = 0; i1 < 8; ++i1)
//        {
//            byte b0 = 0;
//            byte b1 = 1;
//
//            if (i1 == 0)
//            {
//                b0 = 2;
//            }
//
//            if (i1 == 1)
//            {
//                b0 = 3;
//            }
//
//            if (i1 == 2)
//            {
//                b0 = 4;
//            }
//
//            if (i1 == 3)
//            {
//                b0 = 5;
//                b1 = 2;
//            }
//
//            if (i1 == 4)
//            {
//                b0 = 6;
//                b1 = 3;
//            }
//
//            if (i1 == 5)
//            {
//                b0 = 7;
//                b1 = 5;
//            }
//
//            if (i1 == 6)
//            {
//                b0 = 6;
//                b1 = 2;
//            }
//
//            if (i1 == 7)
//            {
//                b0 = 3;
//            }
//
//            float f = (float)b0 / 16.0F;
//            float f1 = 1.0F - (float)l / 16.0F;
//            float f2 = 1.0F - (float)(l + b1) / 16.0F;
//            l += b1;
//            helper.setRenderBounds((double)(0.5F - f), (double)f2, (double)(0.5F - f), (double)(0.5F + f), (double)f1, (double)(0.5F + f));
//            helper.renderStandardBlock(silmaril, x, y, z);
//        }
//
//        flag = true;
//        helper.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
//        return flag;
//    }
//}

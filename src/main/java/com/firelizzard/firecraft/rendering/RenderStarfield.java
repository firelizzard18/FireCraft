package com.firelizzard.firecraft.rendering;

import org.lwjgl.opengl.GL11;

import cofh.core.render.IconRegistry;
import cofh.core.render.RenderUtils;
import cofh.core.render.ShaderHelper;
import cofh.repack.codechicken.lib.render.CCModel;
import cofh.repack.codechicken.lib.render.CCRenderState;
import cofh.thermalfoundation.render.shader.ShaderStarfield;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class RenderStarfield extends TileEntitySpecialRenderer implements IItemRenderer {
	public static final RenderStarfield instance = new RenderStarfield();

	static IIcon texture;
	static CCModel model = CCModel.quadModel(24).generateBlock(0, 0, 0, 0, 1, 1, 1).computeNormals();;

	public static void initialize() {
		texture = IconRegistry.getIcon("SkyEnder");
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		GL11.glPushMatrix();

		CCRenderState.changeTexture(ShaderStarfield.starsTexture);

		ShaderStarfield.alpha = 0.05f;

		ShaderHelper.useShader(ShaderStarfield.starfieldShader, ShaderStarfield.callback);
		CCRenderState.startDrawing();
		model.render(x, y, z, RenderUtils.getIconTransformation(texture));
		CCRenderState.draw();
		ShaderHelper.releaseShader();

		GL11.glPopMatrix();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		double offset = -0.5;
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			offset = 0;
		}

		GL11.glPushMatrix();
		RenderUtils.preItemRender();

		CCRenderState.changeTexture(ShaderStarfield.starsTexture);

		ShaderStarfield.alpha = 0;

		ShaderHelper.useShader(ShaderStarfield.starfieldShader, ShaderStarfield.callback);
		CCRenderState.startDrawing();
		model.render(offset, offset, offset, RenderUtils.getIconTransformation(texture));
		CCRenderState.draw();
		ShaderHelper.releaseShader();

		RenderUtils.postItemRender();
		GL11.glPopMatrix();
	}
}

package com.firelizzard.firecraft.rendering;

import buildcraft.texture.TextureManager;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.IItemRenderer;

public abstract class ItemRenderBase implements IItemRenderer {
	protected void renderItemAsEntity(ItemRenderType type, ItemStack item, RenderBlocks renderBlocks, EntityItem entity) {}
	protected void renderItemAsEquipped(ItemRenderType type, ItemStack item, RenderBlocks renderBlocks, EntityLivingBase entity, boolean firstPerson) {}
	protected void renderItemAsFirstPersonMap(ItemRenderType type, ItemStack item, EntityClientPlayerMP player, TextureManager textureManager, MapData mapData) {}
	protected void renderItemAsInventory(ItemRenderType type, ItemStack item, RenderBlocks renderBlocks) {}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
		case ENTITY: {
			RenderBlocks renderBlocks = (RenderBlocks)data[0];
			EntityItem entity = (EntityItem)data[1];
			renderItemAsEntity(type, item, renderBlocks, entity);
			break;
		}
		case EQUIPPED: {
			RenderBlocks renderBlocks = (RenderBlocks)data[0];
			EntityLivingBase entity = (EntityLivingBase)data[1];
			renderItemAsEquipped(type, item, renderBlocks, entity, false);
			break;
		}
		case EQUIPPED_FIRST_PERSON: {
			RenderBlocks renderBlocks = (RenderBlocks)data[0];
			EntityLivingBase entity = (EntityLivingBase)data[1];
			renderItemAsEquipped(type, item, renderBlocks, entity, true);
			break;
		}
		case FIRST_PERSON_MAP:
			EntityClientPlayerMP player = (EntityClientPlayerMP)data[0];
			TextureManager textureManager = (TextureManager)data[1];
			MapData mapData = (MapData)data[2];
			renderItemAsFirstPersonMap(type, item, player, textureManager, mapData);
			break;
		case INVENTORY: {
			RenderBlocks renderBlocks = (RenderBlocks)data[0];
			renderItemAsInventory(type, item, renderBlocks);
			break;
		}
		default:
			break;
		}
	}
}

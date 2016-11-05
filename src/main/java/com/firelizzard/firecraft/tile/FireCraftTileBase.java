package com.firelizzard.firecraft.tile;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.container.ContainerBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public abstract class FireCraftTileBase extends TileEntity {
	@SideOnly(Side.CLIENT)
	public abstract GuiBase getGui(InventoryPlayer inventoryPlayer);
	
	public abstract ContainerBase getContainer(InventoryPlayer inventoryPlayer);
	
	public abstract ResourceLocation getGuiTextureLocation();
}

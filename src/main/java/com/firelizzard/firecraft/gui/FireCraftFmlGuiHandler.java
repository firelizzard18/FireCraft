package com.firelizzard.firecraft.gui;

import com.firelizzard.firecraft.tile.FireCraftTileBase;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FireCraftFmlGuiHandler implements IGuiHandler {
	public static final int TILE_GUI = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == FireCraftFmlGuiHandler.TILE_GUI) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof FireCraftTileBase) {
				return ((FireCraftTileBase)tile).getContainer(player.inventory);
			}
		}
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == FireCraftFmlGuiHandler.TILE_GUI) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof FireCraftTileBase) {
				return ((FireCraftTileBase)tile).getGui(player.inventory);
			}
		}
		// TODO Auto-generated method stub
		return null;
	}
}

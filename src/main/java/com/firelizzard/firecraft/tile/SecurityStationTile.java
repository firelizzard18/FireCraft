//package com.firelizzard.firecraft.tile;
//
//import com.firelizzard.firecraft.FireCraftMod;
//import com.firelizzard.firecraft.block.SecurityStationBlock;
//import com.firelizzard.firecraft.gui.SecurityStationContainer;
//import com.firelizzard.firecraft.gui.SecurityStationGui;
//
//import net.minecraft.entity.player.InventoryPlayer;
//import net.minecraft.util.ResourceLocation;
//
//public class SecurityStationTile extends FireCraftTileBase {
//
//	@Override
//	public SecurityStationGui getGui(InventoryPlayer inventoryPlayer) {
//		return new SecurityStationGui(getContainer(inventoryPlayer), this);
//	}
//
//	@Override
//	public SecurityStationContainer getContainer(InventoryPlayer inventoryPlayer) {
//		return new SecurityStationContainer();
//	}
//
//	@Override
//	public ResourceLocation getGuiTextureLocation() {
//		return new ResourceLocation(FireCraftMod.getGuiTextureLocation(SecurityStationBlock.NAME + ".png"));
//	}
//}

package com.firelizzard.firecraft.block;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.tile.UnpackerTile;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class UnpackerBlock extends BlockContainer {
	public static final String NAME = "unpacker";

	protected UnpackerBlock() {
        super(Material.iron);
		setBlockName(NAME);
		setCreativeTab(FireCraftMod.TAB);
	}

	@Override
	public TileEntity createNewTileEntity(World wrold, int arg) {
		return new UnpackerTile();
	}
}

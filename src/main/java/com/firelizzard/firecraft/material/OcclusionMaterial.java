package com.firelizzard.firecraft.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class OcclusionMaterial extends Material {
	public OcclusionMaterial()
	{
		super(MapColor.magentaColor);
	}
	
	@Override
	public boolean isLiquid()
	{
		return false;
	}

	@Override
	public boolean blocksMovement()
	{
		return false;
	}

	@Override
	public boolean isSolid()
	{
		return true;
	}

	@Override
	public boolean getCanBlockGrass()
	{
		return true;
	}
}

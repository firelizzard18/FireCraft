package com.firelizzard.firecraft;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;

public class SpeedLimitMaterial extends Material {
	public SpeedLimitMaterial()
	{
		super(MapColor.grayColor);
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
		return false;
	}

	@Override
	public boolean getCanBlockGrass()
	{
		return false;
	}
}

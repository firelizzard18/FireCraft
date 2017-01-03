package com.firelizzard.firecraft.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class SilmarilliumMaterial extends Material {
	public SilmarilliumMaterial() {
		super(MapColor.lightBlueColor);
		setRequiresTool();
	}

	@Override
	public boolean isOpaque() {
		return false;
	}
}

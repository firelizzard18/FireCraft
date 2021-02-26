package com.firelizzard.firecraft.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class StarfieldMaterial extends Material {
	public StarfieldMaterial() {
		super(MapColor.obsidianColor);
		setRequiresTool();
	}

	@Override
	public boolean isOpaque() {
		return true;
	}
}
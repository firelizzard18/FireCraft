package com.firelizzard.firecraft;

import net.minecraft.block.material.Material;

public class SilmarilliumStorageBlock extends BasicBlock {
	public SilmarilliumStorageBlock() {
		super(Material.iron, FireCraftOres.SILMARILLIUM + "Storage");
		setHardness(5.0F);
		setResistance(10.0F);
        setLightOpacity(0);
        setLightLevel(15);
		setStepSound(soundTypeMetal);
		setCreativeTab(FireCraftMod.tab);
		setBlockName(FireCraftOres.SILMARILLIUM);

		setHarvestLevel("pickaxe", 2);
		setHarvestLevel("pickaxe", 1, 0);
		setHarvestLevel("pickaxe", 1, 1);
		setHarvestLevel("pickaxe", 3, 6);
		setHarvestLevel("pickaxe", 3, 12);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}

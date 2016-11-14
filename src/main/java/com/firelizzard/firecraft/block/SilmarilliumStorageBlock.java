package com.firelizzard.firecraft.block;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.initialization.FireCraftOres;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class SilmarilliumStorageBlock extends Block {
	public SilmarilliumStorageBlock() {
		super(Material.iron);
		setHardness(5.0F);
		setResistance(10.0F);
        setLightOpacity(0);
        setLightLevel(1);
		setStepSound(soundTypeMetal);
		setCreativeTab(FireCraftMod.TAB);
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
//		return true;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(FireCraftMod.MODID + ":" + FireCraftOres.SILMARILLIUM + "Storage");
    }
}

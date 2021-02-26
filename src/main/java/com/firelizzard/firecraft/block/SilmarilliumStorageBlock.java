package com.firelizzard.firecraft.block;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.initialization.FireCraftMaterials;
import com.firelizzard.firecraft.initialization.FireCraftOres;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;

public class SilmarilliumStorageBlock extends Block {
	public SilmarilliumStorageBlock() {
		super(FireCraftMaterials.silmarillium);
		setHardness(5.0F);
		setResistance(10.0F);
        setLightOpacity(0);
        setLightLevel(1);
		setStepSound(soundTypeMetal);
		setCreativeTab(FireCraftMod.TAB);
		setBlockName(FireCraftOres.SILMARILLIUM);

		setHarvestLevel("pickaxe", 3);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(FireCraftMod.MODID + ":" + FireCraftOres.SILMARILLIUM + "Storage");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int meta)
    {
        return world.getBlock(x, y, z) != this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}

package com.firelizzard.firecraft.block;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.initialization.FireCraftMaterials;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;

public class PrismiumBlock extends Block {
	public static final String NAME = "prismium";
	
	public PrismiumBlock() {
		super(FireCraftMaterials.prismium);
		setHardness(5.0F);
		setResistance(10.0F);
        setLightOpacity(0);
        setLightLevel(1);
		setStepSound(soundTypeMetal);
		setCreativeTab(FireCraftMod.TAB);
		setBlockName(NAME);

		setHarvestLevel("pickaxe", 3);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(FireCraftMod.getAssetLocation(NAME));
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

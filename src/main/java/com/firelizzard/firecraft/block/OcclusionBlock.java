package com.firelizzard.firecraft.block;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.initialization.FireCraftMaterials;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class OcclusionBlock extends Block {
	public static final String NAME = "occlusion";
	
	public OcclusionBlock()
	{
		super(FireCraftMaterials.occlusion);
		setHardness(0.3f);
		setBlockName(NAME);
		setCreativeTab(FireCraftMod.TAB);
		setLightLevel(0.2f);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(FireCraftMod.getAssetLocation(NAME));
    }
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
}

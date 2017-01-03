package com.firelizzard.firecraft.block;

import com.firelizzard.firecraft.FireCraftMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;

public class SilmarilBlock extends BlockDragonEgg {
	public static final String NAME = "silmaril";
//	public static final SimlarilBlockRender RENDERER = new SimlarilBlockRender();
	
	public SilmarilBlock() {
//        super(Material.dragonEgg);
        setCreativeTab(FireCraftMod.TAB);
//        setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
        setLightOpacity(0);
        setLightLevel(1);
        setBlockName(NAME);
    }
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		blockIcon = register.registerIcon(FireCraftMod.MODID + ":" + NAME);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
    @SideOnly(Side.CLIENT)
	@Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
        return true;
    }

	@Override
    public int getRenderType()
    {
		return 27;
//        return RENDERER.getRenderId();
    }
}

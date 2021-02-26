package com.firelizzard.firecraft.block;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.initialization.FireCraftMaterials;
import com.firelizzard.firecraft.tile.StarfieldTile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class StarfieldBlock extends Block implements ITileEntityProvider {
	public static final String NAME = "starfield";

	public StarfieldBlock() {
		super(FireCraftMaterials.starfield);
		setHardness(15.0F);
		setResistance(2000.0F);
		setLightLevel(1);
		setStepSound(soundTypeGlass);
		setCreativeTab(FireCraftMod.TAB);
		setBlockName(NAME);

		setHarvestLevel("pickaxe", 3);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(FireCraftMod.getAssetLocation(NAME));
    }

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new StarfieldTile();
	}

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

	@Override
	@SideOnly(Side.CLIENT)
    public int getRenderType()
    {
        return -1;
    }
}

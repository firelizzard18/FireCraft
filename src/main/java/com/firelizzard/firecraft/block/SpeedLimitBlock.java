package com.firelizzard.firecraft.block;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.initialization.FireCraftMaterials;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class SpeedLimitBlock extends Block {// implements ITileEntityProvider {
	public static final String NAME = "speedlimit";
	
	public static final double SPEED_LIMIT = 5d;
	public static final double PER_TICK_EXPONENT = 1d;
	
	public SpeedLimitBlock()
	{
		super(FireCraftMaterials.speedLimit);
		setHardness(0.3f);
		setBlockName(NAME);
		setCreativeTab(FireCraftMod.TAB);
		setLightOpacity(3);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(FireCraftMod.MODID + ":" + NAME);
    }
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

	
//	@Override
//	public boolean renderAsNormalBlock() {
//		// TODO Auto-generated method stub
//		return super.renderAsNormalBlock();
//	}
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }
	
//	@Override
//	public TileEntity createNewTileEntity(World world, int meta) {
//		return new SpeedLimitTile();
//	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		super.breakBlock(world, x, y, z, block, metadata);
		world.removeTileEntity(x, y, z);
	}
	
	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId_maybe, int eventParam_maybe) {
		return false;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z,	Entity entity) {
		double speed = getSpeed(entity);
		if (speed <= SPEED_LIMIT)
			return;
		
		double scaling = scalingRatio(speed, SPEED_LIMIT);
		System.out.println("Scaling: " + scaling);
		entity.motionX *= scaling;
		entity.motionY *= scaling;
		entity.motionZ *= scaling;
	}
	
	static double getSpeed(Entity entity) {
		return Math.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
	}
	
	static double scalingRatio(double actualSpeed, double targetSpeed) {
		double adjustedTargetSpeed = Math.pow(actualSpeed, 1 - PER_TICK_EXPONENT) * Math.pow(targetSpeed, PER_TICK_EXPONENT);
		return adjustedTargetSpeed / actualSpeed;
	}
}

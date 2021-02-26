package com.firelizzard.firecraft.block;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.initialization.FireCraftFluids;
import com.firelizzard.firecraft.initialization.FireCraftOres;

import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.core.render.IconRegistry;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;

public class SilmarilliumFluidBlock extends BlockFluidCoFHBase {
	public static final int LEVELS = 8;

	public SilmarilliumFluidBlock() {
		super(FireCraftMod.MODID, FireCraftFluids.silmarillium, Material.lava, FireCraftOres.SILMARILLIUM);
		setQuantaPerBlock(LEVELS);
		setTickRate(5);
		setHardness(100F);
		setParticleColor(0.4F, 0.0F, 0.5F);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return FireCraftFluids.silmarillium.getLuminosity();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);
		FireCraftFluids.silmarillium.setStillIcon(IconRegistry.getIcon("Fluid" + StringHelper.titleCase(FireCraftOres.SILMARILLIUM)));
		FireCraftFluids.silmarillium.setFlowingIcon(IconRegistry.getIcon("Fluid" + StringHelper.titleCase(FireCraftOres.SILMARILLIUM), 1));
	}
}

package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.api.Initialization;
import com.firelizzard.firecraft.block.OcclusionBlock;
import com.firelizzard.firecraft.block.PrismiumBlock;
import com.firelizzard.firecraft.block.SecurityStationBlock;
import com.firelizzard.firecraft.block.SilmarilBlock;
import com.firelizzard.firecraft.block.SilmarilliumFluidBlock;
import com.firelizzard.firecraft.block.SilmarilliumStorageBlock;
import com.firelizzard.firecraft.block.SpeedLimitBlock;
import com.firelizzard.firecraft.block.StarfieldBlock;
import com.firelizzard.firecraft.tile.StarfieldTile;
import com.rwtema.extrautils.ExtraUtils;

import cofh.thermalfoundation.fluid.TFFluids;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.TConstructRegistry;

@Initialization(after = {FireCraftFluids.class})
public class FireCraftBlocks {
	public static final SpeedLimitBlock speedlimit = new SpeedLimitBlock();
	public static final SilmarilliumStorageBlock silmarilliumStorage = new SilmarilliumStorageBlock();
	public static final SilmarilBlock silmaril = new SilmarilBlock();
	public static final SecurityStationBlock securityStation = new SecurityStationBlock();
	public static final OcclusionBlock occlusion = new OcclusionBlock();
	public static final PrismiumBlock prismium = new PrismiumBlock();
	public static final StarfieldBlock starfield = new StarfieldBlock();

	public static final SilmarilliumFluidBlock silmarilliumMolten = new SilmarilliumFluidBlock();

	static {
		GameRegistry.registerBlock(speedlimit, SpeedLimitBlock.NAME);
//		GameRegistry.registerTileEntity(SpeedLimitTile.class, SpeedLimitBlock.NAME);
		GameRegistry.registerBlock(silmarilliumMolten, FireCraftOres.SILMARILLIUM + ".molten");
		GameRegistry.registerBlock(silmarilliumStorage, FireCraftOres.SILMARILLIUM + ".storage");
		GameRegistry.registerBlock(silmaril, SilmarilBlock.NAME);
		GameRegistry.registerBlock(securityStation, SecurityStationBlock.NAME);
		GameRegistry.registerBlock(occlusion, OcclusionBlock.NAME);
		GameRegistry.registerBlock(prismium, PrismiumBlock.NAME);
		GameRegistry.registerBlock(starfield, StarfieldBlock.NAME);
		GameRegistry.registerTileEntity(StarfieldTile.class, StarfieldBlock.NAME);
	}

	@Initialization.Post
	public static void recipes() {
		GameRegistry.addRecipe(new ItemStack(speedlimit, 8),
				"AAA",
				"ABA",
				"AAA",
				'A', Blocks.soul_sand,
				'B', ExtraUtils.unstableIngot);
		GameRegistry.addRecipe(new ItemStack(speedlimit, 8),
				"AAA",
				"ABA",
				"AAA",
				'A', Blocks.soul_sand,
				'B', new ItemStack(ExtraUtils.unstableIngot, 1, 2));

		GameRegistry.addRecipe(new ItemStack(silmarilliumStorage),
				"AAA",
				"AAA",
				"AAA",
				'A', FireCraftItems.silmarilliumIngot);

		GameRegistry.addRecipe(new ItemStack(silmaril),
				"ABA",
				"BCB",
				"ABA",
				'A', FireCraftItems.elementiumIngot,
				'B', FireCraftItems.silmarilliumIngot,
				'C', new ItemStack(ExtraUtils.decorative1, 1, ExtraUtils.dec1UnstableBlock));

		GameRegistry.addRecipe(new ItemStack(occlusion, 8),
				"ABA",
				"BCB",
				"ABA",
				'A', Blocks.obsidian,
				'B', Blocks.emerald_block,
				'C', ExtraUtils.unstableIngot);
		GameRegistry.addRecipe(new ItemStack(occlusion, 8),
				"ABA",
				"BCB",
				"ABA",
				'A', Blocks.obsidian,
				'B', Blocks.emerald_block,
				'C', new ItemStack(ExtraUtils.unstableIngot, 1, 2));

		TConstructRegistry.getBasinCasting().addCastingRecipe(
				new ItemStack(starfield),
				new FluidStack(TFFluids.fluidEnder, 50),
				new ItemStack(Blocks.end_stone),
				true,
				100);
	}
}

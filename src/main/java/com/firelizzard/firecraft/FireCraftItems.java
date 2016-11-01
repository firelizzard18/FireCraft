package com.firelizzard.firecraft;

import cofh.thermaldynamics.duct.TDDucts;
import cofh.thermalexpansion.block.TEBlocks;
import cofh.thermalexpansion.block.cell.BlockCell;
import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.item.TEItems;
import cofh.thermalfoundation.item.TFItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;

public class FireCraftItems {
	public static GenericUselessItem useless = new GenericUselessItem();
	public static DestroyerTool destroyer = new DestroyerTool();

	public static ItemStack magnetron = new ItemStack(useless, 1, GenericUselessItem.MAGNETRON_META);
	public static ItemStack destroyerCore = new ItemStack(useless, 1, GenericUselessItem.DESTROYERCORE_META);
	public static ItemStack silmarilliumIngot = new ItemStack(useless, 1, GenericUselessItem.SILMARILLIUMINGOT_META);
	public static ItemStack elementiumIngot = new ItemStack(useless, 1, GenericUselessItem.ELEMENTIUM_META);
	
	public static void register() {
		GameRegistry.registerItem(useless, GenericUselessItem.USELESS_NAME);
		GameRegistry.registerItem(destroyer, DestroyerTool.NAME);
	}
	
	public static void recipes() {
		ItemStack superConductor = TDDucts.energySuperCond.itemStack;
		
		GameRegistry.addRecipe(magnetron,
				"ABA",
				"BCB",
				"ABA",
//				'A', FireCraftBlocks.silmarilliumBlock,
				'A', silmarilliumIngot,
				'B', TEItems.powerCoilGold,
				'C', new ItemStack(superConductor.getItem(), 8, superConductor.getItemDamage()));
		
		GameRegistry.addRecipe(elementiumIngot,
				" A ",
				"BEC",
				" D ",
				'A', TFItems.dustPyrotheum,
				'B', TFItems.dustAerotheum,
				'C', TFItems.dustPetrotheum,
				'D', TFItems.dustCryotheum,
				'E', Items.diamond);
		
		GameRegistry.addRecipe(destroyerCore,
				" A ",
				"BCB",
				"DED",
				'A', magnetron,
				'B', FireCraftBlocks.silmarilliumStorage,
				'C', FireCraftBlocks.silmaril,
				'D', superConductor,
				'E', new ItemStack(TEBlocks.blockDynamo, 1, BlockDynamo.Types.ENERVATION.ordinal()));
		
		GameRegistry.addRecipe(new ItemStack(destroyer),
				"ABA",
				"CDC",
				"EFE",
				'A', Blocks.dirt,
				'B', Items.diamond,
				'C', Items.coal,
				'D', destroyerCore,
				'E', Items.redstone,
				'F', new ItemStack(TEBlocks.blockCell, 1, BlockCell.Types.RESONANT.ordinal()));
		
		TConstructRegistry.getTableCasting().addCastingRecipe(
				silmarilliumIngot,
				new FluidStack(FireCraftFluids.silmarillium, TConstruct.ingotLiquidValue),
				TConstructRegistry.getItemStack("ingotCast"),
				100);
	}
}

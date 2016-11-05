package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.api.Initialization;
import com.firelizzard.firecraft.item.DestroyerTool;
import com.firelizzard.firecraft.item.GenericUselessItem;
import com.rwtema.extrautils.ExtraUtils;

import cofh.thermaldynamics.duct.TDDucts;
import cofh.thermalexpansion.block.TEBlocks;
import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.item.ItemCapacitor;
import cofh.thermalexpansion.item.TEItems;
import cofh.thermalfoundation.item.TFItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;

@Initialization
public class FireCraftItems {
	public static GenericUselessItem useless = new GenericUselessItem();
	public static DestroyerTool destroyer = new DestroyerTool();

	public static ItemStack magnetron = new ItemStack(useless, 1, GenericUselessItem.MAGNETRON_META);
	public static ItemStack destroyerCore = new ItemStack(useless, 1, GenericUselessItem.DESTROYERCORE_META);
	public static ItemStack silmarilliumIngot = new ItemStack(useless, 1, GenericUselessItem.SILMARILLIUMINGOT_META);
	public static ItemStack elementiumIngot = new ItemStack(useless, 1, GenericUselessItem.ELEMENTIUM_META);

	static {
		GameRegistry.registerItem(useless, GenericUselessItem.USELESS_NAME);
		GameRegistry.registerItem(destroyer, DestroyerTool.NAME);
	}

	@Initialization.Post
	public static void recipes() {
		ItemStack superConductor = TDDucts.energySuperCond.itemStack;
		
		GameRegistry.addRecipe(magnetron,
				"ABA",
				"BCB",
				"ABA",
				'A', silmarilliumIngot,
				'B', TEItems.powerCoilGold,
				'C', superConductor);
		
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
				"CDE",
				"FGH",
				'A', Items.diamond,
				'B', ExtraUtils.ethericSword,
				'C', ExtraUtils.erosionShovel,
				'D', destroyerCore,
				'E', ExtraUtils.destructionPickaxe,
				'F', ExtraUtils.precisionShears,
				'G', new ItemStack(TEItems.itemCapacitor, 1, ItemCapacitor.Types.RESONANT.ordinal()),
				'H', ExtraUtils.healingAxe);
		
		TConstructRegistry.getTableCasting().addCastingRecipe(
				silmarilliumIngot,
				new FluidStack(FireCraftFluids.silmarillium, TConstruct.ingotLiquidValue),
				TConstructRegistry.getItemStack("ingotCast"),
				100);
	}
}

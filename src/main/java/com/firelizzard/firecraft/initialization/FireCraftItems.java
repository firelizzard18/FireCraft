package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.api.Initialization;
import com.firelizzard.firecraft.item.DestroyerTool;
import com.firelizzard.firecraft.item.GenericUselessItem;
import com.rwtema.extrautils.ExtraUtils;

import cofh.lib.util.helpers.ItemHelper;
import cofh.thermaldynamics.duct.TDDucts;
import cofh.thermalexpansion.block.TEBlocks;
import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.item.ItemCapacitor;
import cofh.thermalexpansion.item.TEItems;
import cofh.thermalexpansion.util.crafting.FurnaceManager;
import cofh.thermalexpansion.util.crafting.PulverizerManager;
import cofh.thermalexpansion.util.crafting.SmelterManager;
import cofh.thermalfoundation.item.TFItems;
import cpw.mods.fml.common.registry.GameRegistry;
import mods.railcraft.common.items.RailcraftToolItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;

@Initialization
public class FireCraftItems {
	public final static GenericUselessItem useless = new GenericUselessItem();
	public final static DestroyerTool destroyer = new DestroyerTool();

	public final static ItemStack[] useless_stacks = new ItemStack[GenericUselessItem.Items.values().length];
	public final static ItemStack magnetron, destroyerCore, silmarilliumIngot, elementiumIngot, carborundum, pulverizedCoke;

	static {
		for (int i = 0; i < useless_stacks.length; i++)
			useless_stacks[i] = new ItemStack(useless, 1, GenericUselessItem.Items.values()[i].getMeta());

		magnetron = GenericUselessItem.Items.MAGNETRON.getStack();
		destroyerCore = GenericUselessItem.Items.DESTROYER_CORE.getStack();
		silmarilliumIngot = GenericUselessItem.Items.SILMARILLIUM_INGOT.getStack();
		elementiumIngot = GenericUselessItem.Items.ELEMENTIUM.getStack();
		carborundum = GenericUselessItem.Items.CARBORUNDUM.getStack();
		pulverizedCoke = GenericUselessItem.Items.PULVERIZED_COKE.getStack();

		GameRegistry.registerItem(useless, GenericUselessItem.Items.USELESS.getName());
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
				'B', ExtraUtils.lawSword,
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

		PulverizerManager.addRecipe(2400, RailcraftToolItems.getCoalCoke(), pulverizedCoke);
		SmelterManager.addAlloyRecipe(2400, "sand", 1, "dustCoke", 1, carborundum);
		SmelterManager.addAlloyRecipe(2400, "sand", 1, "dustCoal", 1, carborundum);
		SmelterManager.addAlloyRecipe(2400, "sand", 1, "dustCharcoal", 1, carborundum);

		ItemStack ingotSteel = ItemHelper.cloneStack(OreDictionary.getOres("ingotSteel").get(0), 1);
		SmelterManager.addAlloyRecipe(4000, "dustCoke", 1, "dustSteel", 1, ingotSteel);
		SmelterManager.addAlloyRecipe(4000, "dustCoke", 1, "dustIron", 1, ingotSteel);
		SmelterManager.addAlloyRecipe(4000, "dustCoke", 1, "ingotIron", 1, ingotSteel);

		ItemStack ingotGraphite = ItemHelper.cloneStack(OreDictionary.getOres("ingotGraphite").get(0), 1);
		FurnaceManager.addRecipe(4000, carborundum, ingotGraphite, false);
	}
}

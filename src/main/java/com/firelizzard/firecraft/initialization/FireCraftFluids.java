package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.api.Initialization;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.library.crafting.Smeltery;

@Initialization
public class FireCraftFluids {
	public static Fluid silmarillium = new Fluid(FireCraftOres.SILMARILLIUM + ".molten")
			.setLuminosity(15)
			.setDensity(900)
			.setViscosity(2000)
			.setTemperature(750);

	static {
		FluidRegistry.registerFluid(silmarillium);
	}

	@Initialization.Post
	public static void recipes() {
		Smeltery.addAlloyMixing(new FluidStack(FireCraftFluids.silmarillium, TConstruct.ingotLiquidValue),
				new FluidStack(FluidRegistry.getFluid("enderium.molten"), TConstruct.ingotLiquidValue),
				new FluidStack(FluidRegistry.getFluid("lumium.molten"), TConstruct.ingotLiquidValue),
				new FluidStack(FluidRegistry.getFluid("signalum.molten"), TConstruct.ingotLiquidValue),
//				new FluidStack(FluidRegistry.getFluid("mithril.molten"), TConstruct.ingotLiquidValue),
				new FluidStack(FluidRegistry.getFluid("molten.unstableingots"), TConstruct.ingotLiquidValue),
				new FluidStack(FluidRegistry.getFluid("bedrockium.molten"), TConstruct.ingotLiquidValue),
				new FluidStack(FluidRegistry.getFluid("manyullyn.molten"), TConstruct.ingotLiquidValue),
				new FluidStack(FluidRegistry.getFluid("emerald.liquid"), 640));
	}
}

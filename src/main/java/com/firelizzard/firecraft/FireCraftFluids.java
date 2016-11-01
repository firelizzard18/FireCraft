package com.firelizzard.firecraft;

import cofh.thermalfoundation.fluid.TFFluids;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.library.crafting.Smeltery;

public class FireCraftFluids {
	public static Fluid silmarillium = new Fluid(FireCraftOres.SILMARILLIUM + ".molten")
			.setLuminosity(15)
			.setDensity(900)
			.setViscosity(2000)
			.setTemperature(750);
	
	public static void register() {
		FluidRegistry.registerFluid(silmarillium);
	}
	
	public static void recipes() {
		Smeltery.addAlloyMixing(new FluidStack(FireCraftFluids.silmarillium, TConstruct.ingotLiquidValue*4),
				new FluidStack(FluidRegistry.getFluid("enderium.molten"), TConstruct.ingotLiquidValue),
				new FluidStack(FluidRegistry.getFluid("lumium.molten"), TConstruct.ingotLiquidValue),
				new FluidStack(FluidRegistry.getFluid("signalum.molten"), TConstruct.ingotLiquidValue),
				new FluidStack(TFFluids.fluidPetrotheum, TConstruct.ingotLiquidValue/4),
				new FluidStack(TFFluids.fluidCryotheum, TConstruct.ingotLiquidValue/4),
				new FluidStack(TFFluids.fluidAerotheum, TConstruct.ingotLiquidValue/4),
				new FluidStack(TFFluids.fluidPyrotheum, TConstruct.ingotLiquidValue/4));
	}
}

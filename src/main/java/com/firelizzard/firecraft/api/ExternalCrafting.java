package com.firelizzard.firecraft.api;

import cofh.thermalexpansion.api.crafting.IChargerHandler;
import cofh.thermalexpansion.api.crafting.ICrucibleHandler;
import cofh.thermalexpansion.api.crafting.IFurnaceHandler;
import cofh.thermalexpansion.api.crafting.IInsolatorHandler;
import cofh.thermalexpansion.api.crafting.IPulverizerHandler;
import cofh.thermalexpansion.api.crafting.ISawmillHandler;
import cofh.thermalexpansion.api.crafting.ISmelterHandler;
import cofh.thermalexpansion.api.crafting.ITransposerHandler;

@Initialization(after = {
	cofh.thermalexpansion.util.APIWarden.class
})
public class ExternalCrafting {
	public final static IFurnaceHandler furnace = cofh.thermalexpansion.api.crafting.CraftingHandlers.furnace;
	public final static IPulverizerHandler pulverizer = cofh.thermalexpansion.api.crafting.CraftingHandlers.pulverizer;
	public final static ISawmillHandler sawmill = cofh.thermalexpansion.api.crafting.CraftingHandlers.sawmill;
	public final static ISmelterHandler smelter = cofh.thermalexpansion.api.crafting.CraftingHandlers.smelter;
	public final static ICrucibleHandler crucible = cofh.thermalexpansion.api.crafting.CraftingHandlers.crucible;
	public final static ITransposerHandler transposer = cofh.thermalexpansion.api.crafting.CraftingHandlers.transposer;
	public final static IChargerHandler charger = cofh.thermalexpansion.api.crafting.CraftingHandlers.charger;
	public final static IInsolatorHandler insolator = cofh.thermalexpansion.api.crafting.CraftingHandlers.insolator;
}

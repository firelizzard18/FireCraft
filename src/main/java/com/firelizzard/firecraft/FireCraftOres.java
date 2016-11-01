package com.firelizzard.firecraft;

import cofh.lib.util.helpers.StringHelper;
import net.minecraftforge.oredict.OreDictionary;

public class FireCraftOres {
	public static final String SILMARILLIUM = "silmarillium";
	
	public static void register() {
		OreDictionary.registerOre("ingot" + StringHelper.titleCase(SILMARILLIUM), FireCraftItems.silmarilliumIngot);
		OreDictionary.registerOre("block" + StringHelper.titleCase(SILMARILLIUM), FireCraftBlocks.silmarilliumStorage);
	}
	
	public static void recipes() {
	}
}

package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.api.Initialization;

import cofh.lib.util.helpers.StringHelper;
import net.minecraftforge.oredict.OreDictionary;

@Initialization(after={FireCraftItems.class})
public class FireCraftOres {
	public static final String SILMARILLIUM = "silmarillium";
	
	static {
		OreDictionary.registerOre("ingot" + StringHelper.titleCase(SILMARILLIUM), FireCraftItems.silmarilliumIngot);
		OreDictionary.registerOre("block" + StringHelper.titleCase(SILMARILLIUM), FireCraftBlocks.silmarilliumStorage);
	}

//	@Initialization.Post
	public static void recipes() {
	}
}

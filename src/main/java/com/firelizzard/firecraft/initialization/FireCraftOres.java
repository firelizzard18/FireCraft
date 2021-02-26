package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.api.Initialization;

import cofh.lib.util.helpers.StringHelper;
import net.minecraftforge.oredict.OreDictionary;

@Initialization(after={FireCraftItems.class})
public class FireCraftOres {
	public static final String AFFIX_DUST = "dust";
	public static final String AFFIX_INGOT = "ingot";
	public static final String AFFIX_BLOCK = "block";
	public static final String AFFIX_NUGGET = "nugget";

	public static final String SILMARILLIUM = "silmarillium";
	public static final String COKE = "coke";
	public static final String CARBORUNDUM = "carborundum";

	static {
		OreDictionary.registerOre(AFFIX_INGOT + StringHelper.titleCase(SILMARILLIUM), FireCraftItems.silmarilliumIngot);
		OreDictionary.registerOre(AFFIX_NUGGET + StringHelper.titleCase(SILMARILLIUM), FireCraftItems.silmarilliumNugget);
		OreDictionary.registerOre(AFFIX_BLOCK + StringHelper.titleCase(SILMARILLIUM), FireCraftBlocks.silmarilliumStorage);
		OreDictionary.registerOre(AFFIX_DUST + StringHelper.titleCase(COKE), FireCraftItems.pulverizedCoke);
	}

//	@Initialization.Post
	public static void recipes() {
	}
}

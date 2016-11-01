package com.firelizzard.firecraft;

//import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod(modid = FireCraftMod.MODID, version = FireCraftMod.VERSION)
public class FireCraftMod {
	public static final String MODID = "firecraft";
	public static final String VERSION = "1.0";
	
	public static final int RENDERERID_1 = 1;
	
	public static final CreativeTabs tab = new CreativeTabs("firecraft") {
		@Override
		public Item getTabIconItem() {
			return Items.diamond;
		}
	};
	
	@SidedProxy(
		clientSide = "com.firelizzard.firecraft.FireCraftProxy$Client",
		serverSide = "com.firelizzard.firecraft.FireCraftProxy$Server")
	public static FireCraftProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		
		FireCraftFluids.register();
		FireCraftBlocks.register();
		FireCraftItems.register();
		FireCraftOres.register();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
		
		FireCraftFluids.recipes();
		FireCraftBlocks.recipes();
		FireCraftItems.recipes();
		FireCraftOres.recipes();
	}
}
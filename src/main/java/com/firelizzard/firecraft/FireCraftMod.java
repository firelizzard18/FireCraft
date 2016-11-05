package com.firelizzard.firecraft;

import com.firelizzard.firecraft.initialization.FireCraftProxy;

import cofh.mod.BaseMod;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod(modid = FireCraftMod.MODID, version = FireCraftMod.VERSION)
public class FireCraftMod extends BaseMod {
	public static final String MODID = "firecraft";
	public static final String VERSION = "1.0";
	
	public static final int RENDERERID_1 = 1;
	
	private static final FireCraftMod inst = new FireCraftMod();
	
	public static final CreativeTabs tab = new CreativeTabs("firecraft") {
		@Override
		public Item getTabIconItem() {
			return Items.diamond;
		}
	};
	
	@SidedProxy(
		clientSide = "com.firelizzard.firecraft.initialization.FireCraftProxy$Client",
		serverSide = "com.firelizzard.firecraft.initialization.FireCraftProxy$Server")
	public static FireCraftProxy proxy;
	

	
	public static String getAssetLocation(String name) {
		return MODID + ":" + name;
	}
	
	public static String getTextureLocation(String name) {
		return getAssetLocation("textures/" + name);
	}
	
	public static String getGuiTextureLocation(String name) {
		return getTextureLocation("gui/" + name);
	}
	
	
	@Mod.InstanceFactory
	public static FireCraftMod instance() {
		return inst;
	}
	
	private final Intitializer init = new Intitializer();
	
	private FireCraftMod() {}

	@Override
	public String getModId() {
		return MODID;
	}

	@Override
	public String getModName() {
		return "FireCraft";
	}

	@Override
	public String getModVersion() {
		return VERSION;
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		init.callClassInitializers();
		
		proxy.preInit();
		init.callPreInitializers();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		init.callInitializers();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
		init.callPostInitializers();
	}
}
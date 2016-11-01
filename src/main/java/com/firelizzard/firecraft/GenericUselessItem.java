package com.firelizzard.firecraft;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GenericUselessItem extends Item {
	public static int COUNT = 5;
	
	public static String USELESS_NAME = "useless";
	public static String MAGNETRON_NAME = "magnetron";
	public static String DESTROYERCORE_NAME = "destroyerCore";
	public static String SILMARILLIUMINGOT_NAME = FireCraftOres.SILMARILLIUM + "Ingot";
	public static String ELEMENTIUM_NAME = "elementium";

	public static int USELESS_META = 0;
	public static int MAGNETRON_META = 1;
	public static int DESTROYERCORE_META = 2;
	public static int SILMARILLIUMINGOT_META = 3;
	public static int ELEMENTIUM_META = 4;
	
	static String[] NAMES = new String[COUNT];
	
	static {
		NAMES[USELESS_META] = USELESS_NAME;
		NAMES[MAGNETRON_META] = MAGNETRON_NAME;
		NAMES[DESTROYERCORE_META] = DESTROYERCORE_NAME;
		NAMES[SILMARILLIUMINGOT_META] = SILMARILLIUMINGOT_NAME;
		NAMES[ELEMENTIUM_META] = ELEMENTIUM_NAME;
	}

	IIcon[] icons = new IIcon[COUNT];
  
	public GenericUselessItem() {
		super();
		setMaxStackSize(64);
		setCreativeTab(FireCraftMod.tab);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		for (int i = 0; i < COUNT; i++)
			icons[i] = register.registerIcon(FireCraftMod.MODID + ":" + NAMES[i]);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItem() != this || stack.getItemDamage() >= COUNT)
			return super.getUnlocalizedName(stack);
		
		return NAMES[stack.getItemDamage()];
	}
	
	@Override
	public IIcon getIconFromDamage(int damage) {
		if (damage >= COUNT)
			return super.getIconFromDamage(damage);
		
		return icons[damage];
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, @SuppressWarnings("rawtypes") List list) {
		for (int i = 1; i < COUNT; i++)
			list.add(new ItemStack(item, 1, i));
	}
}

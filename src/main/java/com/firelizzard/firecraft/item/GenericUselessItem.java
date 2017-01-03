package com.firelizzard.firecraft.item;

import java.util.List;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.initialization.FireCraftItems;
import com.firelizzard.firecraft.initialization.FireCraftOres;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GenericUselessItem extends Item {
	public static enum Items {
		USELESS("uselesss"),
		MAGNETRON("magnetron"),
		DESTROYER_CORE("destroyerCore"),
		SILMARILLIUM_INGOT(FireCraftOres.SILMARILLIUM + "Ingot"),
		ELEMENTIUM("elementium"),
		CARBORUNDUM(FireCraftOres.CARBORUNDUM + "Gem"),
		PULVERIZED_COKE("pulverized" + StringHelper.titleCase(FireCraftOres.COKE));
		
		private final String name;
		
		Items(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public String getName() {
			return name;
		}
		
		public int getMeta() {
			return ordinal();
		}
		
		public ItemStack getStack() {
			return FireCraftItems.useless_stacks[ordinal()];
		}
	}
	
	public final static int COUNT = Items.values().length;

	IIcon[] icons = new IIcon[COUNT];
  
	public GenericUselessItem() {
		super();
		setMaxStackSize(64);
		setCreativeTab(FireCraftMod.TAB);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		for (int i = 0; i < COUNT; i++)
			icons[i] = register.registerIcon(FireCraftMod.MODID + ":" + Items.values()[i]);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItem() != this || stack.getItemDamage() >= COUNT)
			return super.getUnlocalizedName(stack);
		
		return "item." + Items.values()[stack.getItemDamage()].getName();
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

package com.firelizzard.firecraft;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class DestroyerTool extends Item {
	public static final String NAME = "destroyer";
	
	public DestroyerTool() {
		super();
		setMaxStackSize(1);
		setCreativeTab(FireCraftMod.tab);
		setUnlocalizedName(NAME);
	}
	
	@Override
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon(FireCraftMod.MODID + ":" + NAME);
	}
}

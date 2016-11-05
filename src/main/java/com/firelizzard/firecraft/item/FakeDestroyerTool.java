package com.firelizzard.firecraft.item;

import com.firelizzard.firecraft.FireCraftMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class FakeDestroyerTool extends Item {
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon(FireCraftMod.MODID + ":" + DestroyerTool.NAME);
	}
}

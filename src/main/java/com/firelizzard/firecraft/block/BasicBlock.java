package com.firelizzard.firecraft.block;

import com.firelizzard.firecraft.FireCraftMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public abstract class BasicBlock extends Block {
	String textureName;
	IIcon icon;
	
	protected BasicBlock(Material mat, String textureName)
	{
		super(mat);
		this.textureName = textureName;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icon = iconRegister.registerIcon(FireCraftMod.MODID + ":" + this.textureName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        return this.icon;
    }
}

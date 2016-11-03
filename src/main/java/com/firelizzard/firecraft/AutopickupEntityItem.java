package com.firelizzard.firecraft;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AutopickupEntityItem extends EntityItem {
	EntityPlayer player;
	
    public AutopickupEntityItem(World world, EntityPlayer player, double x, double y, double z, ItemStack drop)
    {
    	super(world, x, y, z, drop);
    	
    	this.player = player;
    }
    
    @Override
    public void onUpdate() {
    	if (player != null) {
    		if (player.inventory.addItemStackToInventory(getEntityItem()))
    			kill();
    		else
    			player = null;
    	} else
    		super.onUpdate();
    }
}

package com.firelizzard.firecraft.events;

import com.firelizzard.firecraft.block.SecurityStationBlock;

import cofh.lib.util.helpers.SecurityHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

@SideOnly(Side.CLIENT)
public class ItemToolTipHandler {
	public static final ItemToolTipHandler INSTANCE = new ItemToolTipHandler();

	private ItemToolTipHandler() { }

    @SubscribeEvent
    public void addSecuredItemTooltip(ItemTooltipEvent event) {
    	if (SecurityStationBlock.isUnsafeToSecure(event.itemStack))
    		return;

    	if (SecurityStationBlock.hasSecureRecipe(Minecraft.getMinecraft().theWorld, event.itemStack))
    		return;

    	if (!SecurityHelper.isSecure(event.itemStack))
    		return;

    	SecurityHelper.addOwnerInformation(event.itemStack, event.toolTip);
    	SecurityHelper.addAccessInformation(event.itemStack, event.toolTip);
    }
}

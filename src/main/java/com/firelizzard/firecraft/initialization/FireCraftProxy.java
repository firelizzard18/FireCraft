package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.entity.PlasmaBoltEntity;
import com.firelizzard.firecraft.events.ItemToolTipHandler;
import com.firelizzard.firecraft.rendering.DestroyerRender;
import com.firelizzard.firecraft.rendering.PlasmaBoltRender;
import com.firelizzard.firecraft.rendering.RenderStarfield;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

public abstract class FireCraftProxy {
	public void preInit() { }
	public void init() { }
	public void postInit() { }

	public static class Client extends FireCraftProxy {
		@Override
		public void init() {
			RenderingRegistry.registerEntityRenderingHandler(PlasmaBoltEntity.class, new PlasmaBoltRender());
			MinecraftForgeClient.registerItemRenderer(FireCraftItems.destroyer, new DestroyerRender());
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(FireCraftBlocks.starfield), RenderStarfield.instance);

			MinecraftForge.EVENT_BUS.register(ItemToolTipHandler.INSTANCE);
		}

		@SideOnly(Side.CLIENT)
		@SubscribeEvent
		public void initializeIcons(TextureStitchEvent.Post event) {
			RenderStarfield.initialize();
		}
	}

	public static class Server extends FireCraftProxy {

	}
}

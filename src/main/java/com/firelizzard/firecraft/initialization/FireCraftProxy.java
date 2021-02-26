package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.entity.PlasmaBoltEntity;
import com.firelizzard.firecraft.events.ItemToolTipHandler;
import com.firelizzard.firecraft.rendering.DestroyerRender;
import com.firelizzard.firecraft.rendering.PlasmaBoltRender;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
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

//			RenderingRegistry.registerBlockHandler(SilmarilliumStorageBlock.RENDER);
			MinecraftForge.EVENT_BUS.register(ItemToolTipHandler.INSTANCE);
		}
	}

	public static class Server extends FireCraftProxy {

	}
}

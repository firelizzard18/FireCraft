package com.firelizzard.firecraft;

public abstract class FireCraftProxy {
	public void preInit() { }
	public void init() { }
	public void postInit() { }
	
	public static class Client extends FireCraftProxy {
		@Override
		public void init() {
//			RenderingRegistry.registerBlockHandler(SilmarilBlock.RENDERID, new FireCraftRenderingHandler());
		}
	}
	
	public static class Server extends FireCraftProxy {
		
	}
}

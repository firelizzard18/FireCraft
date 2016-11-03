package com.firelizzard.firecraft;

import logisticspipes.LogisticsPipes;

public class FireCraftLogisticsIntegration {
//	public static final SecureItemSinkModule module = new SecureItemSinkModule();
	
	public static void postInit() {
		LogisticsPipes.ModuleItem.registerModule(1000, SecureItemSinkModule.class);
	}
}

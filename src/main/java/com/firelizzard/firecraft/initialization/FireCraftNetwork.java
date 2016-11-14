package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.api.Initialization;
import com.firelizzard.firecraft.network.PlasmaBoltPacket;

import cpw.mods.fml.relauncher.Side;

@Initialization
public class FireCraftNetwork {
	@Initialization.During
	public static void register() {
		int id = 0;
		FireCraftMod.NETWORK.registerMessage(PlasmaBoltPacket.Handler.class, PlasmaBoltPacket.class, id++, Side.SERVER);
//		FireCraftMod.NETWORK.registerMessage(PlasmaBoltPacket.Handler.class, PlasmaBoltPacket.class, id++, Side.CLIENT);
	}
}

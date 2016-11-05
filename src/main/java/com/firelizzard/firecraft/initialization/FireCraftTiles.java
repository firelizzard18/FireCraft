package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.api.Initialization;
import com.firelizzard.firecraft.block.SecurityStationBlock;
import com.firelizzard.firecraft.gui.FireCraftFmlGuiHandler;
import com.firelizzard.firecraft.tile.SecurityStationTile;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Initialization
public class FireCraftTiles {
	public static final FireCraftFmlGuiHandler guiHandler = new FireCraftFmlGuiHandler();
//	public static final SecurityStationTile securityStation = new SecurityStationTile();

	static {
		GameRegistry.registerTileEntity(SecurityStationTile.class, SecurityStationBlock.NAME);
		NetworkRegistry.INSTANCE.registerGuiHandler(FireCraftMod.instance(), guiHandler);
	}
}

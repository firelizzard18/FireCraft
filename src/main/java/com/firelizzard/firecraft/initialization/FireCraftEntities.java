package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.api.Initialization;
import com.firelizzard.firecraft.entity.PlasmaBoltEntity;

import cpw.mods.fml.common.registry.EntityRegistry;

@Initialization
public class FireCraftEntities {
	@Initialization.During
	public static void register() {
		EntityRegistry.registerModEntity(
				PlasmaBoltEntity.class, "Plasma Bolt",
				FireCraftMod.ENTITYID_PLASMA_BOLT, FireCraftMod.instance(),
				64, 20, true);
	}
}

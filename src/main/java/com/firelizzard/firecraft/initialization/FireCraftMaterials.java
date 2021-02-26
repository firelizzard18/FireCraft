package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.api.Initialization;
import com.firelizzard.firecraft.material.OcclusionMaterial;
import com.firelizzard.firecraft.material.PrismiumMaterial;
import com.firelizzard.firecraft.material.SilmarilliumMaterial;
import com.firelizzard.firecraft.material.SpeedLimitMaterial;
import com.firelizzard.firecraft.material.StarfieldMaterial;

@Initialization
public final class FireCraftMaterials {
	public static final SpeedLimitMaterial speedLimit = new SpeedLimitMaterial();
	public static final OcclusionMaterial occlusion = new OcclusionMaterial();
	public static final SilmarilliumMaterial silmarillium = new SilmarilliumMaterial();
	public static final PrismiumMaterial prismium = new PrismiumMaterial();
	public static final StarfieldMaterial starfield = new StarfieldMaterial();
}

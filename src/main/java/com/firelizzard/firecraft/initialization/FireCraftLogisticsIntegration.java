package com.firelizzard.firecraft.initialization;

import com.firelizzard.firecraft.api.Initialization;
import com.firelizzard.firecraft.gui.SecureItemSinkInHand;
import com.firelizzard.firecraft.gui.SecureItemSinkSlot;
import com.firelizzard.firecraft.logistics.DisenchantModule;
import com.firelizzard.firecraft.logistics.EnergyItemSinkModule;
import com.firelizzard.firecraft.logistics.SecureItemSinkModule;
import com.firelizzard.firecraft.network.SecureItemSinkMode;
import com.firelizzard.firecraft.network.SecureItemSinkPacket;

import logisticspipes.LogisticsPipes;
import logisticspipes.network.NewGuiHandler;
import logisticspipes.network.PacketHandler;
import logisticspipes.network.abstractguis.GuiProvider;
import logisticspipes.network.abstractpackets.ModernPacket;

@Initialization
public class FireCraftLogisticsIntegration {
//	public static final SecureItemSinkModule module = new SecureItemSinkModule();

	@Initialization.Post
	public static void postInit() {
		LogisticsPipes.ModuleItem.registerModule(1000, SecureItemSinkModule.class);
		LogisticsPipes.ModuleItem.registerModule(1001, EnergyItemSinkModule.class);
		LogisticsPipes.ModuleItem.registerModule(1002, DisenchantModule.class);



		int cid1 = NewGuiHandler.guilist.size();

		GuiProvider inst1 = new SecureItemSinkInHand(cid1++);
		NewGuiHandler.guilist.add(inst1);
		NewGuiHandler.guimap.put(SecureItemSinkInHand.class, inst1);

		inst1 = new SecureItemSinkSlot(cid1++);
		NewGuiHandler.guilist.add(inst1);
		NewGuiHandler.guimap.put(SecureItemSinkSlot.class, inst1);



		int cid2 = PacketHandler.packetlist.size();

		ModernPacket inst2 = new SecureItemSinkMode(cid2++);
		PacketHandler.packetlist.add(inst2);
		PacketHandler.packetmap.put(SecureItemSinkMode.class, inst2);

		inst2 = new SecureItemSinkPacket(cid2++);
		PacketHandler.packetlist.add(inst2);
		PacketHandler.packetmap.put(SecureItemSinkPacket.class, inst2);
	}
}

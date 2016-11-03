package com.firelizzard.firecraft.network;

import com.firelizzard.firecraft.logistics.SecureItemSinkModule;

import logisticspipes.network.abstractpackets.ModernPacket;
import net.minecraft.entity.player.EntityPlayer;

public class SecureItemSinkMode extends SecureModulePacket {
	public SecureItemSinkMode(int id) {
		super(id);
	}

	@Override
	public ModernPacket template() {
		return new SecureItemSinkMode(getId());
	}

	@Override
	public void processPacket(EntityPlayer player) {
		SecureItemSinkModule receiver = this.getLogisticsModule(player, SecureItemSinkModule.class);
		if (receiver == null)
			return;
		receiver.setOwnerUUID(getOwnerUUID());
		receiver.setOwnerName(getOwnerName());
	}
}
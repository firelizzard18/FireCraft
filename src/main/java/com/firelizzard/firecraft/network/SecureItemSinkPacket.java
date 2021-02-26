package com.firelizzard.firecraft.network;

import com.firelizzard.firecraft.logistics.SecureItemSinkModule;

import logisticspipes.network.PacketHandler;
import logisticspipes.network.abstractpackets.ModernPacket;
import logisticspipes.proxy.MainProxy;
import net.minecraft.entity.player.EntityPlayer;

public class SecureItemSinkPacket extends SecureModulePacket {
	public SecureItemSinkPacket(int id) {
		super(id);
	}

	@Override
	public ModernPacket template() {
		return new SecureItemSinkPacket(getId());
	}

	@Override
	public void processPacket(EntityPlayer player) {
		final SecureItemSinkModule module = getLogisticsModule(player, SecureItemSinkModule.class);
		if (module == null) {
			return;
		}
		module.setOwnerUUID(getOwnerUUID());
		module.setOwnerName(getOwnerName());

		SecureItemSinkMode packet = PacketHandler.getPacket(SecureItemSinkMode.class);
		packet.setOwnerUUID(module.getOwnerUUID());
		packet.setOwnerName(module.getOwnerName());
		packet.setPacketPos(this);
		MainProxy.sendPacketToPlayer(packet, player);
	}
}

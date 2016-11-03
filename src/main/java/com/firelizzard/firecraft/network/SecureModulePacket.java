package com.firelizzard.firecraft.network;

import java.io.IOException;
import java.util.UUID;

import logisticspipes.network.LPDataInputStream;
import logisticspipes.network.LPDataOutputStream;
import logisticspipes.network.abstractpackets.ModuleCoordinatesPacket;

public abstract class SecureModulePacket extends ModuleCoordinatesPacket {
	private UUID ownerUUID;
	private String ownerName;
	
	public SecureModulePacket(int id) {
		super(id);
	}
	
	public UUID getOwnerUUID() {
		return ownerUUID;
	}
	
	public void setOwnerUUID(UUID ownerUUID) {
		this.ownerUUID = ownerUUID;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	@Override
	public void writeData(LPDataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeLong(ownerUUID == null ? 0 : ownerUUID.getLeastSignificantBits());
		data.writeLong(ownerUUID == null ? 0 : ownerUUID.getMostSignificantBits());
		data.writeByteArray(ownerName == null ? new byte[0] : ownerName.getBytes());
	}
	
	@Override
	public void readData(LPDataInputStream data) throws IOException {
		super.readData(data);
		long lsb = data.readLong();
		long msb = data.readLong();
		byte[] bytes = data.readByteArray();
		
		if (lsb == 0 && msb == 0)
			ownerUUID = null;
		else
			ownerUUID = new UUID(msb, lsb);
		
		if (bytes.length == 0)
			ownerName = null;
		else
			ownerName = new String(bytes);
	}
}

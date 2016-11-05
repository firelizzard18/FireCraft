package com.firelizzard.firecraft.gui;

import java.io.IOException;
import java.util.UUID;

import com.firelizzard.firecraft.logistics.SecureItemSinkModule;

import logisticspipes.network.LPDataInputStream;
import logisticspipes.network.LPDataOutputStream;
import logisticspipes.network.abstractguis.GuiProvider;
import logisticspipes.network.abstractguis.ModuleCoordinatesGuiProvider;
import logisticspipes.utils.gui.DummyContainer;
import net.minecraft.entity.player.EntityPlayer;

public class SecureItemSinkSlot extends ModuleCoordinatesGuiProvider {
	private UUID ownerUUID;
	private String ownerName;

	public SecureItemSinkSlot(int id) {
		super(id);
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

	@Override
	public Object getClientGui(EntityPlayer player) {
		SecureItemSinkModule module = this.getLogisticsModule(player.getEntityWorld(), SecureItemSinkModule.class);
		if (module == null) {
			return null;
		}
		module.setOwnerUUID(ownerUUID);
		module.setOwnerName(ownerName);
		return new SecureItemSinkGui(player, module);
	}

	@Override
	public DummyContainer getContainer(EntityPlayer player) {
		SecureItemSinkModule module = this.getLogisticsModule(player.getEntityWorld(), SecureItemSinkModule.class);
		if (module == null) {
			return null;
		}
		return new DummyContainer(player.inventory, null);
	}

	@Override
	public GuiProvider template() {
		return new SecureItemSinkSlot(getId());
	}
}

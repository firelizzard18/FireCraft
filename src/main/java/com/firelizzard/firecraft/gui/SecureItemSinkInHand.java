package com.firelizzard.firecraft.gui;

import com.firelizzard.firecraft.logistics.SecureItemSinkModule;

import logisticspipes.modules.abstractmodules.LogisticsModule;
import logisticspipes.network.abstractguis.GuiProvider;
import logisticspipes.network.abstractguis.ModuleInHandGuiProvider;
import logisticspipes.utils.gui.DummyContainer;
import logisticspipes.utils.gui.DummyModuleContainer;
import net.minecraft.entity.player.EntityPlayer;

public class SecureItemSinkInHand extends ModuleInHandGuiProvider {
	public SecureItemSinkInHand(int id) {
		super(id);
	}

	@Override
	public Object getClientGui(EntityPlayer player) {
		LogisticsModule module = getLogisticsModule(player);
		if (!(module instanceof SecureItemSinkModule)) {
			return null;
		}
		return new SecureItemSinkGui(player, (SecureItemSinkModule)module);
	}

	@Override
	public DummyContainer getContainer(EntityPlayer player) {
		DummyContainer dummy = new DummyModuleContainer(player, getInvSlot());
		if (!(((DummyModuleContainer) dummy).getModule() instanceof SecureItemSinkModule)) {
			return null;
		}
		return dummy;
	}

	@Override
	public GuiProvider template() {
		return new SecureItemSinkInHand(getId());
	}
}

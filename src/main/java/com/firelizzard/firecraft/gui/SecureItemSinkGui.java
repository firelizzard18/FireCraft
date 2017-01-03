package com.firelizzard.firecraft.gui;

import org.lwjgl.opengl.GL11;

import com.firelizzard.firecraft.logistics.SecureItemSinkModule;
import com.firelizzard.firecraft.network.SecureItemSinkPacket;
import com.mojang.authlib.GameProfile;

import logisticspipes.gui.modules.ModuleBaseGui;
import logisticspipes.network.PacketHandler;
import logisticspipes.proxy.MainProxy;
import logisticspipes.utils.gui.DummyContainer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SecureItemSinkGui extends ModuleBaseGui {
	private EntityPlayer player;
	private SecureItemSinkModule module;
	
	public SecureItemSinkGui(EntityPlayer player, SecureItemSinkModule module) {
		super(new DummyContainer(player.inventory, null), module);
		this.player = player;
		this.module = module;
		xSize = 160;
		ySize = 60;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();

		buttonList.add(new GuiButton(0, guiLeft + 60, guiTop + 30, 40, 20, "Claim"));
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton) {
		GameProfile profile = player.getGameProfile();
		module.setOwnerUUID(profile.getId());
		module.setOwnerName(profile.getName());
		
		SecureItemSinkPacket packet = PacketHandler.getPacket(SecureItemSinkPacket.class);
		packet.setOwnerUUID(module.getOwnerUUID());
		packet.setOwnerName(module.getOwnerName());
		packet.setModulePos(module);
		MainProxy.sendPacketToServer(packet);

		super.actionPerformed(guibutton);
	}

	private static final ResourceLocation TEXTURE = new ResourceLocation("logisticspipes", "textures/gui/extractor.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		logisticspipes.utils.gui.GuiGraphics.drawGuiBackGround(mc, guiLeft, guiTop, xSize + guiLeft, ySize + guiTop, 0, true);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p1, int p2) {
		super.drawGuiContainerForegroundLayer(p1, p2);
		if (module.getOwnerName() == null)
			mc.fontRenderer.drawString("Owner: None", 10, 10, 0x404040);
		else
			mc.fontRenderer.drawString("Owner: " + module.getOwnerName(), 10, 10, 0x404040);
	}
}

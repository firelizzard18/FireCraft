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
		ySize = 200;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();

		int left = width / 2 - xSize / 2;
		int top = height / 2 - ySize / 2;

		buttonList.add(new GuiButton(0, left + 60, top + 90, 40, 20, "Claim"));
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton) {
		GameProfile name = player.getGameProfile();
		module.setOwnerUUID(name.getId());
		module.setOwnerName(name.getName());
		
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
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(TEXTURE);
		int j = guiLeft;
		int k = guiTop;
		//drawRect(width/2 - xSize / 2, height / 2 - ySize /2, width/2 + xSize / 2, height / 2 + ySize /2, 0xFF404040);
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
	}
}

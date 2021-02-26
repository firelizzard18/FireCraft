package com.firelizzard.firecraft.network;

import com.firelizzard.firecraft.entity.PlasmaBoltEntity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class PlasmaBoltPacket implements IMessage {
	private int shooterId;
	private float chargeLevel;

	public PlasmaBoltPacket() { }

	public PlasmaBoltPacket(int shooterId, float chargeLevel) {
		this.shooterId = shooterId;
		this.chargeLevel = chargeLevel;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.shooterId = buf.readInt();
		this.chargeLevel = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(shooterId);
		buf.writeFloat(chargeLevel);
	}

	public static class Handler implements IMessageHandler<PlasmaBoltPacket, IMessage> {
		@Override
		public IMessage onMessage(PlasmaBoltPacket message, MessageContext ctx) {
			World world = ctx.getServerHandler().playerEntity.getEntityWorld();
			Entity shooter = world.getEntityByID(message.shooterId);
			PlasmaBoltEntity bolt = new PlasmaBoltEntity(world, shooter, message.chargeLevel);
			world.spawnEntityInWorld(bolt);
			return null;
		}
	}
}

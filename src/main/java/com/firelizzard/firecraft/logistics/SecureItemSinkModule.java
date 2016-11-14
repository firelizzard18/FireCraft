package com.firelizzard.firecraft.logistics;

import java.util.List;
import java.util.UUID;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.gui.SecureItemSinkInHand;
import com.firelizzard.firecraft.gui.SecureItemSinkSlot;
import com.google.common.base.Strings;

import cofh.lib.util.helpers.SecurityHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import logisticspipes.modules.abstractmodules.LogisticsGuiModule;
import logisticspipes.modules.abstractmodules.LogisticsModule;
import logisticspipes.network.NewGuiHandler;
import logisticspipes.network.abstractguis.ModuleCoordinatesGuiProvider;
import logisticspipes.network.abstractguis.ModuleInHandGuiProvider;
import logisticspipes.pipes.PipeLogisticsChassi.ChassiTargetInformation;
import logisticspipes.utils.SinkReply;
import logisticspipes.utils.SinkReply.FixedPriority;
import logisticspipes.utils.item.ItemIdentifier;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class SecureItemSinkModule extends LogisticsGuiModule {
	public static final String NAME = "secureItemSinkModule";
	public static final String OWNER_UUID_KEY = "OwnerUUID";
	public static final String OWNER_NAME_KEY = "Owner";
	
	SinkReply _sinkReply;

	public SecureItemSinkModule() {}
	
	
	
	/* settings */
	private UUID ownerUUID;
	private String ownerName;
	
	public UUID getOwnerUUID() {
		return ownerUUID;
	}
	
	public void setOwnerUUID(UUID uuid) {
		ownerUUID = uuid;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public void setOwnerName(String name) {
		ownerName = name;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		String uuid = tag.getString(OWNER_UUID_KEY);
		if (!Strings.isNullOrEmpty(uuid))
			ownerUUID = UUID.fromString(uuid);
		
		String name = tag.getString(OWNER_NAME_KEY);
		if (!Strings.isNullOrEmpty(name))
			ownerName = name;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		if (ownerUUID != null)
			tag.setString(OWNER_UUID_KEY, ownerUUID.toString());
		if (ownerName != null)
			tag.setString(OWNER_NAME_KEY, ownerName);
	}

	
	
	/* module stuff */
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconTexture(IIconRegister register) {
		return register.registerIcon(FireCraftMod.MODID + ":" + NAME);
//		return register.registerIcon("logisticspipes:itemModule/ModulePolymorphicItemSink");
	}

	@Override
	public void registerPosition(ModulePositionType slot, int positionInt) {
		super.registerPosition(slot, positionInt);
		_sinkReply = new SinkReply(FixedPriority.ElectricBuffer, 1000, true, false, 5, 0, new ChassiTargetInformation(getPositionInt()));
	}

	@Override
	public SinkReply sinksItem(ItemIdentifier item, int bestPriority, int bestCustomPriority, boolean allowDefault, boolean includeInTransit) {
		if (ownerUUID == null && ownerName == null)
			return null;
		
		if (bestPriority > _sinkReply.fixedPriority.ordinal())
			return null;
		
		if (bestPriority == _sinkReply.fixedPriority.ordinal() && bestCustomPriority >= _sinkReply.customPriority)
			return null;
		
		ItemStack stack = item.makeNormalStack(0);
		if (!SecurityHelper.isSecure(stack))
			return null;

		if (ownerUUID != null) {
			if (!ownerUUID.equals(UUID.fromString(stack.stackTagCompound.getString(OWNER_UUID_KEY))))
				return null;
		} else if (!ownerName.equals(stack.stackTagCompound.getString(OWNER_NAME_KEY)))
			return null;
		
		if (_service.canUseEnergy(5))
			return _sinkReply;
		
		return null;
	}

	@Override
	public LogisticsModule getSubModule(int slot) {
		return null;
	}

	@Override
	public void tick() {}

	@Override
	public boolean hasGenericInterests() {
		return true;
	}

	@Override
	public List<ItemIdentifier> getSpecificInterests() {
		return null;
	}

	@Override
	public boolean interestedInAttachedInventory() {
		return false;
	}

	@Override
	public boolean interestedInUndamagedID() {
		return false;
	}

	@Override
	public boolean recievePassive() {
		return true;
	}
	
	
	
	/* gui stuff */
	@Override
	protected ModuleCoordinatesGuiProvider getPipeGuiProvider() {
		return NewGuiHandler.getGui(SecureItemSinkSlot.class);
	}

	@Override
	protected ModuleInHandGuiProvider getInHandGuiProvider() {
		return NewGuiHandler.getGui(SecureItemSinkInHand.class);
	}
}

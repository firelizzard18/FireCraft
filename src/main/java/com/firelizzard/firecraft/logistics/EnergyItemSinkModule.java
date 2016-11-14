package com.firelizzard.firecraft.logistics;

import java.util.List;

import com.firelizzard.firecraft.FireCraftMod;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import logisticspipes.modules.abstractmodules.LogisticsModule;
import logisticspipes.pipes.PipeLogisticsChassi.ChassiTargetInformation;
import logisticspipes.utils.SinkReply;
import logisticspipes.utils.SinkReply.FixedPriority;
import logisticspipes.utils.item.ItemIdentifier;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class EnergyItemSinkModule extends LogisticsModule {
	public static final String NAME = "energyItemSinkModule";
	public static final String ENERGY_KEY = "Energy";
	
	SinkReply _sinkReply;

	public EnergyItemSinkModule() {}
	
	
	
	/* module stuff */
	@Override
	public void readFromNBT(NBTTagCompound paramNBTTagCompound) {}

	@Override
	public void writeToNBT(NBTTagCompound paramNBTTagCompound) {}
	
	@Override
	public int getX() {
		return _service.getX();
	}
	
	@Override
	public int getY() {
		return _service.getY();
	}
	
	@Override
	public int getZ() {
		return _service.getZ();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconTexture(IIconRegister register) {
		return register.registerIcon(FireCraftMod.MODID + ":" + NAME);
//		return register.registerIcon("logisticspipes:itemModule/ModulePolymorphicItemSink");
	}

	@Override
	public void registerPosition(ModulePositionType slot, int positionInt) {
		super.registerPosition(slot, positionInt);
		_sinkReply = new SinkReply(FixedPriority.ElectricManager, Integer.MAX_VALUE, true, false, 5, 0, new ChassiTargetInformation(getPositionInt()));
	}

	@Override
	public SinkReply sinksItem(ItemIdentifier item, int bestPriority, int bestCustomPriority, boolean allowDefault, boolean includeInTransit) {
		if (bestPriority > _sinkReply.fixedPriority.ordinal())
			return null;
		
		if (bestPriority == _sinkReply.fixedPriority.ordinal() && bestCustomPriority >= _sinkReply.customPriority)
			return null;
		
		if (!(item.item instanceof IEnergyContainerItem))
			return null;

		ItemStack stack = item.makeNormalStack(0);
		IEnergyContainerItem container = (IEnergyContainerItem)item.item;
		if (container.getEnergyStored(stack) == container.getMaxEnergyStored(stack))
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
}

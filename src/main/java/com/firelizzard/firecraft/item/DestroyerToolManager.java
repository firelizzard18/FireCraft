package com.firelizzard.firecraft.item;

import cpw.mods.fml.common.Optional;
import ic2.api.item.IElectricItemManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

@Optional.Interface(iface = "ic2.api.item.IElectricItemManager", modid = "IC2")
public class DestroyerToolManager implements IElectricItemManager {
	@Override
	public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
		DestroyerTool item = (DestroyerTool)stack.getItem();
		return item.receiveEnergy(stack, (int) (amount * 4), simulate) / 4;
	}

	@Override
	public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
		if (externally)
			return 0;
		DestroyerTool item = (DestroyerTool)stack.getItem();
		return item.extractEnergy(stack, (int) (amount * 4), simulate) / 4;
	}

	@Override
	public double getCharge(ItemStack stack) {
		DestroyerTool item = (DestroyerTool)stack.getItem();
		return (double)item.getEnergyStored(stack) / 4;
	}

	@Override
	public boolean canUse(ItemStack stack, double amount) {
//		return getCharge(stack) > amount;
		return false;
	}

	@Override
	public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
		return false;
	}

	@Override
	public void chargeFromArmor(ItemStack stack, EntityLivingBase entity) {
//		DestroyerTool item = (DestroyerTool)stack.getItem();
//		item.receiveEnergy(stack, arg1, arg2)
	}

	@Override
	public String getToolTip(ItemStack stack) {
		return "Sup bro";
	}

}

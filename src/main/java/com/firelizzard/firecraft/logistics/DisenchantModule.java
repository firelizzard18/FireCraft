package com.firelizzard.firecraft.logistics;

import java.util.Collection;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import logisticspipes.interfaces.IInventoryUtil;
import logisticspipes.interfaces.routing.IFilter;
import logisticspipes.modules.abstractmodules.LogisticsModule;
import logisticspipes.pipefxhandlers.Particles;
import logisticspipes.pipes.PipeLogisticsChassi.ChassiTargetInformation;
import logisticspipes.pipes.basic.CoreRoutedPipe.ItemSendMode;
import logisticspipes.proxy.SimpleServiceLocator;
import logisticspipes.utils.SinkReply;
import logisticspipes.utils.SinkReply.FixedPriority;
import logisticspipes.utils.item.ItemIdentifier;
import logisticspipes.utils.tuples.Triplet;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class DisenchantModule extends LogisticsModule {
	public static final String NAME = "disenchantModule";

	public static final int ENERGY_USE = 5;

	private int ticksToAction = 20;
	private int currentTick = 0;
	private SinkReply _sinkReply;

	public DisenchantModule() {}

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
//		return register.registerIcon(FireCraftMod.getAssetLocation(NAME));
		return register.registerIcon("logisticspipes:itemModule/ModuleEnchantmentSink");
	}

	@Override
	public void registerPosition(ModulePositionType slot, int positionInt) {
		super.registerPosition(slot, positionInt);
		_sinkReply = new SinkReply(FixedPriority.EnchantmentItemSink, 2, true, false, ENERGY_USE, 1, new ChassiTargetInformation(getPositionInt()));
	}

	@Override
	public SinkReply sinksItem(ItemIdentifier item, int bestPriority, int bestCustomPriority, boolean allowDefault, boolean includeInTransit) {
		if (bestPriority > _sinkReply.fixedPriority.ordinal() || (bestPriority == _sinkReply.fixedPriority.ordinal() && bestCustomPriority >= _sinkReply.customPriority))
			return null;

		ItemStack stack = item.makeNormalStack(1);
		if (stack.isItemEnchanted())
			if (_service.canUseEnergy(ENERGY_USE))
				return _sinkReply;
			else
				return null;

		if (item.item != Items.enchanted_book)
			return null;

		if (Items.enchanted_book.func_92110_g(stack).tagCount() < 2)
			return null;

		if (!_service.canUseEnergy(ENERGY_USE))
			return null;

		return _sinkReply;
	}

	@Override
	public void tick() {
		if (++currentTick < ticksToAction)
			return;
		currentTick = 0;


		IInventoryUtil inv = _service.getSneakyInventory(true, slot, positionInt);
		if (inv == null)
			return;

		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack == null || stack.getItem() != Items.enchanted_book)
				continue;

			if (Items.enchanted_book.func_92110_g(stack).tagCount() != 1)
				continue;

			Triplet<Integer, SinkReply, List<IFilter>> reply = SimpleServiceLocator.logisticsManager.hasDestinationWithMinPriority(ItemIdentifier.get(stack), _service.getSourceID(), true, FixedPriority.ElectricBuffer);
			if (reply == null)
				continue;

			if (!_service.useEnergy(ENERGY_USE * 2))
				continue;

			_service.spawnParticle(Particles.OrangeParticle, 2);
			_service.sendStack(inv.decrStackSize(i, 1), reply, ItemSendMode.Normal);
		}
	}

	@Override
	public boolean hasGenericInterests() {
		return true;
	}

	@Override
	public LogisticsModule getSubModule(int slot) {
		return null;
	}

	@Override
	public Collection<ItemIdentifier> getSpecificInterests() {
		return null;
	}

	@Override
	public boolean interestedInAttachedInventory() {
		return true;
	}

	@Override
	public boolean interestedInUndamagedID() {
		return true;
	}

	@Override
	public boolean recievePassive() {
		return true;
	}

	@Override
	public boolean hasEffect() {
		return true;
	}
}

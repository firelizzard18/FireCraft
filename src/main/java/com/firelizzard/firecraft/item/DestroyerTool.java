package com.firelizzard.firecraft.item;

import java.util.List;

import com.firelizzard.firecraft.FireCraftMod;
import com.firelizzard.firecraft.block.SecurityStationBlock;
import com.firelizzard.firecraft.entity.AutopickupEntityItem;
import com.firelizzard.firecraft.network.PlasmaBoltPacket;

import codechicken.enderstorage.common.BlockEnderStorage;
import cofh.api.block.IBlockConfigGui;
import cofh.api.block.IBlockInfo;
import cofh.api.block.IDismantleable;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.tileentity.ITileInfo;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermaldynamics.duct.BlockDuct;
import cofh.thermalexpansion.block.cache.BlockCache;
import cofh.thermalexpansion.block.cell.BlockCell;
import cofh.thermalexpansion.block.device.BlockDevice;
import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.block.ender.BlockEnder;
import cofh.thermalexpansion.block.light.BlockLight;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.block.plate.BlockPlate;
import cofh.thermalexpansion.block.strongbox.BlockStrongbox;
import cofh.thermalexpansion.block.tank.BlockTank;
import cofh.thermalexpansion.block.workbench.BlockWorkbench;
import cofh.thermalexpansion.item.tool.ItemEnergyContainerBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.ironchest.BlockIronChest;
import erogenousbeef.bigreactors.common.block.BlockBRDevice;
import erogenousbeef.bigreactors.common.multiblock.block.BlockFuelRod;
import erogenousbeef.bigreactors.common.multiblock.block.BlockMultiblockGlass;
import erogenousbeef.bigreactors.common.multiblock.block.BlockReactorPart;
import erogenousbeef.bigreactors.common.multiblock.block.BlockReactorRedstonePort;
import erogenousbeef.bigreactors.common.multiblock.block.BlockTurbinePart;
import erogenousbeef.bigreactors.common.multiblock.block.BlockTurbineRotorPart;
import logisticspipes.blocks.LogisticsSolidBlock;
import logisticspipes.modules.abstractmodules.LogisticsGuiModule;
import logisticspipes.modules.abstractmodules.LogisticsModule;
import logisticspipes.pipes.PipeFluidRequestLogistics;
import logisticspipes.pipes.basic.CoreRoutedPipe;
import logisticspipes.pipes.basic.CoreUnroutedPipe;
import logisticspipes.pipes.basic.LogisticsBlockGenericPipe;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import powercrystals.minefactoryreloaded.block.BlockFactoryMachine;

public class DestroyerTool extends ItemEnergyContainerBase implements IEnergyContainerItem {
	public static final String NAME = "destroyer";
	public static final int MAX_USE_TICKS = 72000;
	public static final int MAX_CHARGE_TICKS = 20;

	public static enum Modes {
		TOOL, WEAPON
	}

	static boolean isMachineBlock(Block block) {
		// firecraft
		if (block instanceof SecurityStationBlock)
			return true;

		// thermal expansion
		if (block instanceof BlockCache)
			return true;
		if (block instanceof BlockCell)
			return true;
		if (block instanceof BlockDevice)
			return true;
		if (block instanceof BlockDynamo)
			return true;
		if (block instanceof BlockLight)
			return true;
		if (block instanceof BlockMachine)
			return true;
		if (block instanceof BlockPlate)
			return true;
		if (block instanceof BlockStrongbox)
			return true;
		if (block instanceof BlockTank)
			return true;
		if (block instanceof BlockWorkbench)
			return true;
		if (block instanceof BlockEnder)
			return true;

		// thermal dynamics
		if (block instanceof BlockDuct)
			return true;

		// big reactors
		if (block instanceof BlockBRDevice)
			return true;
		if (block instanceof BlockFuelRod)
			return true;
		if (block instanceof BlockMultiblockGlass)
			return true;
		if (block instanceof BlockReactorPart)
			return true;
		if (block instanceof BlockReactorRedstonePort)
			return true;
		if (block instanceof BlockTurbinePart)
			return true;
		if (block instanceof BlockTurbineRotorPart)
			return true;

		// ender storage
		if (block instanceof BlockEnderStorage)
			return true;

		// iron chest
		if (block instanceof BlockIronChest)
			return true;

		// logistics
		if (block instanceof LogisticsSolidBlock)
			return true;
		if (block instanceof LogisticsBlockGenericPipe)
			return true;

		// modular powersuits
		// if (block instanceof BlockTinkerTable)
		// return true;

		// minefactory reloaded
		if (block instanceof BlockFactoryMachine)
			return true;

		return false;
	}

	public float minDamage = 1f;
	public float hitDamage = 50f;
	public float silkTouchMultiplier = 2f;

	public DestroyerTool() {
		super(NAME);
		setMaxStackSize(1);
		setCreativeTab(FireCraftMod.TAB);

		maxEnergy = 20000000;
		maxTransfer = 160000;
		energyPerUse = 10000;

		modName = "firecraft";
	}
	
	public float getChargeLevel(ItemStack stack, int ticks) {
		switch (getModeEnum(stack)) {
		case WEAPON:
			return Math.max(0, Math.min((float)ticks / MAX_CHARGE_TICKS, 1));
			
		case TOOL:
		default:
			return 0;
		}
	}

	/* private helpers */

	public Modes getModeEnum(ItemStack stack) {
		int imode = getMode(stack);
		if (imode < 0 || imode >= Modes.values().length)
			return Modes.TOOL;
		return Modes.values()[imode];
	}

	private ItemStack getHeadDrop(EntityLivingBase entity) {
		// meta 0,1: skeleton and wither skeleton
		if (entity instanceof EntitySkeleton) {
			return new ItemStack(Items.skull, 1, ((EntitySkeleton) entity).getSkeletonType() == 1 ? 1 : 0);
		}
		// meta 2: zombie
		else if (entity instanceof EntityZombie) {
			return new ItemStack(Items.skull, 1, 2);
		}
		// meta 4: creeper
		else if (entity instanceof EntityCreeper) {
			return new ItemStack(Items.skull, 1, 4);
		}
		// meta 3: player
		else if (entity instanceof EntityPlayer) {
			ItemStack head = new ItemStack(Items.skull, 1, 3);
			NBTTagCompound nametag = new NBTTagCompound();
			nametag.setString("SkullOwner", ((EntityPlayer) entity).getDisplayName());
			head.setTagCompound(nametag);
			return head;
		}

		// no head
		return null;
	}

	private boolean onItemUse_machine(ItemStack stack, EntityPlayer player, World world, Block block, int x, int y,
			int z, int hitSide) {
		if (block instanceof IDismantleable && player.isSneaking())
			if (onItemUse_dismantle(stack, player, world, (IDismantleable) block, x, y, z, hitSide))
				return true;

		if (ServerHelper.isClientWorld(world))
			return onItemUse_client(stack, player, world, block, x, y, z, hitSide);

		if (player.isSneaking())
			return onItemUse_break(stack, player, world, block, x, y, z, hitSide);

		if (block instanceof IBlockConfigGui) {
			return ((IBlockConfigGui) block).openConfigGui(world, x, y, z, ForgeDirection.VALID_DIRECTIONS[hitSide],
					player);
		}

		if (block instanceof LogisticsBlockGenericPipe)
			return onItemUse_logisticsPipe(stack, player, world, (LogisticsBlockGenericPipe) block, x, y, z, hitSide);

		return false;
	}

	private boolean onItemUse_dismantle(ItemStack stack, EntityPlayer player, World world, IDismantleable block, int x,
			int y, int z, int hitSide) {
		if (!block.canDismantle(player, world, x, y, z))
			return false;

		List<ItemStack> drops = block.dismantleBlock(player, world, x, y, z, true);
		if (drops == null || drops.isEmpty())
			return false;

		tryGiveToPlayer(world, player, x, y, z, drops);
		return true;
	}

	private boolean onItemUse_client(ItemStack stack, EntityPlayer player, World world, Block block, int x, int y,
			int z, int hitSide) {
		if (block instanceof IBlockConfigGui || block instanceof IBlockInfo)
			return true;

		TileEntity theTile = world.getTileEntity(x, y, z);
		return theTile instanceof ITileInfo;
	}

	private boolean onItemUse_break(ItemStack stack, EntityPlayer player, World world, Block block, int x, int y, int z,
			int hitSide) {
		List<ItemStack> drops = BlockHelper.breakBlock(world, player, x, y, z, block, 0, true, false);
		if (drops == null || drops.size() == 0)
			return false;

		ejectIntoWorld(world, player, x, y, z, drops);
		return true;
	}

	private boolean onItemUse_logisticsPipe(ItemStack stack, EntityPlayer player, World world,
			LogisticsBlockGenericPipe block, int x, int y, int z, int hitSide) {
		CoreUnroutedPipe pipe = LogisticsBlockGenericPipe.getPipe(world, x, y, z);

		if (!LogisticsBlockGenericPipe.isValid(pipe))
			return false;

		if (!(pipe instanceof CoreRoutedPipe))
			return false;

		if (pipe instanceof PipeFluidRequestLogistics) {
			((PipeFluidRequestLogistics) pipe).openGui(player);
			return true;
		}

		LogisticsModule module = ((CoreRoutedPipe) pipe).getLogisticsModule();
		if (module instanceof LogisticsGuiModule)
			((LogisticsGuiModule) module).getPipeGuiProviderForModule().setTilePos(pipe.getContainer()).open(player);
		else
			((CoreRoutedPipe) pipe).onWrenchClicked(player);

		return true;
	}

	private void tryGiveToPlayer(World world, EntityPlayer player, int x, int y, int z, List<ItemStack> drops) {
		for (ItemStack drop : drops) {
			if (drop.stackSize == 0)
				continue;
			tryGiveToPlayer(world, player, x, y, z, drop);
		}
	}

	private void tryGiveToPlayer(World world, EntityPlayer player, int x, int y, int z, ItemStack drop) {
		if (!player.inventory.addItemStackToInventory(drop))
			ejectIntoWorld(world, null, x, y, z, drop);
	}

	private void ejectIntoWorld(World world, EntityPlayer player, int x, int y, int z, List<ItemStack> drops) {
		for (ItemStack drop : drops)
			ejectIntoWorld(world, player, x, y, z, drop);
	}

	private void ejectIntoWorld(World world, EntityPlayer player, int x, int y, int z, ItemStack drop) {
		EntityItem item;

		if (player == null)
			item = new EntityItem(world, x, y, z, drop);
		else
			item = new AutopickupEntityItem(world, player, x, y, z, drop);

		item.delayBeforeCanPickup = 10;
		world.spawnEntityInWorld(item);
	}

	/* minecraft item */
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		switch (getModeEnum(stack)) {
		case WEAPON:
			return MAX_USE_TICKS;
			
		case TOOL:
		default:
			return super.getMaxItemUseDuration(stack);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon(FireCraftMod.MODID + ":" + NAME);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z,
			EntityLivingBase entity) {
		switch (getModeEnum(stack)) {
		case TOOL:
			EntityPlayer player = null;
			if (entity instanceof EntityPlayer)
				player = (EntityPlayer) entity;

			if (player != null && !player.capabilities.isCreativeMode)
				extractEnergy(stack, energyPerUse, false);

			if (!ServerHelper.isServerWorld(world))
				return false;

			List<ItemStack> drops = BlockHelper.breakBlock(world, player, x, y, z, block, 0, true, false);
			if (drops == null || drops.size() == 0)
				return false;

			tryGiveToPlayer(world, player, x, y, z, drops);
			return true;

		case WEAPON:
		default:
			return super.onBlockDestroyed(stack, world, block, x, y, z, entity);
		}
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		switch (getModeEnum(stack)) {
		case TOOL:
			return 3;
		case WEAPON:
			return 0;
		default:
			return 1;
		}
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		switch (getModeEnum(stack)) {
		case TOOL:
			if (getEnergyStored(stack) < energyPerUse)
				return 0;

			if (isMachineBlock(block))
				return 1;

			return 20000;

		case WEAPON:
			return 0;

		default:
			return 1;
		}
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase player) {
		switch (getModeEnum(stack)) {
		case TOOL:
			float damage = Math.max(minDamage, hitDamage * extractEnergy(stack, energyPerUse, false) / energyPerUse);
			entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), damage);
			return true;

		case WEAPON:
		default:
			return super.hitEntity(stack, entity, player);
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase living) {
		switch (getModeEnum(stack)) {
		case TOOL:
			if (!(living instanceof EntityPlayer) || !player.isSneaking())
				// living.attackEntityFrom(DamageSource.causePlayerDamage(player),
				// Float.POSITIVE_INFINITY);
				living.setDead();
			ItemStack drop = getHeadDrop(living);
			if (drop != null)
				tryGiveToPlayer(player.worldObj, player, (int) living.posX, (int) living.posY, (int) living.posZ, drop);
			return true;

		case WEAPON:
		default:
			return super.itemInteractionForEntity(stack, player, living);
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int hitSide,
			float hitX, float hitY, float hitZ) {
		switch (getModeEnum(stack)) {
		case TOOL:
			Block block = world.getBlock(x, y, z);
			int metadata = world.getBlockMetadata(x, y, z);

			if (isMachineBlock(block))
				return onItemUse_machine(stack, player, world, block, x, y, z, hitSide);

			if (!block.canSilkHarvest(world, player, x, y, z, metadata))
				return false;

			int cost = (int) (energyPerUse * silkTouchMultiplier);
			if (!player.capabilities.isCreativeMode && extractEnergy(stack, cost, false) != cost)
				return false;

			List<ItemStack> drops = BlockHelper.breakBlock(world, player, x, y, z, block, 0, true, true);
			if (drops == null || drops.size() == 0)
				return false;

			tryGiveToPlayer(world, player, x, y, z, drops);
			return true;

		case WEAPON:
		default:
			return super.onItemUse(stack, player, world, x, y, z, hitSide, hitX, hitY, hitZ);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		switch (getModeEnum(stack)) {
		case WEAPON:
			player.setItemInUse(stack, MAX_USE_TICKS);
			return stack;

		case TOOL:
		default:
			return super.onItemRightClick(stack, world, player);
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int ticksLeft) {
		switch (getModeEnum(stack)) {
		case WEAPON:
			if (ServerHelper.isClientWorld(world))
				FireCraftMod.NETWORK.sendToServer(new PlasmaBoltPacket(player.getEntityId(), getChargeLevel(stack, player.getItemInUseDuration())));
//			if (ServerHelper.isServerWorld(world)) {
//				world.spawnEntityInWorld(new PlasmaBoltEntity(world, player, 2.0, hitDamage, 50));
//			}

		case TOOL:
		default:
			super.onPlayerStoppedUsing(stack, world, player, ticksLeft);
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int metadata, boolean isHolding) {
//		switch (getModeEnum(stack)) {
//		case WEAPON:
//			if (!isHolding) {
//				super.onUpdate(stack, world, entity, metadata, isHolding);
//				return;
//			}
//			if (Minecraft.getMinecraft().gameSettings.keyBindAttack.getIsKeyPressed())
//				System.out.println("Hi");
//			return;
//			
//		case TOOL:
//		default:
//			return;
//		}
	}

	/* thermal expansion tool */

	@Override
	protected void addInformationDelegate(ItemStack stack, EntityPlayer player, List<String> list, boolean check) {
		super.addInformationDelegate(stack, player, list, check);
		list.add("Mode: " + getModeEnum(stack));
	}

	@Override
	public int getNumModes(ItemStack stack) {
		return Modes.values().length;
	}

	/* override TE's name stuff */

	@Override
	public String getUnlocalizedName() {
		return "item." + NAME;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
}
